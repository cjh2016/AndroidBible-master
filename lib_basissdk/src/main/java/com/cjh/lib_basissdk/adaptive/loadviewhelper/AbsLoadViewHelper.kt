package com.boll.lib_coremodel.adaptation.loadviewhelper

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.boll.lib_coremodel.adaptation.conversion.IConversion
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.IntDef
import com.boll.lib_coremodel.adaptation.conversion.CustomConversion
import com.cjh.lib_basissdk.util.DisplayUtils
import com.cjh.lib_basissdk.util.ViewUtils


/**
 * @author: caijianhui
 * @date: 2019/7/5 11:16
 * @description: 核心转换属性类，转换算法都在这里实现
 */
abstract class AbsLoadViewHelper(val context: Context, val designWidth: Int,
    val designHeight: Int, val designDpi: Int, val fontSize: Float, val unit: String): ILoadViewHelper {

    protected var actualDensity: Float = 0f

    protected var actualDensityDpi: Float = 0f

    protected var actualWidth: Float = 0f

    protected var actualHeight: Float = 0f

    @BenchMark
    protected var benchMark = BenchMark.WIDTH

    init {
        setActualParams(context)
    }

    fun reset(context: Context) {
        setActualParams(context)
    }

    private fun setActualParams(context: Context) {
        val actualScreenInfo = ActualScreen.screenInfo(context)
        if (actualScreenInfo.size == 4) {
            if (actualScreenInfo[0] < actualScreenInfo[1]) {  //针对平板，一般平板的宽应该都是大于高的
                actualWidth = actualScreenInfo[1]
                actualHeight = actualScreenInfo[0]
            } else {
                actualWidth = actualScreenInfo[0]
                actualHeight = actualScreenInfo[1]
            }
            actualDensity = actualScreenInfo[2]
            actualDensityDpi = actualScreenInfo[3]
            Log.i("cjhhhh", "actualWidth = $actualWidth actualHeight = $actualHeight actualDensity = $actualDensity actualDensityDpi = $actualDensityDpi")
        }
    }

    @JvmOverloads
    fun loadView(view: View, conversion: IConversion = CustomConversion()) {
        benchMark = installBenchMark()
        if (view is ViewGroup) {
            conversion.transform(view, this)
            for (i in 0 until view.childCount) {
                if (view.getChildAt(i) is ViewGroup) {
                    loadView(view.getChildAt(i), conversion)
                } else {
                    conversion.transform(view.getChildAt(i), this)
                }
            }
        } else {
            conversion.transform(view, this)
        }
    }

    override fun loadWidthHeightFont(view: View) {
        val layoutParams = view.layoutParams
        if (layoutParams.width > 0) {
            layoutParams.width = setValue(layoutParams.width)
        }
        if (layoutParams.height > 0) {
            layoutParams.height = setValue(layoutParams.height)
        }
        loadViewFont(view)
    }

    protected fun loadViewFont(view: View) {
        if (view is TextView) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, setFontSize(view))
        }
    }

    private fun setFontSize(textView: TextView): Float {
        return calculateValue(textView.textSize * fontSize, false)
    }

    fun calculateFontSize(fontSize: Float): Float {
        return calculateValue(fontSize, false)
    }

    override fun loadPadding(view: View) {
        view.setPadding(setValue(view.paddingLeft), setValue(view.paddingTop),
                setValue(view.paddingRight), setValue(view.paddingBottom))
    }

    override fun loadLayoutMargin(view: View) {
        val params = view.layoutParams
        val marginLayoutParams: ViewGroup.MarginLayoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            marginLayoutParams = params
            marginLayoutParams.leftMargin = setValue(marginLayoutParams.leftMargin)
            marginLayoutParams.topMargin = setValue(marginLayoutParams.topMargin)
            marginLayoutParams.rightMargin = setValue(marginLayoutParams.rightMargin)
            marginLayoutParams.bottomMargin = setValue(marginLayoutParams.bottomMargin)
            view.layoutParams = marginLayoutParams
        }
    }

    override fun loadMaxWidthAndHeight(view: View) {
        ViewUtils.setMaxWidth(view, setValue(ViewUtils.getMaxWidth(view)))
        ViewUtils.setMaxHeight(view, setValue(ViewUtils.getMaxHeight(view)))
    }

    override fun loadMinWidthAndHeight(view: View) {
        ViewUtils.setMinWidth(view, setValue(ViewUtils.getMinWidth(view)))
        ViewUtils.setMinHeight(view, setValue(ViewUtils.getMinHeight(view)))
    }


    @IntDef(BenchMark.WIDTH, BenchMark.HEIGHT, BenchMark.BOTH)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BenchMark {
        companion object {

            const val WIDTH = 0
            const val HEIGHT = 1
            const val BOTH = 2
        }
    }

    @JvmOverloads
    protected fun setValue(value: Int, isHeightSeries: Boolean = false): Int {
        return when(value) {
            0, 1 -> value
            else -> calculateValue(value.toFloat(), isHeightSeries).toInt()
        }
    }

    override fun loadCustomAttrValue(px: Int): Int {
        return setValue(px)
    }

    fun calculateValue(value: Float, isHeightSeries: Boolean): Float {
        return when(benchMark) {
            BenchMark.WIDTH -> calculateWidthValue(value)
            BenchMark.HEIGHT -> calculateHeightValue(value)
            BenchMark.BOTH -> if (isHeightSeries)
                                  calculateHeightValue(value)
                              else
                                  calculateWidthValue(value)
            else -> calculateWidthValue(value)
        }
    }

    private fun calculateWidthValue(value: Float): Float {
        return when(unit) {
            "px" -> value * (actualWidth / designWidth.toFloat())
            "dp" -> {
                val dip = DisplayUtils.px2dip(actualDensity, value)
                (designDpi.toFloat() / 160) * dip * (actualWidth / designWidth.toFloat())
            }
            else -> 0f
        }
    }

    private fun calculateHeightValue(value: Float): Float {
        return when(unit) {
            "px" -> value * (actualHeight / designHeight.toFloat())
            "dp" -> {
                val dip = DisplayUtils.px2dip(actualDensity, value)
                (designDpi.toFloat() / 160) * dip * (actualHeight / designHeight.toFloat())
            }
            else -> 0f
        }
    }

    fun calculateCustomValue(value: Float, isHeightSeries: Boolean = false, unit: String = "px"): Float {
        return if (isHeightSeries)
                    when(unit) {
                        "px" -> value * (actualHeight / designHeight.toFloat())
                        "dp" -> {
                            val dip = DisplayUtils.px2dip(actualDensity, value)
                            (designDpi.toFloat() / 160) * dip * (actualHeight / designHeight.toFloat())
                        }
                        else -> 0f
                    }
                else
                    when(unit) {
                        "px" -> value * (actualWidth / designWidth.toFloat())
                        "dp" -> {
                            val dip = DisplayUtils.px2dip(actualDensity, value)
                            (designDpi.toFloat() / 160) * dip * (actualWidth / designWidth.toFloat())
                        }
                        else -> 0f
                    }

    }

    @BenchMark
    abstract fun installBenchMark(): Int
}