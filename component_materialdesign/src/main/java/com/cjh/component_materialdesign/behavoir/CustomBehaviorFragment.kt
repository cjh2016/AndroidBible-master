package com.cjh.component_materialdesign.behavoir

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cjh.component_materialdesign.BaseFragment
import com.cjh.component_materialdesign.R

class CustomBehaviorFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_behavior, container, false)
    }

    companion object {
        private var sInstance: BaseFragment? = null

        fun newInstance(): CustomBehaviorFragment? {
            if (null == sInstance) {
                sInstance = CustomBehaviorFragment()
            }
            return sInstance as CustomBehaviorFragment
        }
    }
}