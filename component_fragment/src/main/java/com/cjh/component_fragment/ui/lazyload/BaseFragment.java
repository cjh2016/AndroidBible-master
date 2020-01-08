package com.cjh.component_fragment.ui.lazyload;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author: caijianhui
 * @date: 2019/8/27 14:41
 * @description:
 */
public abstract class BaseFragment extends SupportFragment {

    private final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected View rootView; //缓存Fragment view
    private boolean isViewPrepared;  // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据

    /**
     * 通过xml定义的Fragment才会走这个生命周期
     */
    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.i(TAG, "onInflate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
        if (null != context) {
            mContext = context;
        } else {
            mContext = getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if (null == rootView) {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootView已经有parent的错误。
            rootView = inflater.inflate(getLayout(), container, false);
            Log.i(TAG, "onCreateView inflate");
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (null != parent) {
            parent.removeView(rootView);  //如果这个view添加进过一个容器中, 则先从容器中移出
        }
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
        initEvent();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Log.i(TAG, "onPrepareOptionsMenu");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
        //view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }

    /**
     * 这个可能被用来在一组有序的fragmen里, 例如fragment生命周期的更新。告诉我们这个方法被调用希望在一个pager里，
     * 因此FragmentPagerAdapter可以使用这个，然后调用这个setUserVisibleHint()在fragment里，因此有些人也建议重写
     * setUserVisibleHint()来知道当前一个fragment对用户来说是隐藏还是显示，这个方法仅仅工作在FragmentPagerAdapter中，
     * 不能被使用在一个普通的activity中。
     *
     * 因此单个fragment是不会被调用setUserVisibleHint()的, 可以重写onHiddenChanged()方法来得知当前fragment的状态。
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "setUserVisibleHint isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
        Log.i(TAG, "lazyFetchData");
    }

    public String getName() {
        return BaseFragment.class.getName();
    }

    protected abstract int getLayout();

    protected void initView(View view) {
    }

    protected void initEvent() {
    }

}
