package com.cjh.lib_basissdk.util

import android.os.Environment
import java.io.File


/**
 * @author: caijianhui
 * @date: 2020/4/29 17:00
 * @description: 清除相关工具类
 */
object CleanUtils {

    /**
     * 清除内部缓存
     *
     * /data/data/com.xxx.xxx/cache
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalCache(): Boolean {
        return deleteFilesInDir(Utils.app?.cacheDir)
    }

    /**
     * 清除内部文件
     *
     * /data/data/com.xxx.xxx/files
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalFiles(): Boolean {
        return deleteFilesInDir(Utils.app?.filesDir)
    }

    /**
     * 清除内部数据库
     *
     * /data/data/com.xxx.xxx/databases
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalDbs(): Boolean {
        return deleteFilesInDir(
            Utils.app?.filesDir?.parent
                .toString() + File.separator + "databases"
        )
    }


    /**
     * 根据名称清除数据库
     *
     * /data/data/com.xxx.xxx/databases/dbName
     *
     * @param dbName 数据库名称
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalDbByName(dbName: String?): Boolean {
        return Utils.app?.deleteDatabase(dbName)!!
    }


    /**
     * 清除内部 SP
     *
     * /data/data/com.xxx.xxx/shared_prefs
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanInternalSP(): Boolean {
        return deleteFilesInDir(
            Utils.app?.filesDir?.parent
                .toString() + File.separator + "shared_prefs"
        )
    }

    /**
     * 清除外部缓存
     *
     * /storage/emulated/0/android/data/com.xxx.xxx/cache
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanExternalCache(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && deleteFilesInDir(
            Utils.app?.externalCacheDir
        )
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanCustomCache(dirPath: String?): Boolean {
        return deleteFilesInDir(dirPath)
    }


    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    fun cleanCustomCache(dir: File?): Boolean {
        return deleteFilesInDir(dir)
    }

    fun deleteFilesInDir(dirPath: String?): Boolean {
        return deleteFilesInDir(getFileByPath(dirPath!!))
    }

    /**
     * 清除文件
     * @param dir
     * @param isDeleteSelf 是否清楚自身
     * @return
     */
    fun deleteFiles(dir: File?, isDeleteSelf: Boolean): Boolean {
        return if (isDeleteSelf) deleteDir(dir) else deleteFilesInDir(dir)
    }

    /**
     * 清除文件
     * @param dirPath
     * @param isDeleteSelf 是否清楚自身
     * @return
     */
    fun deleteFiles(dirPath: String?, isDeleteSelf: Boolean): Boolean {
        return if (null != dirPath && isDeleteSelf) deleteDir(File(dirPath)) else deleteFilesInDir(
            dirPath
        )
    }


    /**
     * 删除整个文件夹里面的所有文件,但是不包括这个文件夹自身
     * @param dir 文件夹
     * @return 是否删除成功
     */
    private fun deleteFilesInDir(dir: File?): Boolean {
        if (dir == null) return false
        // 目录不存在返回 true
        if (!dir.exists()) return true
        // 不是目录返回 false
        if (!dir.isDirectory) return false
        //获取该文件夹里面的所有文件
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (file.isFile) {
                    //有一个删除失败就是失败
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    //有一个删除失败就是失败
                    if (!deleteDir(file)) return false
                }
            }
        }
        return true
    }


    /**
     * 删除整个文件夹,包括这个文件夹自身
     * @param dir 文件夹
     * @return 是否删除成功
     */
    private fun deleteDir(dir: File?): Boolean {
        if (dir == null) return false
        // 目录不存在返回 true
        if (!dir.exists()) return true
        // 不是目录返回 false
        if (!dir.isDirectory) return false
        //获取该文件夹里面的所有文件
        val files = dir.listFiles()
        if (files != null && files.isNotEmpty()) {
            for (file in files) {
                if (file.isFile) {
                    //有一个删除失败就是失败
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    //有一个删除失败就是失败
                    if (!deleteDir(file)) return false
                }
            }
        }
        //删除自身
        return dir.delete()
    }


    private fun getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }


    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            //有一个字符不是空就不是空了
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}