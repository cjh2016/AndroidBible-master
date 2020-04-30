package com.cjh.lib_basissdk.util.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Build;

public class MountServiceManger {
	
	private Context mContext;
	private Object mMountService;
	private List<MountVolume> mMountVolumes;
	
	public @interface VolumeType {
		public static final int PRIMARY = 0;   //内部存储
		public static final int SD = 1;        //SD卡
		public static final int USB = 2;       //USB
	}
	
	public MountServiceManger(Context context) {
		mContext = context;
		refreshMountedVolumes();
	}
	
	/**
	 * 获取挂载服务对象
	 * Obtain the Mount Service object.
	 *
	 * @return The Mount Service.  挂载服务对象
	 */
	public Object getMountService() {
		if (mMountService == null && Build.VERSION.SDK_INT < /*Build.VERSION_CODES.N*/24) {
			mMountService = StorageManagerStub.MountService.getService();
		}
		return mMountService;
	}
	
	/**
	 * 获取对应类型和位置的挂载路径
	 * @param volumeType 挂载类型
	 * @param position 挂载位置
	 * @return 挂载路径
	 */
	public String getMountedVolumePath(@VolumeType int volumeType, int position) {
		List<String> volumePaths = getMountedVolumePath(volumeType);
		if (volumePaths.size() > 0) {
			if (position < volumePaths.size()) {
				return volumePaths.get(position);
			} else {
				return volumePaths.get(volumePaths.size() - 1);
			}
		}
		return null;
	}
	
	/**
	 * 获取对应类型的挂载列表
	 * @param volumeType 挂载类型
	 * @return 挂载列表
	 */
	public List<String> getMountedVolumePath(@VolumeType int volumeType) {
		List<String> volumePaths = new ArrayList<String>();
		switch (volumeType) {
			case VolumeType.SD:
				volumePaths = getSDCardVolumePaths();
				break;
			case VolumeType.USB:
				volumePaths = getUSBVolumePaths();
				break;
			case VolumeType.PRIMARY:
			default: 
				volumePaths.add(getPrimaryVolumePath());
				break;
		}
		return volumePaths;
	}

	/**
	 * 刷新所有挂载列表,包括内部存储,SD卡和USB
	 * Get the list of mounted volumes.
	 *
	 */
	public void refreshMountedVolumes() {
		mMountVolumes = StorageManagerStub.MountService.getRealMountedVolumes(getMountService(), mContext);
	}
	
	/**
	 * 获取所有挂载路径列表,包括内部存储,SD卡和USB
	 * @return 所有挂载的路径列表
	 */
	public List<String> getAllMountedVolumePaths() {
		refreshMountedVolumes();
		List<String> allVolumePaths = new ArrayList<String>();
		for (MountVolume volume : mMountVolumes) {
			allVolumePaths.add(volume.getPath());
		}
		return allVolumePaths;
	}
	
	/**
	 * Obtain mount volume based on the path.
	 *
	 * @param path Path to check.
	 * @return Mount volume of checked path.
	 */
	public MountVolume getMountVolumeByPath(String path) {
		for (MountVolume volume : mMountVolumes) {
			if (StorageManagerStub.contained(volume.getPath(), path)) {
				return volume;
			}
		}
		return null;
	}
	
	/**
	 * Obtain mount volume based on the volume UUID.
	 *
	 * @param uuid The volume UUID to find.
	 * @return Mount volume with requested UUID.
	 */
	public MountVolume getMountVolumeByUuid(String uuid) {
		if (uuid != null && !"null".equalsIgnoreCase(uuid)) {
			for (MountVolume volume : mMountVolumes) {
				if (volume.getUuid() != null) {
					if (volume.getUuid().equals(uuid)
							|| ("primary".equalsIgnoreCase(uuid) && volume.isPrimary())) {
						return volume;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取内部存储挂载路径
	 * Get primary mounted volume path.
	 *
	 * @return null or primary mounted volume path.
	 */
	private String getPrimaryVolumePath() {
		for (MountVolume volume : mMountVolumes) {
			if (volume.isPrimary() && !volume.isRemovable() 
					&& volume.isMounted()) {
				return volume.getPath();
			}
		}
		return null;
	}
	
	/**
	 * 获取内部存储挂载对象
	 * Get primary mounted volume.
	 *
	 * @return null or primary mounted volume.
	 */
	private MountVolume getPrimaryVolume() {
		for (MountVolume volume : mMountVolumes) {
			if (volume.isPrimary() && !volume.isRemovable() 
					&& volume.isMounted()) {
				return volume;
			}
		}
		return null;
	}
	
	/**
	 * 获取挂载的SD卡路径列表
	 * @return
	 */
	private List<String> getSDCardVolumePaths() {
		List<String> sdMountVolumePaths = new ArrayList<String>();
		String primaryPath = getPrimaryVolumePath();
		for (MountVolume volume : mMountVolumes) {
			if ((StorageManagerStub.contained("SD", volume.getDescription()) || 
					StorageManagerStub.contained("sd", volume.getDescription())) && 
					!primaryPath.equals(volume.getPath())) {
				sdMountVolumePaths.add(volume.getPath());
			}
		}
		return sdMountVolumePaths;
	}
	
	/**
	 * 获取挂载的SD卡挂载对象列表
	 * @return
	 */
	private List<MountVolume> getSDCardVolumes() {
		List<MountVolume> sdMountVolumes = new ArrayList<MountVolume>();
		for (MountVolume volume : mMountVolumes) {
			if (StorageManagerStub.contained("SD", volume.getDescription()) || 
					StorageManagerStub.contained("sd", volume.getDescription())) {
				sdMountVolumes.add(volume);
			}
		}
		return sdMountVolumes;
	}
	
	/**
	 * 获取挂载的USB路径列表
	 * @return
	 */
	private List<String> getUSBVolumePaths() {
		List<String> usbMountVolumePaths = new ArrayList<String>();
		for (MountVolume volume : mMountVolumes) {
			if (StorageManagerStub.contained("U", volume.getDescription()) || 
					StorageManagerStub.contained("u", volume.getDescription()) ||
					StorageManagerStub.contained("USB", volume.getDescription()) || 
					StorageManagerStub.contained("usb", volume.getDescription())) {
				usbMountVolumePaths.add(volume.getPath());
			}
		}
		return usbMountVolumePaths;
	}
	
	/**
	 * 获取挂载的USB挂载对象列表
	 * @return
	 */
	private List<MountVolume> getUSBVolumes() {
		List<MountVolume> usbMountVolumes = new ArrayList<MountVolume>();
		for (MountVolume volume : mMountVolumes) {
			if (StorageManagerStub.contained("U", volume.getDescription()) || 
					StorageManagerStub.contained("u", volume.getDescription()) ||
					StorageManagerStub.contained("USB", volume.getDescription()) || 
					StorageManagerStub.contained("usb", volume.getDescription())) {
				usbMountVolumes.add(volume);
			}
		}
		return usbMountVolumes;
	}
	
	/**
	 * Obtain default folder scanning, normally should be /storage/sdcard/DCIM, where
	 * the /storage/sdcard/ is the primary volume.
	 *
	 * @return Default path for camera storage folder.
	 */
	public String getDefaultFolderScanning() {
		
		String path = getPrimaryVolumePath();
		File dcim = null;
		if (path.length() > 0) {
			dcim = new File(path, "DCIM");
			if (dcim.exists() && dcim.isDirectory()) {
				path = dcim.getAbsolutePath();
			}
		} else {
			dcim = new File("DCIM");
			if (dcim.exists() && dcim.isDirectory()) {
				path = "DCIM";
			} else {
				path = "/";
			}
		}
		dcim = null;
		return path;
	}

}
