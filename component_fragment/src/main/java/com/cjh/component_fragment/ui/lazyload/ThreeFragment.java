package com.cjh.component_fragment.ui.lazyload;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.cjh.component_fragment.R;

/**
 * @author: caijianhui
 * @date: 2019/8/27 16:30
 * @description:
 */
public class ThreeFragment extends BaseFragment {

    private AppCompatTextView aTv;

    @Override
    protected void initView(View view) {
        aTv = view.findViewById(R.id.three_fragment_content);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_three;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        aTv.setText("three fragment lazyFetchData");
    }
}
