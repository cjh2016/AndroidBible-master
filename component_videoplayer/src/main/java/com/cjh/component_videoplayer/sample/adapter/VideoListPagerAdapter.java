package com.cjh.component_videoplayer.sample.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
