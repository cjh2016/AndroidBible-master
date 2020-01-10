package com.cjh.component_fragment.ui.lazyload;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.cjh.component_fragment.R;

/**
 * @author: caijianhui
 * @date: 2019/8/27 16:30
 * @description:
 */
public class TwoFragment extends BaseFragment {

    private AppCompatTextView aTv;

    @Override
    protected void initView(View view) {
        aTv = view.findViewById(R.id.two_fragment_content);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_two;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        aTv.setText("two fragment lazyFetchData");
    }
}
