package com.cjh.component_fragment.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.cjh.component_fragment.R;
import com.cjh.component_fragment.ui.fragment.HideFragment;
import com.cjh.component_fragment.ui.fragment.ShowFragment;


/**
 * @author: caijianhui
 * @date: 2019/8/27 11:18
 * @description:
 */
public class ReplaceFragmentActivity extends AppCompatActivity {

    private Fragment hideFragment, showFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_fragment);
        hideFragment = HideFragment.newInstance();
        showFragment = ShowFragment.newInstance();
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.replace_fragment_container, hideFragment)
                .add(R.id.replace_fragment_container, showFragment)
                .hide(hideFragment)
                .commit();*/
    }

    public void replaceShowFragment(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_fragment_container, showFragment).commit();
    }

    public void replaceHideFragment(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_fragment_container, hideFragment).commit();
    }
}
