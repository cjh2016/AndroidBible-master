package com.cjh.lib_basissdk.compress;

import java.io.File;

public interface IArchiverListener {
	
	void onStartArchiver(File archiverFile);
	
	void onProgressArchiver(int current, int total);
	
	void onEndArchiver(String unarchiverPath);
	
	void onErrorArchiver(String errorMsg);
}
