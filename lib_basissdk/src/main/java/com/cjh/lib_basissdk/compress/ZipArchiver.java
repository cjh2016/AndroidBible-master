package com.cjh.lib_basissdk.compress;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import android.os.Handler;
import android.text.TextUtils;

public class ZipArchiver extends BaseArchiver {

	@Override
	public void doArchiver(File[] files, String destPath) {

	}

	@Override
	public void doUnArchiver(final Handler handler, String zipPath, final String unzipPath,
							 final IArchiverListener listener) {
		if (TextUtils.isEmpty(zipPath) || TextUtils.isEmpty(unzipPath)) {
			return;
		}
		final File file = new File(zipPath);
		if (!file.exists()) {
			return;
		}
		try {
			ZipFile zipFile = new ZipFile(zipPath);
			//这句话必须设置,不然中文会乱码
			//zipFile.setFileNameCharset("GBK");
			zipFile.setCharset(Charset.forName("GBK"));
			if (!zipFile.isValidZipFile()) {
				if (listener != null) {
					mMainHandler.post(new Runnable() {
						@Override
						public void run() {
							listener.onErrorArchiver("文件不合法!");
						}
					});
				}
				return;
			}
			
			File destDir = new File(unzipPath);
            if (destDir.isDirectory() && !destDir.exists()) {
                destDir.mkdir();
            }
            
			if (listener != null) {
				mMainHandler.post(new Runnable() {
					@Override
					public void run() {
						listener.onStartArchiver(file);
					}
				});
			}
			
			FileHeader fileHeader = null;
			List fileHeaders = zipFile.getFileHeaders();
			final int total = fileHeaders.size();
			for (int i = 0; i < total; i++) {
				fileHeader = (FileHeader) fileHeaders.get(i);
				zipFile.extractFile(fileHeader, unzipPath);
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
				
		} catch (final ZipException e) {
			e.printStackTrace();
			if (listener != null) 
				mMainHandler.post(new Runnable() {
					@Override
					public void run() {
						listener.onErrorArchiver(e.getMessage());
					}
				});
		}
		
		if (listener != null) {
			file.delete();
			mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onEndArchiver(unzipPath);
                }
            });
		}
		//handler.sendEmptyMessage(DownloadStatus.FINISH);
	}

}
