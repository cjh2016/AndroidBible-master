package com.cjh.lib_basissdk.image_loading;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.collection.LruCache;

import com.cjh.lib_basissdk.R;

/**
 * 图片缓存库, 实现了三级缓存功能, LruCache, DiskLruCache和网络
 * 
 */
public final class OriginalImageLoader implements ImageLoader {
	
	private static final String TAG = "ImageLoader";
	private Context mContext;
	private LruCache<String, Bitmap> mMemoryCache;
	private DiskLruCache mDiskLruCache;
	private Bitmap bitmap;
	//50M
	private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
	private boolean mIsDiskLruCacheCreated;
	private static final int DISK_CACHE_INDEX = 0;
	private ImageResizer mImageResizer;
	private String cachePath;
	//8k
	private static final int IO_BUFFER_SIZE = 8 * 1024;
	
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int TAG_KEY_URI = R.id.imageloader_uri;
	
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
	private static final long KEEP_ALIVE = 10L;
	
	private boolean isSyncNet = false;
	
	private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			LoaderResult result = (LoaderResult) msg.obj;
			View target = result.target;
			String uri = (String) target.getTag(TAG_KEY_URI);
			if (NinePatch.isNinePatchChunk(result.bitmap.getNinePatchChunk())) {
				NinePatchDrawable drawable = new NinePatchDrawable(result.bitmap, 
						result.bitmap.getNinePatchChunk(), new Rect(), null);
				target.setBackground(drawable);
				if (uri.equals(result.uri)) {
					target.setBackground(drawable);
				} else {
					Log.d(TAG, "set image bitmap, but url has changed, ignored!");
				}
			} else {
				target.setBackground(new BitmapDrawable(mContext.getResources(), result.bitmap));
				if (uri.equals(result.uri)) {
					target.setBackground(new BitmapDrawable(mContext.getResources(), result.bitmap));
				} else {
					Log.d(TAG, "set image bitmap, but url has changed, ignored!");
				}
			}
			
		}
	};
	
	/**
	 * 自定义线程工厂, 主要是统一命名线程池里面线程的名字
	 */
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		//保证多线程下的原子性
		private final AtomicInteger mCount = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
		}
	};
	
	public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 
			KEEP_ALIVE, TimeUnit.SECONDS, 
			new LinkedBlockingDeque<Runnable>(), sThreadFactory);
	
	private static final int MESSAGE_POST_RESULT = 1;
	
	public OriginalImageLoader(Context context) {
		//图片压缩工具
		mImageResizer = new ImageResizer();
		mContext = context;
		//初始化LruCache
		//k
		int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
		// k, Google默认做法是取最大的八分之一
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024; //k
			}
		};
		//初始化DiskLruCache
		File diskCacheDir = getDiskCacheDir(mContext, "cjh/network/imageloader");
		if (!diskCacheDir.exists()) {
			diskCacheDir.mkdirs();
		}
		if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
			try {
				mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
				mIsDiskLruCacheCreated = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			return path.getUsableSpace();
		}
		StatFs statFs = new StatFs(path.getPath());
		return statFs.getBlockSize() * statFs.getAvailableBlocks();
	}
	
	private File getDiskCacheDir(Context mContext, String uniqueName) {
		boolean externalStorageAvailable = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);
		if (externalStorageAvailable) {
			//优先用外部存储
			cachePath = mContext.getExternalCacheDir().getPath();
		} else {
			cachePath = mContext.getCacheDir().getPath();  
		}
		return new File(cachePath + File.separator + uniqueName);
		/*return StorageUtils.getInstance(HRContext.getInstance())
				.autoMatchSpecifiedFile(VolumeType.USB, uniqueName);*/
	}
	
	/**
	 * 同步加载(没什么卵用。因为网络请求不能在主线程,还是得开线程)
	 */
	@Override
	public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
		if (!isSyncNet) {
			//先从缓存中获取
			Bitmap bitmap = loadBitmapFromMemoryCache(uri);
			if(bitmap != null){
	            Log.e(TAG, "loadBitmapFromMemCache, url " + uri);
	            return bitmap;
	        }
		}
		try {
			if (!isSyncNet) {
				//从磁盘中获取
				bitmap = loadBitmapFromDiskCache(uri, reqWidth, reqHeight);
				if(bitmap != null){
	                Log.e(TAG, "loadBitmapFromDiskCache, url " + uri );
	                return bitmap;
	            }
			}
			//从网络中获取
			bitmap = loadBitmapFromHttp(uri, reqWidth, reqHeight);
			Log.e(TAG, "loadBitmapFromHttp, url " + uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(bitmap == null && !mIsDiskLruCacheCreated){
            Log.e(TAG, "encounter error,DiskLruCache is not created.");
			//没开磁盘缓存,直接从网络下载
            bitmap = downloadBitmapFromUrl(uri);
        }
		return bitmap;
	}
	
	/**
	 * 异步加载
	 */
	@SuppressLint("NewApi")
	public void bindBitmap(final String uri, final View target, final int reqWidth, final int reqHeight) {
		target.setTag(TAG_KEY_URI, uri);
		if (!isSyncNet) {
			//先从缓存中获取
			Bitmap bitmap = loadBitmapFromMemoryCache(uri);
			if(bitmap != null){
				if (NinePatch.isNinePatchChunk(bitmap.getNinePatchChunk())) {
					NinePatchDrawable drawable = new NinePatchDrawable(bitmap, bitmap.getNinePatchChunk(), 
							new Rect(), null);
					target.setBackground(drawable);
				} else {
					target.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
				}
		        return;
		    }
		}
		Runnable loadBitmapTask = new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
				if(bitmap != null){
					LoaderResult result = new LoaderResult(target, uri, bitmap);
					Message message = mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result);
					message.sendToTarget();
				}
			}
		};
		THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
	}
	
	/**
	 * 直接下载图片
	 */
	private Bitmap downloadBitmapFromUrl(String uri) {
		Bitmap bitmap = null;
		HttpURLConnection urlConnection = null;
		BufferedInputStream in = null;
		try {
			URL url = new URL(uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
			bitmap = BitmapFactory.decodeStream(in);
		} catch (IOException e) {
			Log.e(TAG, "Error in downloadBitmap:" + e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			closeIO(in);
		}
		return bitmap;
	}
	
	/**
	 * 从内存中加载图片
	 */
	private Bitmap loadBitmapFromMemoryCache(String url) {
		String key = hashKeyFormUrl(url);
		Bitmap bitmap = getBitmapFromMemoryCache(key);
		return bitmap;
	}
	
	/**
	 * 添加图片到内存
	 */
	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		Log.e(TAG, "addBitmapToMemoryCache: ");
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}
	
	/**
	 * 从内存中获取图片
	 */
	private Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}
	
	/**
	 * 从网络中下载图片,并添加到磁盘
	 */
	private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) throws IOException {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			throw new RuntimeException("can not visit netWork from UI Thread");
		}
		if (mDiskLruCache == null) {
			return null;
		}
		String key = hashKeyFormUrl(url);
		DiskLruCache.Editor editor = mDiskLruCache.edit(key);
		if (editor != null) {
			OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
			if (downloadUrlToStream(url, outputStream)) {
				editor.commit();
			} else {
				editor.abort();
			}
			mDiskLruCache.flush();
		}
		return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
	}
	
	/**
	 * 从磁盘中获取图片
	 */
	private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {
		if (Looper.myLooper() == Looper.getMainLooper()) {
			throw new RuntimeException("load bitmap from UI Thread, it's not recommended");
		}
		if(mDiskLruCache == null){
			return null;
	    }
		Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if(snapshot != null){
        	FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
        	FileDescriptor fileDescriptor = fileInputStream.getFD();
        	bitmap = mImageResizer.decodeSampleFromFileDescriptor(fileDescriptor, reqWidth, reqHeight);
        	if(bitmap != null){
        		addBitmapToMemoryCache(key, bitmap);
        	}
        }
        return bitmap;
	}
	
	/**
	 * 从网络中下载图片
	 */
	private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());
			out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            closeIO(in, out);
        }
        return false;
	}
	
	/**
	 * 将url转成key, 因为url中很可能有特殊字符, 会影响url在Android中的直接使用
	 */
	private String hashKeyFormUrl(String url) {
		String cacheKey = null;
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(url.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
			Log.e("cjhhh", "url = " + url + " md5 = " + cacheKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return cacheKey;
	}
	
	/**
	 * 将字节转为16进制
	 */
	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	
	/**
	 * 关闭可关闭对象 
	 */
	private void closeIO(Closeable... closeables) {
		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
