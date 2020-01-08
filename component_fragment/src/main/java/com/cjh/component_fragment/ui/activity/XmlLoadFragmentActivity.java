package com.cjh.component_fragment.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cjh.component_fragment.R;

/**
 * @author: caijianhui
 * @date: 2019/9/23 14:16
 * @description:
 */
public class XmlLoadFragmentActivity extends AppCompatActivity {

    Fragment xmlLoadFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_load_fragment);
    }
}
