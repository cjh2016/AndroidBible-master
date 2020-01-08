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
 * @date: 2019/8/27 10:16
 * @description:
 */
public class ShowFragment extends Fragment {

    private static final String TAG = "ShowHideFragment";

    public static ShowFragment newInstance() {
        ShowFragment showFragment = new ShowFragment();
        return showFragment;
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.i(TAG, "ShowFragment onInflate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "ShowFragment onAttach");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ShowFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "ShowFragment onCreateView");
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "ShowFragment onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "ShowFragment onActivityCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "ShowFragment onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "ShowFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "ShowFragment onResume");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "ShowFragment onCreateOptionsMenu");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.i(TAG, "ShowFragment onPrepareOptionsMenu");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "ShowFragment onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "ShowFragment onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "ShowFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "ShowFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ShowFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "ShowFragment onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "ShowFragment onHiddenChanged = " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "ShowFragment setUserVisibleHint = " + isVisibleToUser);
    }

}
