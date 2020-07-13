package com.cjh.lib_basissdk.util;

import jp.wasabeef.glide.transformations.BlurTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class GlideUtils {
	
	public static void loadImg(Context context, String url, int defaultLoadingImg, ImageView imageView) {
		try {
			RequestOptions options = new RequestOptions()
			.placeholder(defaultLoadingImg)  
			.error(defaultLoadingImg)   
			.diskCacheStrategy(DiskCacheStrategy.RESOURCE) 
			.priority(Priority.HIGH);
		Glide.with(context)
			.load(url)
			.apply(options)
			.into(imageView);
		} catch (Exception e) {
			return;
		}
	}
	
	public static void loadImgBlur(Context context, String url, ImageView imageView) {
		try {
			RequestOptions options = new RequestOptions()
					.priority(Priority.HIGH)
					.bitmapTransform(new BlurTransformation(16));
			Glide.with(context)
					.load(url)
					.apply(options)
					.into(imageView);
		} catch (Exception exception) {
			return;
		}
	}
	
	public static void loadImgCache(Fragment fragment, String url, int placeRes,
									int errorRes, int width, int height, ImageView imageView) {
		try {
			RequestOptions options = new RequestOptions()
					.priority(Priority.HIGH)
					.placeholder(placeRes)
					.error(errorRes)
					.fallback(placeRes)
					.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
					.fitCenter()
					.override(width, height);
			Glide.with(fragment)
					.load(url)
					.apply(options)
					.into(imageView);
		} catch (Exception e) {
			return;
		}
	}
	
	public static void loadViewImg(final Fragment fragment, final String url, final int placeRes, final int errorRes, final View view) {
		try {
			RequestOptions options = new RequestOptions()
					.priority(Priority.HIGH)
					.placeholder(placeRes)
					.error(errorRes)
					.fallback(placeRes)
					.diskCacheStrategy(DiskCacheStrategy.DATA);
					//.fitCenter();
			Glide.with(fragment)
					.asDrawable()
					.load(url)
					.apply(options)
					.thumbnail(0.1f)
					.into(new ViewTarget<View, Drawable>(view) {

						@Override
						public void onResourceReady(Drawable drawable,
								Transition<? super Drawable> transition) {
							if (drawable == null)
					            return;
							if (view instanceof ImageView) {
								((ImageView) view).setImageDrawable(drawable);
							} else if (view instanceof ImageButton) {
								((ImageButton) view).setImageDrawable(drawable);
							} else {
								view.setBackground(drawable);
							}
						}
					});
			
		} catch (Exception e) {
			return;
		}
	}
	
	/*public class CustomDiskPathGlideModule implements GlideModule {

		private String diskPath = StorageUtils.getInstance(HRContext.getInstance())
				.autoMatchSpecifiedFile(VolumeType.USB, "boll/network/glide").getAbsolutePath(); 
		
		private int cacheSizeBytes = 80 * 1024 * 1024;
		
		@Override
		public void registerComponents(Context context, Glide glide, Registry registry) {
		}

		@Override
		public void applyOptions(Context context, GlideBuilder glidebuilder) {
			glidebuilder.setDiskCache(new DiskLruCacheFactory(diskPath, cacheSizeBytes));
		}
		
	}*/
}
