package com.cjh.lib_basissdk.compress;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

/**
 * 解压管理工具,暂时只支持zip和rar格式解压,后续可能要支持7z
 */
public final class ArchiverManager extends BaseArchiver {
	
	private volatile static ArchiverManager sInstance;
	
	private Executor mThreadPoolExecutor;
	
	private BaseArchiver mSmartArchiver;
	
	private ArchiverManager() {
		mThreadPoolExecutor = Executors.newSingleThreadExecutor();
	}
	
	public static ArchiverManager getInstance() {
		if (null == sInstance) {
			synchronized (ArchiverManager.class) {
				if (null == sInstance) {
					sInstance = new ArchiverManager();
				}
			}
		}
		return sInstance;
	}

	@Override
	public void doArchiver(File[] files, String destPath) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void doUnArchiver(final Handler handler, final String archiverPath, 
			final String unarchiverPath, final IArchiverListener listener) {
		mSmartArchiver = findSmartArchiver(getFileType(archiverPath));
		mThreadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				mSmartArchiver.doUnArchiver(handler, archiverPath, unarchiverPath, listener);
			}
		});
	}
	
	/**
     * 获取文件类型
     * @param filename
     * @return
     */
    private String getFileType(String filename) {
        Log.i(TAG, "getFileType: " + filename);
        String type = null;
        if (TextUtils.isEmpty(filename)) {
			return null;
		}
        String[] temp = filename.split("\\.");
        type = temp[temp.length - 1].trim();
        return type;
    }
	
	private BaseArchiver findSmartArchiver(@ArchiverType String archiverType) {
		switch (archiverType) {
			case ArchiverType._ZIP:
				return new ZipArchiver();
			case ArchiverType._RAR:
				return new RarArchiver();
			default:
				return null;
		}
	}
	
	/**
	 *  压缩类型
	 */
	public @interface ArchiverType {
		public static final String _ZIP = "zip";
		public static final String _RAR = "rar";
	}
}
