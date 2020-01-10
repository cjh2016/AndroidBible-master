package com.cjh.component_textview.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.cjh.component_textview.R
import com.cjh.component_textview.span.RectSpan
import com.cjh.component_textview.span.SparkSpan
import kotlinx.android.synthetic.main.activity_span.*

/**
 * @author: caijianhui
 * @date: 2019/10/10 13:48
 * @description:
 */
class SpanActivity : AppCompatActivity(), View.OnClickListener {

    private var mSmoothInterpolator: AccelerateDecelerateInterpolator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span)
        spanAnimation.setOnClickListener(this)
        val spannableString = SpannableString(CONTENT)
        spanTextView.text = spannableString
        mSmoothInterpolator = AccelerateDecelerateInterpolator()
        title = HEAD
    }

    override fun onClick(v: View?) {
        Log.e("test","onClick")
        //useRectSpan()
        useSparkleSpan()

    }

    private fun useRectSpan() {
        val span = RectSpan()
        val spannableString = SpannableString(CONTENT)
        spannableString.setSpan(span, 0, spannableString.length / 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanTextView.text = spannableString
    }

    private fun useSparkleSpan() {
        val spannableString = SpannableString(CONTENT)
        val span = SparkSpan(this)
        spannableString.setSpan(span, 0, spannableString.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanTextView.text = spannableString
        val objectAnimator = ObjectAnimator.ofFloat(span, SparkSpan.SPARK_SPAN_FLOAT_PROPERTY, 0.0f, 1.0f)
        objectAnimator.addUpdateListener {
            spanTextView.text = spannableString
        }
        objectAnimator.interpolator = mSmoothInterpolator
        objectAnimator.duration = 600
        objectAnimator.start()
    }

    companion object {
        private const val CONTENT = "This is a demo for span"
        private const val HEAD = "This is a demo for span"

    }


}