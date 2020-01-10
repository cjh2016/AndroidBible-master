package com.cjh.component_fragment.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
