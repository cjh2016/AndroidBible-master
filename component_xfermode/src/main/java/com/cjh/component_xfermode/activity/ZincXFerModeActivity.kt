package com.cjh.component_xfermode.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjh.component_xfermode.R
import com.cjh.component_xfermode.XFerModeInfo
import com.cjh.component_xfermode.adapter.XFerModeAdapter
import com.cjh.component_xfermode.bean.XFerModeBean
import kotlinx.android.synthetic.main.activity_zinc_xfermode.*


/**
 * @author: caijianhui
 * @date: 2020/4/29 9:50
 * @description:
 */
class ZincXFerModeActivity : AppCompatActivity(), XFerModeInfo, XFerModeAdapter.XFerModeListener {


    private lateinit var mAdapter: XFerModeAdapter

    private val xfermodeList = ArrayList<XFerModeBean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zinc_xfermode)

        createXFerModeData()

        initView()
    }

    private fun initView() {
        mAdapter = XFerModeAdapter(this, xfermodeList)
        mAdapter.listener = this
        recycle_view.adapter = mAdapter
        recycle_view.layoutManager = LinearLayoutManager(this)

        val selXFerModeBean = getSelXFerModeBean()
        iv_dst.setImageBitmap(selXFerModeBean?.dstBitmap)
        iv_src.setImageBitmap(selXFerModeBean?.srcBitmap)

        xfermode_view.setBitmap(
            selXFerModeBean?.dst!!,
            selXFerModeBean?.src!!,
            selXFerModeBean.dstBitmap,
            selXFerModeBean.srcBitmap
        )
    }

    private fun createXFerModeData() {

        xfermodeList.add(
            XFerModeBean(
                this,
                "zinc",
                true,
                R.drawable.dst,
                R.drawable.src
            )
        )

        xfermodeList.add(
            XFerModeBean(
                this,
                "monkey",
                false,
                R.drawable.monkey,
                R.drawable.white_shadow_to_alpha
            )
        )

        xfermodeList.add(
            XFerModeBean(
                this,
                "spider",
                false,
                R.drawable.spider_black,
                R.drawable.spider
            )
        )

        xfermodeList.add(
            XFerModeBean(
                this,
                "spider_circle",
                false,
                R.drawable.spider_black,
                R.drawable.circle
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        for (xFerModeBean in xfermodeList) {
            xFerModeBean.recycle()
        }

        xfermodeList.clear()
    }

    override fun getSelXFerModeBean(): XFerModeBean? {

        for (bean in xfermodeList) {
            if (bean.isSelect) {
                return bean
            }
        }
        return null
    }

    override fun onClickItem(position: Int) {

        for (pos in 0 until xfermodeList.size) {
            val item = xfermodeList[pos]
            if (pos == position) {
                item.isSelect = true
                iv_dst.setImageBitmap(item.dstBitmap)
                iv_src.setImageBitmap(item.srcBitmap)
                xfermode_view.setBitmap(item.dst, item.src, item.dstBitmap, item.srcBitmap)
            } else {
                item.isSelect = false
            }
        }
        mAdapter.notifyDataSetChanged()
    }

}