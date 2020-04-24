package com.cjh.component_customview.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ProgressBar
import com.cjh.component_customview.R
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @author: caijianhui
 * @date: 2020/4/22 13:49
 * @description:
 */
class TolySeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1)
    : ProgressBar(context, attrs, defStyle) {


    private lateinit var mPaint: Paint
    private var mSBWidth: Int = 0
    private lateinit var mRectF: RectF
    private lateinit var mPath: Path
    lateinit var mFloat8Left: FloatArray //左边圆角数组
    lateinit var mFloat8Right: FloatArray //右边圆角数组

    private var mProgressX: Float = 0f //进度理论值
    private var mEndX: Float = 0f  //进度条尾部
    private var mTextWidth: Int = 0 //文字宽度
    private var mLostRight: Boolean = false //是否不画右边
    private var mText: String = "" //文字

    private var mSbBgColor = 0xffC9C9C9
    private var mSbOnColor = 0xff54F340
    private var mSbOnHeight = dp2px(10f)
    private var mSbBgHeight = dp2px(10f)
    private var mSbTxtColor = 0xff525252
    private var mSbTxtSize = sp2px(10f)
    private var mSbTxtOffset= sp2px(10f)
    private var mSbTxtGone = false


    private fun dp2px(dp: Float): Float {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    private fun sp2px(dp: Float): Float {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics)
    }


    init {

        initAttrs(attrs)

        init()

    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.apply {
            textSize = mSbTxtSize
            color = mSbOnColor.toInt()
            strokeWidth = mSbOnHeight

        }

        mRectF = RectF()
        mPath = Path()

        mFloat8Left = floatArrayOf(
            //仅左边两个圆角--为背景
            mSbBgHeight / 2, mSbOnHeight / 2,  //左上圆角x,y
            0f, 0f, //右上圆角x,y
            0f, 0f, //右下圆角x,y
            mSbOnHeight / 2, mSbOnHeight / 2  //左下圆角x,y

        )

        mFloat8Right = floatArrayOf(
            //仅右边两个圆角
            0f, 0f, //左上圆角x,y
            mSbBgHeight / 2, mSbBgHeight / 2, //右上圆角x,y
            mSbBgHeight / 2, mSbBgHeight / 2, //右下圆角x,y
            0f, 0f //左下圆角x,y
        )

    }

    private fun initAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TolySeekBar)

        mSbOnHeight = a.getDimension(R.styleable.TolySeekBar_z_sb_on_height, mSbOnHeight)
        mSbTxtOffset=a.getDimension(R.styleable.TolySeekBar_z_sb_text_offset, mSbTxtOffset)

        mSbOnColor = a.getColor(R.styleable.TolySeekBar_z_sb_on_color, mSbOnColor.toInt()).toLong()

        mSbTxtSize = a.getDimension(R.styleable.TolySeekBar_z_sb_text_size, mSbTxtSize)

        mSbTxtColor =
            a.getColor(R.styleable.TolySeekBar_z_sb_text_color, mSbTxtColor.toInt()).toLong()

        mSbBgHeight = a.getDimension(R.styleable.TolySeekBar_z_sb_bg_height, mSbBgHeight)

        mSbBgColor = a.getColor(R.styleable.TolySeekBar_z_sb_bg_color, mSbBgColor.toInt()).toLong()

        mSbTxtGone = a.getBoolean(R.styleable.TolySeekBar_z_sb_text_gone, mSbTxtGone)

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        mSBWidth = measuredWidth - paddingLeft - paddingRight //进度条实际宽度

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        canvas.translate(paddingLeft.toFloat(), height / 2f)

        parseBeforeDraw() //1.绘制前对数值进行计算以及控制的flag设置

        if (progress == 100) {//进度达到100后文字消失
            whenOver();//2.
        }
        if (mEndX > 0) {//当进度条尾部>0绘制
            drawProgress(canvas)//3.
        }
        if (!mSbTxtGone) {//绘制文字
            mPaint.color = mSbTxtColor.toInt()

            var y = (-(mPaint.descent() + mPaint.ascent()) / 2)
            canvas.drawText(mText, mProgressX, y, mPaint);
        } else {
            mTextWidth = (0 - mSbTxtOffset).toInt()
        }
        if (!mLostRight) {//绘制右侧
            drawRight(canvas) //4.
        }

        canvas.restore()

    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result = 0
        var mode = MeasureSpec.getMode(heightMeasureSpec)
        var size = MeasureSpec.getSize(heightMeasureSpec)

        if (mode == MeasureSpec.EXACTLY) {
            //控件尺寸已经确定：如：
            // android:layout_height="40dp"或"match_parent"
            result = size
        } else {
            var textHeight = mPaint.descent() - mPaint.ascent()
            result = paddingTop + paddingBottom + max(
                max(mSbBgHeight, mSbOnHeight),
                abs(textHeight)
            ).toInt()

            if (mode == MeasureSpec.AT_MOST) {
                //最多不超过
                result = min(result, size)

            }

        }

        return result

    }

    /**
     * 对数值进行计算以及控制的flag设置
     */
    private fun parseBeforeDraw() {
        mLostRight = false  //lostRight控制是否绘制右侧
        var radio = progress / max.toFloat()  //当前百分比率
        mProgressX = radio * mSBWidth //进度条当前长度
        mEndX = mProgressX - mSbTxtOffset / 2  //进度条当前长度-文字间隔的左半
        mText = "${progress}%"
        if (mProgressX + mTextWidth > mSBWidth) {
            mProgressX = (mSBWidth - mTextWidth).toFloat()
            mLostRight = true
        }

        //文字宽度
        mTextWidth = mPaint.measureText(mText).toInt()
    }

    /**
     * 当结束是执行：
     */
    private fun whenOver() {
        mSbTxtGone = true
        mFloat8Right = floatArrayOf(
            mSbBgHeight / 2, mSbBgHeight / 2, //左上圆角x,y
            mSbBgHeight / 2, mSbBgHeight / 2, //右上圆角x,y
            mSbBgHeight / 2, mSbBgHeight / 2, //右下圆角x,y
            mSbBgHeight / 2, mSbBgHeight / 2  //左下圆角x,y
        )

    }

    /**
     * 绘制左侧：(进度条)
     *
     * @param canvas
     */
    private fun drawProgress(canvas: Canvas) {
        mPath.reset()
        mRectF.set(0f, mSbOnHeight / 2, mEndX, -mSbOnHeight / 2)
        mPath.addRoundRect(mRectF, mFloat8Left, Path.Direction.CW) //顺时针画
        mPaint.style = Paint.Style.FILL
        mPaint.color = mSbOnColor.toInt()
        canvas.drawPath(mPath, mPaint) //使用path绘制一端是圆头的线

    }

    /**
     * 绘制左侧：(背景)
     *
     * @param canvas
     */
    private fun drawRight(canvas: Canvas) {
        val start: Float = mProgressX + mSbTxtOffset / 2 + mTextWidth
        mPaint.color = mSbBgColor.toInt()
        mPaint.strokeWidth = mSbBgHeight
        mPath.reset()
        mRectF[start, mSbBgHeight / 2, mSBWidth.toFloat()] = -mSbBgHeight / 2
        mPath.addRoundRect(mRectF, mFloat8Right, Path.Direction.CW) //顺时针画
        canvas.drawPath(mPath, mPaint) //使用path绘制一端是圆头的线
    }






}