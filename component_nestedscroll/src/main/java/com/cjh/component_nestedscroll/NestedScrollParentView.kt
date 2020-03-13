package com.cjh.component_nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout


class NestedScrollParentView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attributeSet, defStyleAttr) {

    private var mHeader: View? = null
    private var mHeaderHeight = 0

    /**
     * 这个方法如果返回true的话代表接受由内层传来的滚动消息,我们直接返回true就好,否则后面的消息都接受不到
     */
    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return true
    }

    /**
     * 在内层view处理滚动事件前先被调用,可以让外层view先消耗部分滚动，也就是这个方法可以让NestedScrollParentView滚动
     *
     */
    override fun onNestedPreScroll(target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        Log.i("NestedScrollParentView", "onNestedPreScroll scrollY=$scrollY")
        super.onNestedPreScroll(target, dx, dy, consumed)
        val headerScrollUp = dy > 0 && scrollY < mHeaderHeight
        val headerScrollDown = dy < 0 && scrollY > 0 && !target!!.canScrollVertically(-1)

        if (headerScrollUp || headerScrollDown) {
            //通过这个方法滚动NestedScrollParentView
            scrollBy(0, dy)
            consumed!![1] = dy
        }
    }

    override fun onNestedPreFling(target: View?, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(target, velocityX, velocityY)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            mHeader = getChildAt(0)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeaderHeight = mHeader?.measuredHeight!!
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        mHeader?.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        val totalHeight = MeasureSpec.getSize(heightMeasureSpec) + mHeader?.measuredHeight!!
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(totalHeight, mode))
    }

    override fun scrollTo(x: Int, y: Int) {
        var y = y
        if (y < 0) {
            y = 0
        } else if (y > mHeaderHeight) {
            y = mHeaderHeight
        }
        super.scrollTo(x, y)
    }


}