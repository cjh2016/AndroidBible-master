package com.cjh.lib_basissdk.util;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;

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



	/**
     * Return whether sdcard is enabled by environment.
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Return the path of sdcard by environment.
     *
     * @return the path of sdcard by environment
     */
    public static String getSDCardPathByEnvironment() {
        if (isSDCardEnableByEnvironment()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * Return the information of sdcard.
     *
     * @return the information of sdcard
     */
    public static List<SDCardInfo> getSDCardInfo() {
        List<SDCardInfo> paths = new ArrayList<>();
        StorageManager sm = (StorageManager) Utils.getApp().getSystemService(Context.STORAGE_SERVICE);
        if (sm == null) return paths;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<StorageVolume> storageVolumes = sm.getStorageVolumes();
            try {
                //noinspection JavaReflectionMemberAccess
                Method getPathMethod = StorageVolume.class.getMethod("getPath");
                for (StorageVolume storageVolume : storageVolumes) {
                    boolean isRemovable = storageVolume.isRemovable();
                    String state = storageVolume.getState();
                    String path = (String) getPathMethod.invoke(storageVolume);
                    paths.add(new SDCardInfo(path, state, isRemovable));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
                //noinspection JavaReflectionMemberAccess
                Method getPathMethod = storageVolumeClazz.getMethod("getPath");
                Method isRemovableMethod = storageVolumeClazz.getMethod("isRemovable");
                //noinspection JavaReflectionMemberAccess
                Method getVolumeStateMethod = StorageManager.class.getMethod("getVolumeState", String.class);
                //noinspection JavaReflectionMemberAccess
                Method getVolumeListMethod = StorageManager.class.getMethod("getVolumeList");
                Object result = getVolumeListMethod.invoke(sm);
                final int length = Array.getLength(result);
                for (int i = 0; i < length; i++) {
                    Object storageVolumeElement = Array.get(result, i);
                    String path = (String) getPathMethod.invoke(storageVolumeElement);
                    boolean isRemovable = (Boolean) isRemovableMethod.invoke(storageVolumeElement);
                    String state = (String) getVolumeStateMethod.invoke(sm, path);
                    paths.add(new SDCardInfo(path, state, isRemovable));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return paths;
    }

    /**
     * Return the ptah of mounted sdcard.
     *
     * @return the ptah of mounted sdcard.
     */
    public static List<String> getMountedSDCardPath() {
        List<String> path = new ArrayList<>();
        List<SDCardInfo> sdCardInfo = getSDCardInfo();
        if (sdCardInfo == null || sdCardInfo.isEmpty()) return path;
        for (SDCardInfo cardInfo : sdCardInfo) {
            String state = cardInfo.state;
            if (state == null) continue;
            if ("mounted".equals(state.toLowerCase())) {
                path.add(cardInfo.path);
            }
        }
        return path;
    }


    /**
     * Return the total size of external storage
     *
     * @return the total size of external storage
     */
    public static long getExternalTotalSize() {
        return UtilsBridge.getFsTotalSize(getSDCardPathByEnvironment());
    }

    /**
     * Return the available size of external storage.
     *
     * @return the available size of external storage
     */
    public static long getExternalAvailableSize() {
        return UtilsBridge.getFsAvailableSize(getSDCardPathByEnvironment());
    }

    /**
     * Return the total size of internal storage
     *
     * @return the total size of internal storage
     */
    public static long getInternalTotalSize() {
        return UtilsBridge.getFsTotalSize(Environment.getDataDirectory().getAbsolutePath());
    }

    /**
     * Return the available size of internal storage.
     *
     * @return the available size of internal storage
     */
    public static long getInternalAvailableSize() {
        return UtilsBridge.getFsAvailableSize(Environment.getDataDirectory().getAbsolutePath());
    }

    public static class SDCardInfo {

        private String  path;
        private String  state;
        private boolean isRemovable;
        private long    totalSize;
        private long    availableSize;

        SDCardInfo(String path, String state, boolean isRemovable) {
            this.path = path;
            this.state = state;
            this.isRemovable = isRemovable;
            this.totalSize = UtilsBridge.getFsTotalSize(path);
            this.availableSize = UtilsBridge.getFsAvailableSize(path);
        }

        public String getPath() {
            return path;
        }

        public String getState() {
            return state;
        }

        public boolean isRemovable() {
            return isRemovable;
        }

        public long getTotalSize() {
            return totalSize;
        }

        public long getAvailableSize() {
            return availableSize;
        }

        @Override
        public String toString() {
            return "SDCardInfo {" +
                    "path = " + path +
                    ", state = " + state +
                    ", isRemovable = " + isRemovable +
                    ", totalSize = " + Formatter.formatFileSize(Utils.getApp(), totalSize) +
                    ", availableSize = " + Formatter.formatFileSize(Utils.getApp(), availableSize) +
                    '}';
        }
    }
	
}
