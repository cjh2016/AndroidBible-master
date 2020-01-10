package com.cjh.component_fragment.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cjh.component_fragment.R;
import com.cjh.component_fragment.ui.fragment.NormalFragment1;
import com.cjh.component_fragment.ui.fragment.NormalFragment2;


/**
 * @author: caijianhui
 * @date: 2019/8/27 11:18
 * @description:
 */
public class NormalFragmentActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_fragment);
    }

    public void addNormalFragment1(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.replace_fragment_container, NormalFragment1.newInstance()).commit();
    }

    public void addNormalFragment2(View view) {
        getSupportFragmentManager().beginTransaction().add(R.id.replace_fragment_container, NormalFragment2.newInstance()).commit();
    }
}
