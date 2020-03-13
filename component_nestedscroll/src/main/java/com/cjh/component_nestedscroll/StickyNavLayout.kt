package com.cjh.component_nestedscroll

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Interpolator
import android.util.AttributeSet
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.OverScroller
import androidx.viewpager.widget.ViewPager


class StickyNavLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int)
    : LinearLayout(context, attributeSet, defStyleAttr) {

    private lateinit var mTop: View
    private lateinit var mNav: View
    private lateinit var mViewPager: ViewPager

    private var mTopViewHeight = 0

    private lateinit var mScroller: OverScroller
    private var mVelocityTracker: VelocityTracker? = null
    private lateinit var mOffsetAnimator: ValueAnimator
    private lateinit var mInterpolator: Interpolator

    private var mTouchSlop: Int = 0
    private var mMaximumVelocity: Int = 0
    private var mMinimumVelocity: Int = 0

    private var mLastY: Float = 0f
    private var mDragging: Boolean = false



    init {

        orientation = VERTICAL

        mScroller = OverScroller(context)

        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

        mMaximumVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
        mMinimumVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    }

    private fun initVelocityTrackerIfNotExists() {
        if (null == mVelocityTracker)
            mVelocityTracker = VelocityTracker.obtain()
    }


    private fun recycleVelocityTracker() {

        mVelocityTracker?.let {
            it.recycle()
            mVelocityTracker = null
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mTop = findViewById(R.id.id_stickynavlayout_topview)
        mNav = findViewById(R.id.id_stickynavlayout_indicator)
        val view = findViewById<View>(R.id.id_stickynavlayout_viewpager)

        if (view !is ViewPager) {
            throw RuntimeException("id_stickynavlayout_viewpager show used by ViewPager !")
        }

        mViewPager = view
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getChildAt(0)?.run {
            measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        }
        val params = mViewPager.layoutParams
        params.height = measuredHeight - mNav.measuredHeight
        setMeasuredDimension(measuredWidth,
            mTop.measuredHeight + mNav.measuredHeight + mViewPager.measuredHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = mTop.measuredHeight
    }

    fun fling(velocityY: Int) {
        mScroller.fling(0, scrollY, 0, velocityY, 0, 0,
            0, mTopViewHeight)
        invalidate()
    }


    override fun scrollTo(x: Int, y: Int) {
        var y = y
        if (y < 0)
        {
            y = 0
        }
        if (y > mTopViewHeight)
        {
            y = mTopViewHeight
        }
        if (y != scrollY)
        {
            super.scrollTo(x, y)
        }
    }


    override fun computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            invalidate()
        }

    }


    companion object {
        val TAG = StickyNavLayout::class.java
    }
}