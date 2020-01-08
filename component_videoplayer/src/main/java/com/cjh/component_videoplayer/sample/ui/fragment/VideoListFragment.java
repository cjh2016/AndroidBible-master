package com.cjh.component_videoplayer.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjh.component_videoplayer.R;
import com.cjh.component_videoplayer.sample.adapter.ListAdapter;
import com.cjh.component_videoplayer.sample.bean.VideoBean;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:14
 * @description:
 */
public class VideoListFragment extends Fragment implements ListAdapter.OnListListener {

    private static final String KEY_FRAG_INDEX = "frag_index";

    private RecyclerView mRecycler;
    private ListAdapter mListAdapter;

    private int mFragIndex;

    private boolean hasInit;

    public static VideoListFragment create(int fragIndex) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_FRAG_INDEX, fragIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, null);
        mRecycler = root.findViewById(R.id.fragment_recycler);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            mFragIndex = bundle.getInt(KEY_FRAG_INDEX);
        }
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mListAdapter = new ListAdapter(getContext(), mRecycler);
    }

    @Override
    public void onTitleClick(ListAdapter.VideoItemHolder holder, VideoBean item, int position) {

    }

    @Override
    public void playItem(ListAdapter.VideoItemHolder holder, VideoBean item, int position) {

    }
}
