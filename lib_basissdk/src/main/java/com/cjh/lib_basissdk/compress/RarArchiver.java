package com.cjh.lib_basissdk.compress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

import android.os.Handler;
import android.util.Log;

public class RarArchiver extends BaseArchiver {

	@Override
	public void doArchiver(File[] files, String destPath) {

	}

	@Override
	public void doUnArchiver(final Handler handler, String rarPath, String unrarPath,
                             final IArchiverListener listener) {
		final File file = new File(rarPath);
        if (null == unrarPath || "".equals(unrarPath)) {
            unrarPath = file.getParentFile().getPath();
        }
        // 保证文件夹路径最后是"/"或者"\"
        char lastChar = unrarPath.charAt(unrarPath.length() - 1);
        if (lastChar != '/' && lastChar != '\\') {
            unrarPath += File.separator;
        }
        final String finalUnrarPath = unrarPath;
        Log.d(TAG, "unrar file to :" + unrarPath);
        if (listener != null)
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onStartArchiver(file);
                }
            });
        
        FileOutputStream fileOut = null;
        Archive rarFile = null;
        try {
			rarFile = new Archive(file, null);
			FileHeader fileHeader = null;
			List fileHeaders = rarFile.getFileHeaders();
			final int total = fileHeaders.size();
			for (int i = 0; i < total; i++) {
				fileHeader = (FileHeader) fileHeaders.get(i);
				String entrypath = "";
                //解決中文乱码
				if (fileHeader.isUnicode()) {
                    entrypath = fileHeader.getFileNameW().trim();
                } else {
                    entrypath = fileHeader.getFileNameString().trim();
                }
				entrypath = entrypath.replaceAll("\\\\", "/");
				
				File unFile = new File(unrarPath + entrypath);
                Log.d(TAG, "unrar entry file :" + file.getPath());
                
                if (fileHeader.isDirectory()) {
                	unFile.mkdirs();
                } else {
                    File parent = unFile.getParentFile();
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(unFile);
                    rarFile.extractFile(fileHeader, fileOut);
                    fileOut.close();
                }
                if (listener != null) {
                    final int current = i;
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onProgressArchiver(current + 1, total);
                        }
                    });
                }
			}
			rarFile.close();
		} catch (final Exception e) {
			e.printStackTrace();
			if (listener != null)
	            mMainHandler.post(new Runnable() {
	                @Override
	                public void run() {
	                    listener.onErrorArchiver(e.getMessage());
	                }
	            });
		} finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                    fileOut = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (rarFile != null) {
                try {
                    rarFile.close();
                    rarFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (listener != null) {
            	file.delete();
            	mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onEndArchiver(finalUnrarPath);
                    }
                });
            }
            //handler.sendEmptyMessage(DownloadStatus.FINISH);
        }
	}

}
