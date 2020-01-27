package com.cjh.component_materialdesign.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cjh.component_materialdesign.BaseFragment
import com.cjh.component_materialdesign.R

class XmlTabLayoutFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tablayout_xml,
            container, false)
    }

    companion object {

        private var sInstance: BaseFragment? = null

        fun newInstance(): XmlTabLayoutFragment? {
            if (null == sInstance) {
                sInstance = XmlTabLayoutFragment()
            }
            return sInstance as XmlTabLayoutFragment
        }
    }
}