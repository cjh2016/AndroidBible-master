package com.cjh.component_videoplayer.sample.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:09
 * @description:
 */
public class VideoListPagerAdapter extends FragmentStatePagerAdapter {



    public VideoListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
