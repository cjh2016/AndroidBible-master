package com.cjh.component_fragment.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjh.component_fragment.R;

/**
 * @author: caijianhui
 * @date: 2019/9/23 14:26
 * @description:
 */
public class XmlFragment extends Fragment {

    private static final String TAG = XmlFragment.class.getSimpleName();

    public static XmlFragment newInstance() {
        XmlFragment xmlFragment = new XmlFragment();
        return xmlFragment;
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.i(TAG, "XmlFragment onInflate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "XmlFragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "XmlFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "XmlFragment onCreateView");
        return inflater.inflate(R.layout.fragment_xml, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "XmlFragment onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "XmlFragment onActivityCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "XmlFragment onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "XmlFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "XmlFragment onResume");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "XmlFragment onCreateOptionsMenu");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.i(TAG, "XmlFragment onPrepareOptionsMenu");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "XmlFragment onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "XmlFragment onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "XmlFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "XmlFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "XmlFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "XmlFragment onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "XmlFragment onHiddenChanged = " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "XmlFragment setUserVisibleHint = " + isVisibleToUser);
    }
}
