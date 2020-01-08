package com.cjh.component_fragment.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cjh.component_fragment.R;
import com.cjh.component_fragment.ui.fragment.HideFragment;
import com.cjh.component_fragment.ui.fragment.ShowFragment;
import com.cjh.component_fragment.ui.lazyload.LazyFragmentPagerAdapter;
import com.cjh.component_fragment.ui.lazyload.OneFragment;
import com.cjh.component_fragment.ui.lazyload.ThreeFragment;
import com.cjh.component_fragment.ui.lazyload.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;


/**
 * @author: caijianhui
 * @date: 2019/8/27 11:18
 * @description:
 */
public class LazyLoadFragmentActivity extends SupportActivity {

    private Fragment oneFragment, twoFragment, threeFragment;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private LazyFragmentPagerAdapter lazyFragmentPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load_fragment);
        init();
    }

    private void init() {
        viewPager = findViewById(R.id.vp_lazy_load_fragment);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fragments = new ArrayList<>();
        fragments.add(oneFragment);
        fragments.add(twoFragment);
        fragments.add(threeFragment);
        lazyFragmentPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(lazyFragmentPagerAdapter);
    }


}
