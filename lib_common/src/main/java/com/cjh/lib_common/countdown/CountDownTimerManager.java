package com.cjh.lib_common.countdown;

import android.os.Handler;

import androidx.annotation.NonNull;

/**
 * 倒计时控制器 
 */
public abstract class CountDownTimerManager {

	private long mTotal;
	private Handler mHandler;
	private long mCurrent;
	private long mStartTime;
	private boolean mCancelled;
	//默认1秒间隔
	private static long mInterval = 1000;
	
	public CountDownTimerManager(@NonNull long total, @NonNull long interval) {
		
		mTotal = total;
		mInterval = interval;
		mHandler = new Handler();
	}
	
	public CountDownTimerManager(long total) {
		this(total, mInterval);
	}
	
	public CountDownTimerManager start() {
		
		mCancelled = false;
		if (mTotal <= 0) {
			onDone();
			return this;
		}
		
		mStartTime = System.currentTimeMillis();
		onProgress(mCurrent);
		mCurrent++;
		mHandler.postDelayed(countDownRunnable, mInterval);
		return this;
	}
	
	public void cancel() {
		
		mCancelled = true;
		if (mHandler != null) {
			mHandler.removeCallbacks(countDownRunnable);
		}
	}
	
	private Runnable countDownRunnable = new Runnable() {
		
		@Override
		public void run() {
			
			if (mCancelled) {
				return;
			}
			
			long mProgress = (int) (mTotal - mCurrent);
			if (mProgress > 0) {
				onProgress(mProgress);
				mCurrent++;
				//这句要理解下怎么减少误差,见 https://juejin.im/post/5974cb0b5188255ae666d3c2
				long interval = (mCurrent * mInterval) - ((System.currentTimeMillis() - mStartTime));
				mHandler.postDelayed(countDownRunnable, interval);
			} else {
				onDone();
			}
		}
	};
	
	public abstract void onProgress(long progress);
		
	public abstract void onDone();

}
