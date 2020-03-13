package com.cjh.component_materialdesign.behavoir

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.cjh.component_materialdesign.MaterialDesignSimpleApplication
import com.cjh.component_materialdesign.R


class CoverHeaderScrollBehavior(context: Context, attributes: AttributeSet)
    : CoordinatorLayout.Behavior<View>(context, attributes) {

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        Log.i(TAG,"onLayoutChild.....")
        val params = child.layoutParams as CoordinatorLayout.LayoutParams
        if ((null != params) and (params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT)) {
            child.layout(0, getHeaderHeight(), parent.width, parent.height)
            //child.translationY = getHeaderHeight().toFloat()
            return true
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        Log.i(TAG,"onStartNestedScroll.....")

        return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) !== 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        Log.i(TAG,"onNestedPreScroll.....  dy = $dy")


        // 在这个方法里面只处理向上滑动
        if(dy < 0){
            return
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        //Log.i(TAG,"onNestedScroll.....  dyConsumed = $dyConsumed")

        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
    }


    fun getHeaderHeight(): Int {
        return MaterialDesignSimpleApplication.sContext?.resources
            ?.getDimensionPixelOffset(R.dimen.header_height)!!
    }

    companion object {
        var TAG = CoverHeaderScrollBehavior::class.java.simpleName
    }
}