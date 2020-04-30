package com.cjh.lib_basissdk.util;

import java.io.File;

import android.os.Environment;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : 清除相关工具类
 * </pre>
 */
public final class CleanUtils {
	
	private CleanUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
     * 清除内部缓存
     * <p>/data/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
	public static boolean cleanInternalCache() {
		return deleteFilesInDir(Utils.getApp().getCacheDir());
	}
	
	/**
     * 清除内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
	public static boolean cleanInternalFiles() {
		return deleteFilesInDir(Utils.getApp().getFilesDir());
	}
	
	/**
     * 清除内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbs() {
        return deleteFilesInDir(Utils.getApp().getFilesDir().getParent() + File.separator + "databases");
    }

	/**
	 * Clean the internal databases.
	 * <p>directory: /data/data/package/databases</p>
	 *
	 * @return {@code true}: success<br>{@code false}: fail
	 */
	public static boolean cleanInternalDbsByFile() {
		return deleteFilesInDir(new File(Utils.getApp().getFilesDir().getParent(), "databases"));
	}




	/**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param dbName 数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        return Utils.getApp().deleteDatabase(dbName);
    }
    
    /**
     * 清除内部 SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalSP() {
        return deleteFilesInDir(Utils.getApp().getFilesDir().getParent() + File.separator + "shared_prefs");
    }

	/**
	 * Clean the internal shared preferences.
	 * <p>directory: /data/data/package/shared_prefs</p>
	 *
	 * @return {@code true}: success<br>{@code false}: fail
	 */
	public static boolean cleanInternalSpByFile() {
		return deleteFilesInDir(new File(Utils.getApp().getFilesDir().getParent(), "shared_prefs"));
	}



    
    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanExternalCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				&& deleteFilesInDir(Utils.getApp().getExternalCacheDir());
    }
    
    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(final String dirPath) {
        return deleteFilesInDir(dirPath);
    }
    
    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanCustomCache(final File dir) {
        return deleteFilesInDir(dir);
    }
    

	public static boolean deleteFilesInDir(final String dirPath) {
		return deleteFilesInDir(getFileByPath(dirPath));
	}
	

	/**
	 * 清除文件
	 * @param dir
	 * @param isDeleteSelf 是否清楚自身
	 * @return
	 */
	public static boolean deleteFiles(final File dir, boolean isDeleteSelf) {
		
		if (isDeleteSelf)
			return deleteDir(dir);
		return deleteFilesInDir(dir);
	}
	
	/**
	 * 清除文件
	 * @param dirPath
	 * @param isDeleteSelf 是否清楚自身
	 * @return
	 */
	public static boolean deleteFiles(final String dirPath, boolean isDeleteSelf) {
		
		if (null != dirPath && isDeleteSelf)
			return deleteDir(new File(dirPath));
		return deleteFilesInDir(dirPath);
	}
	
	
	/**
	 * 删除整个文件夹里面的所有文件,但是不包括这个文件夹自身
	 * @param dir 文件夹 
	 * @return 是否删除成功
	 */
	private static boolean deleteFilesInDir(final File dir) {
		if (dir == null) return false;
		// 目录不存在返回 true
		if (!dir.exists()) return true;
		// 不是目录返回 false
        if (!dir.isDirectory()) return false;
        //获取该文件夹里面的所有文件
      	File[] files = dir.listFiles();
      	if (files != null && files.length != 0) {
      		for (File file : files) {
      			if (file.isFile()) {
      				//有一个删除失败就是失败
      				if (!file.delete()) return false;
      			} else if (file.isDirectory()) {
      				//有一个删除失败就是失败
      				if (!deleteDir(file)) return false;
      			}
      		}
      	}
      	
      	return true;
	}
	
	
	/**
	 * 删除整个文件夹,包括这个文件夹自身
	 * @param dir 文件夹 
	 * @return 是否删除成功
	 */
	private static boolean deleteDir(final File dir) {
		
		if (dir == null) return false;
		// 目录不存在返回 true
		if (!dir.exists()) return true;
		// 不是目录返回 false
		if (!dir.isDirectory()) return false;
		//获取该文件夹里面的所有文件
		File[] files = dir.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (file.isFile()) {
					//有一个删除失败就是失败
					if (!file.delete()) return false;
				} else if (file.isDirectory()) {
					//有一个删除失败就是失败
					if (!deleteDir(file)) return false;
				}
			}
		}
		//删除自身
		return dir.delete();
	}
	
	private static File getFileByPath(final String filePath) {
		return isSpace(filePath) ? null : new File(filePath);
	} 
	
	private static boolean isSpace(final String s) {
		if (s == null) return true;
		for (int i = 0, len = s.length(); i < len; ++i) {
			//有一个字符不是空就不是空了
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
