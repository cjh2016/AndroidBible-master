package com.cjh.component_fragment.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cjh.component_fragment.R;
import com.cjh.component_fragment.ui.fragment.HideFragment;
import com.cjh.component_fragment.ui.fragment.ShowFragment;


/**
 * @author: caijianhui
 * @date: 2019/8/27 11:18
 * @description:
 */
public class HideShowFragmentActivity extends AppCompatActivity {

    private Fragment hideFragment, showFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_show_fragment);
        hideFragment = HideFragment.newInstance();
        showFragment = ShowFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.replace_fragment_container, hideFragment)
                .add(R.id.replace_fragment_container, showFragment)
                .hide(hideFragment)
                .commit();
    }

    public void showShowFragment(View view) {
        getSupportFragmentManager().beginTransaction().hide(hideFragment).show(showFragment).commit();
    }

    public void showHideFragment(View view) {
        getSupportFragmentManager().beginTransaction().hide(showFragment).show(hideFragment).commit();
    }
}
