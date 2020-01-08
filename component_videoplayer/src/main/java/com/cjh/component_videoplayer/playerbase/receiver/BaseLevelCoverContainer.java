package com.cjh.component_videoplayer.playerbase.receiver;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.cjh.component_videoplayer.playerbase.log.PLog;

/**
 * @author: caijianhui
 * @date: 2019/8/9 16:18
 * @description:
 */
public abstract class BaseLevelCoverContainer extends AbsCoverContainer {

    private final String TAG = "base_cover_container";

    public BaseLevelCoverContainer(Context context) {
        super(context);
        initLevelContainers(context);
    }

    @Override
    protected void onCoverAdd(BaseCover cover) {

    }

    @Override
    protected void onAvailableCoverAdd(BaseCover cover) {
        PLog.d(TAG,"on available cover add : now count = " + getCoverCount());
    }

    @Override
    protected void onCoverRemove(BaseCover cover) {
        PLog.d(TAG,"on cover remove : now count = " + getCoverCount());

    }

    @Override
    protected void onAvailableCoverRemove(BaseCover cover) {

    }

    @Override
    protected void onCoversRemoveAll() {
        PLog.d(TAG,"on covers remove all ...");

    }

    @Override
    protected ViewGroup initContainerRootView() {
        FrameLayout root = new FrameLayout(mContext);
        root.setBackgroundColor(Color.TRANSPARENT);
        return root;
    }

    protected abstract void initLevelContainers(Context context);

    protected void addLevelContainerView(ViewGroup container, ViewGroup.LayoutParams layoutParams){
        if(getContainerView()!=null){
            if(layoutParams==null)
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getContainerView().addView(container,layoutParams);
        }
    }

}
