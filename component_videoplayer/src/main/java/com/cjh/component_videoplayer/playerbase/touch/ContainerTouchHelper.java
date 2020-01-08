package com.cjh.component_videoplayer.playerbase.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:37
 * @description:
 */
public class ContainerTouchHelper {

    private GestureDetector mGestureDetector;
    private BaseGestureCallbackHandler mGestureCallback;

    public ContainerTouchHelper(Context context, BaseGestureCallbackHandler gestureCallback){
        this.mGestureCallback = gestureCallback;
        mGestureDetector = new GestureDetector(context,gestureCallback);
    }

    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                onEndGesture(event);
                break;
        }
        return mGestureDetector.onTouchEvent(event);
    }

    public void setGestureEnable(boolean enable) {
        this.mGestureCallback.setGestureEnable(enable);
    }

    public void setGestureScrollEnable(boolean enable) {
        this.mGestureCallback.setGestureScrollEnable(enable);
    }

    public void onEndGesture(MotionEvent event) {
        mGestureCallback.onEndGesture(event);
    }

}
