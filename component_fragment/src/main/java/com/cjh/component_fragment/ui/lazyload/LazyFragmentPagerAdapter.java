package com.cjh.component_fragment.ui.lazyload;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/8/27 16:34
 * @description:
 */
public class LazyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public LazyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
