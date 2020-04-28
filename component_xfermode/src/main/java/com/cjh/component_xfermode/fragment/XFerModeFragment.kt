package com.cjh.component_xfermode.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cjh.component_xfermode.R
import com.cjh.component_xfermode.activity.XFerModeItemActivity
import com.cjh.component_xfermode.widget.ItemXFerModeView
import com.cjh.component_xfermode.widget.ZincXFerModeView
import kotlinx.android.synthetic.main.fragment_xfermode_item.*

/**
 * @author: caijianhui
 * @date: 2020/4/28 19:47
 * @description:
 */
class XFerModeFragment : Fragment() {

    private var index = 0
    private lateinit var dstBitmap: Bitmap
    private lateinit var srcBitmap: Bitmap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_xfermode_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = arguments!!.getInt(XFerModeItemActivity.INDEX, 0)
        dstBitmap =
            BitmapFactory.decodeResource(resources, arguments!!.getInt(XFerModeItemActivity.DST))
        srcBitmap =
            BitmapFactory.decodeResource(resources, arguments!!.getInt(XFerModeItemActivity.SRC))

        tv_label.text = ZincXFerModeView.sLabels[index]
        xfermode_view.mCurXfermode = ZincXFerModeView.sModes[index]
        xfermode_view.setBitmap(dstBitmap, srcBitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        dstBitmap.recycle()
        srcBitmap.recycle()
    }

    companion object {
        fun getInstance(index: Int, dst: Int, src: Int): XFerModeFragment {
            val fragment = XFerModeFragment()
            val bundle = Bundle()
            bundle.putInt(XFerModeItemActivity.INDEX, index)
            bundle.putInt(XFerModeItemActivity.DST, dst)
            bundle.putInt(XFerModeItemActivity.SRC, src)

            fragment.arguments = bundle
            return fragment
        }
    }
}
