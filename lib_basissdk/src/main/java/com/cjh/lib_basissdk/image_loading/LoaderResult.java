package com.cjh.lib_basissdk.image_loading;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class LoaderResult {
	
	View target;
	String uri;
	Bitmap bitmap;
	
	public LoaderResult(View target, String uri, Bitmap bitmap) {
        this.target = target;
        this.uri = uri;
        this.bitmap = bitmap;
   }
}
