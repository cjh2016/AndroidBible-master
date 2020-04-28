package com.cjh.component_xfermode.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cjh.component_xfermode.R
import com.cjh.component_xfermode.bean.XFerModeBean
import kotlinx.android.synthetic.main.item_xfermode.view.*

/**
 * @author: caijianhui
 * @date: 2020/4/28 16:50
 * @description:
 */
class XFerModeAdapter(context: Context, data: List<XFerModeBean>) : RecyclerView.Adapter<XFerModeAdapter.ViewHolder>() {

    private var listener: XFerModeListener? = null
    private lateinit var mInflater: LayoutInflater
    private lateinit var mData: List<XFerModeBean>

    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_xfermode, parent, false))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position
        val item = mData[position]
        holder.checkbox.isChecked = item.isSelect
        holder.tvName.text = item.name
        holder.llItem.setOnClickListener {
            listener?.onClickItem(pos)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var llItem: LinearLayout
        lateinit var checkbox: CheckBox
        lateinit var tvName: TextView

        init {
            llItem = itemView.findViewById(R.id.ll_item)
            checkbox = itemView.findViewById(R.id.checkbox)
            tvName = itemView.findViewById(R.id.tv_name)
        }

    }

    interface XFerModeListener {
        fun onClickItem(position: Int)
    }

}