package com.cjh.component_videoplayer.sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cjh.component_videoplayer.R;
import com.cjh.component_videoplayer.sample.bean.VideoBean;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/9/18 12:28
 * @description:
 */
public class PlayPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<VideoBean> mItems;

    public PlayPagerAdapter(Context context, List<VideoBean> list){
        this.mContext = context;
        this.mItems = list;
    }

    @Override
    public int getCount() {
        if(mItems!=null)
            return mItems.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        VideoBean bean = mItems.get(position);
        View itemView = View.inflate(mContext, R.layout.item_pager_play, null);
        FrameLayout playerContainer = itemView.findViewById(R.id.playerContainer);
        playerContainer.setTag(bean.getPath());
        ImageView coverView = itemView.findViewById(R.id.iv_cover);
        /*GlideApp.with(mContext)
                .load(bean.getPath())
                .centerInside()
                .into(coverView);*/
        Glide.with(mContext)
                .load(bean.getPath())
                .into(coverView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
