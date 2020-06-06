package com.cjh.lib_common.dialog;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({DialogProperty.MARGIN,
			DialogProperty.WIDTH,
			DialogProperty.HEIGHT,
			DialogProperty.DIM,
			DialogProperty.BOTTOM,
			DialogProperty.CANCEL,
			DialogProperty.ANIM,
			DialogProperty.LAYOUT})
@Retention(RetentionPolicy.RUNTIME)
public @interface DialogProperty {
	
	String MARGIN = "margin";
	String WIDTH = "width";
	String HEIGHT = "height";
	String DIM = "dim_amount";
	String BOTTOM = "show_bottom";
	String CANCEL = "out_cancel";
	String ANIM = "anim_style";
	String LAYOUT = "layout_id";
}
