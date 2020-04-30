package com.cjh.lib_basissdk.util;

import java.lang.reflect.Method;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.StatFs;
import android.os.storage.StorageManager;

import com.cjh.lib_basissdk.util.storage.MountServiceManger;
import com.cjh.lib_basissdk.util.storage.MountServiceManger.VolumeType;


public class StorageUtils {
	
	private MountServiceManger mountServiceManger;
	
	public StorageUtils(Context context) {
       mountServiceManger = new MountServiceManger(context);
    }
	
	/**
	 * 打印StorageManager类的所有的方法名,参数和返回值,用于调试
	 */
	public void printStorageManagerMethods(){
        try {
            Method[] methods = StorageManager.class.getMethods();
            for(Method method: methods){
                System.out.println("storage_method : methodName = " + method.getName()
                        + " methodParams = " + getParams(method.getParameterTypes())
                        + " returnType = " + method.getReturnType().getSimpleName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
	 * 打印StorageVolume类的所有的方法名,参数和返回值,用于调试
	 */
    public void printStorageVolumeMethods(Context context){
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Method method = StorageManager.class.getMethod("getVolumeList");
            Object[] storageVolumes = (Object[]) method.invoke(storageManager);
            System.out.println("storageVolumes : " + storageVolumes.length);
            Method[] methods = storageVolumes[0].getClass().getMethods();
            for(Method m : methods){
                System.out.println("storageVolumes : methodName = " + m.getName() + " methodParams = " + getParams(m.getParameterTypes()) + " returnType = " + m.getReturnType().getSimpleName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 根据类名获取名字
     * @param cls
     * @return
     */
    private String getParams(Class<?>[] cls){
        StringBuilder sb = new StringBuilder();
        for(Class clz : cls){
            sb.append(clz.getSimpleName()).append(",");
        }
        return sb.toString();
    }
    
    /**
	 * 获取所有挂载路径列表,包括内部存储,SD卡和USB
	 * @return 所有挂载的路径列表
	 */
    public List<String> getAllMountedPaths() {
    	if (null == mountServiceManger) return null;
    	return mountServiceManger.getAllMountedVolumePaths();
    }
    
    /**
     * 获取挂载的内部存储,基本只有一个路径
     * @return
     */
    public String getPrimaryMountedPath() {
    	if (null == mountServiceManger) return null;
    	return mountServiceManger.getMountedVolumePath(VolumeType.PRIMARY).get(0);
    }
    
    /**
     * 获取所有挂载的外部SD卡的路径列表
     * @return
     */
    public List<String> getSDCardMountedPaths() {
    	if (null == mountServiceManger) return null;
    	return mountServiceManger.getMountedVolumePath(VolumeType.SD);
    }
    
    /**
     * 根据position获取挂载的外部SD卡的路径
     * @param position
     * @return
     */
    public String getSDCardMountedPath(int position) {
    	if (null == mountServiceManger) return null;
    	return mountServiceManger.getMountedVolumePath(VolumeType.SD, position);
    }
    
    /**
     * 获取所有挂载的USB的路径列表
     * @return
     */
    public List<String> getUSBMountedPaths() {
    	if (null == mountServiceManger) return null;
    	return mountServiceManger.getMountedVolumePath(VolumeType.USB);
    }
    
    /**
     * 根据position获取挂载的USB的路径
     * @return
     */
    public String getUSBMountedPath(int position) {
    	if (null == mountServiceManger) return null;
    	return mountServiceManger.getMountedVolumePath(VolumeType.USB, position);
    }
    
    /**
	 * 根据优先级类型自动匹配合适的挂载路径
	 * @param priorityType
	 * @param position
	 * @return
	 */
    public String autoMatchMountedPath(@VolumeType int priorityType) {
    	 return autoMatchMountedPath(priorityType, 0);
    }
    
	/**
	 * 根据优先级类型和位置自动匹配合适的挂载路径
	 * @param priorityType
	 * @param position
	 * @return
	 */
	public String autoMatchMountedPath(@VolumeType int priorityType, int position) {
		if (priorityType == VolumeType.USB) {
			List<String> usbPaths = getUSBMountedPaths();
			if (usbPaths.size() > 0) {
				if (position < usbPaths.size()) {
					return usbPaths.get(position);
				} else {
					return usbPaths.get(usbPaths.size() - 1);
				}
			} else {
				List<String> sdPaths = getSDCardMountedPaths();
				if (sdPaths.size() > 0) {
					if (position < sdPaths.size()) {
						return sdPaths.get(position);
					} else {
						return sdPaths.get(sdPaths.size() - 1);
					}
				}
			}
		} else if (priorityType == VolumeType.SD) {
			List<String> sdPaths = getSDCardMountedPaths();
			if (sdPaths.size() > 0) {
				if (position < sdPaths.size()) {
					return sdPaths.get(position);
				} else {
					return sdPaths.get(sdPaths.size() - 1);
				}
			}
		}
		return getPrimaryMountedPath();
	}
   
    /**
     * 根据路径获取当前目录的总大小和可用的大小  storage[0]是总的大小, storage[1]是可用的大小
     * get storage size info by path.
     * @param path
     * @return storage[0] is total size , storage[1] is available size.  
     */
    public long[] getStorageSizeInfo(String path){
        long[] result = new long[2];
        try{
            StatFs statfs = new StatFs(path);
            if (statfs != null) {
                long nTotalBlocks = getBlockCount(statfs);
                long nBlocSize = getBlockSize(statfs);
                result[0] =  nTotalBlocks * nBlocSize;

                long nAvailableBlock = getAvailableBlocksCount(statfs);
                result[1] = nAvailableBlock * nBlocSize;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("NewApi")
	private long getBlockSize(StatFs statFs){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            return statFs.getBlockSizeLong();
        }
        return statFs.getBlockSize();
    }

    @SuppressLint("NewApi")
	private long getBlockCount(StatFs statFs){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            return statFs.getBlockCountLong();
        }
        return statFs.getBlockCount();
    }

    @SuppressLint("NewApi")
	private long getAvailableBlocksCount(StatFs statFs){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            return statFs.getAvailableBlocksLong();
        }
        return statFs.getAvailableBlocks();
    }
}
