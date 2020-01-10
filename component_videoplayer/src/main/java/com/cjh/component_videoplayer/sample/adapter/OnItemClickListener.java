package com.cjh.component_videoplayer.sample.adapter;


import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: caijianhui
 * @date: 2019/9/5 11:19
 * @description:
 */
public interface OnItemClickListener<H extends RecyclerView.ViewHolder,  T> {

    void onItemClick(H holder, T item, int position);
}
