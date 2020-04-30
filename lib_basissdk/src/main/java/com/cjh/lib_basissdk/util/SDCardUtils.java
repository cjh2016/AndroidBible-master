package com.cjh.lib_basissdk.util;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.os.storage.StorageManager;

/**
 *
 * @deprecated use {@link StorageUtils} instead of
 * SD 卡相关工具类
 * 
 */
public final class SDCardUtils {

	private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
	
	/**
     * 获取 SD 卡路径
     *
     * @param removable true : 外置 SD 卡<br>false : 内置 SD 卡
     * @return SD 卡路径
     */
	@SuppressWarnings("TryWithIdenticalCatches")
	public static List<String> getSDCardPaths(boolean removable) {
		List<String> paths = new ArrayList<>();
		StorageManager mStorageManager = (StorageManager) Utils.getApp()
                .getSystemService(Context.STORAGE_SERVICE);
		
		try {
			Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
			Method getVolumeList = StorageManager.class.getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
            	Object storageVolumeElement = Array.get(result, i);
            	String path = (String) getPath.invoke(storageVolumeElement);
            	boolean res = (Boolean) isRemovable.invoke(storageVolumeElement);
            	if (removable == res){
            		paths.add(path);
            	}
            }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paths;
	}
	
	/**
     * 获取 SD 卡路径
     *
     * @return SD 卡路径
     */
	@SuppressWarnings("TryWithIdenticalCatches")
	public static List<String> getSDCardPaths() {
		
		StorageManager storageManager = (StorageManager) Utils.getApp()
				.getSystemService(Context.STORAGE_SERVICE);
		List<String> paths = new ArrayList<String>();
		
		try {
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
			getVolumePathsMethod.setAccessible(true);
			Object invoke = getVolumePathsMethod.invoke(storageManager);
			paths = Arrays.asList((String[]) invoke);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paths;
	}
	
	
	/**
	 * 调用storageInfo.isRemoveable 可移除的就是二级存储卡，一般就是我们可拔插的sd卡。不可移除的就是机身存储。
	 * 调用storageInfo.isMounted()就可知道这个存储位置是否可用。
	 * @param context
	 * @return
	 */
	public static ArrayList<StorageInfo> getAvaliableStorages(Context context) {
	    ArrayList<StorageInfo> storagges = new ArrayList<>();
	    StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
	    try {
	        Class<?>[] paramClasses = {};
	        Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
	        getVolumeList.setAccessible(true);
	        Object[] params = {};
	        Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
	        if (invokes != null) {
	            StorageInfo info = null;
	            for (int i = 0; i < invokes.length; i++) {
	                Object obj = invokes[i];
	                Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
	                String path = (String) getPath.invoke(obj, new Object[0]);
	                info = new StorageInfo(path);
	                File file = new File(info.path);
	                if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
	                    Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
	                    String state = null;
	                    try {
	                        Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
	                        state = (String) getVolumeState.invoke(storageManager, info.path);
	                        info.state = state;
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }

	                    if (info.isMounted()) {
	                        info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
	                        storagges.add(info);
	                    }
	                }
	            }
	        }
	    } catch (NoSuchMethodException e1) {
	        e1.printStackTrace();
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (InvocationTargetException e) {
	        e.printStackTrace();
	    }
	    storagges.trimToSize();

	    return storagges;
	}
	
	public static class StorageInfo {

	    public String path;
	    public String state;
	    public boolean isRemoveable;

	    public StorageInfo(String path) {
	        this.path = path;
	    }

	    public boolean isMounted() {
	        return "mounted".equals(state);
	    }
	}
	
}
