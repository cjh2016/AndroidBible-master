package com.cjh.component_materialdesign.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cjh.component_materialdesign.R

/**
 * @author: caijianhui
 * @date: 2020/1/11 15:26
 * @description:
 */
class SecondFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, null)
    }


}