package com.cjh.lib_basissdk.compress;

import java.io.File;
import android.os.Handler;
import android.os.Looper;

public abstract class BaseArchiver {
	
	protected String TAG = this.getClass().getSimpleName();
	
	protected Handler mMainHandler = new Handler(Looper.getMainLooper());
	
	/**
     * 压缩文件
     * @param files
     * @param destPath
     */
	public abstract void doArchiver(File[] files, String destPath);

	/**
	 * 解压文件
	 * @param handler
	 * @param archiverPath
	 * @param unarchiverPath
	 * @param listener
	 */
    public abstract void doUnArchiver(Handler handler, String archiverPath, String unarchiverPath,
									  IArchiverListener listener);

}
