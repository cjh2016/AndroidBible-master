package com.cjh.lib_basissdk.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.*
import android.os.BatteryManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Method
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * @author: caijianhui
 * @date: 2020/4/29 15:51
 * @description:
 */
object ViewUtils {

    private const val METHOD_GET_MAX_WIDTH = "getMaxWidth"
    private const val METHOD_GET_MAX_HEIGHT = "getMaxHeight"
    private const val METHOD_GET_MIN_WIDTH = "getMinWidth"
    private const val METHOD_GET_MIN_HEIGHT = "getMinHeight"
    private const val METHOD_SET_MAX_WIDTH = "setMaxWidth"
    private const val METHOD_SET_MAX_HEIGHT = "setMaxHeight"


    private const val FRAGMENT_CON = "NoSaveStateFrameLayout"


    /**
     * activity中通过Id获取控件的方法，根据声明的控件类型强制类型转换
     *
     * @param activity 控件所在的activity
     * @param resId    控件的id
     * @param <E>      继承自View的控件类型
     * @return 控件声明的类型
    </E> */
    fun <E : View?> findViewById(
        activity: Activity,
        @IdRes resId: Int
    ): E {
        return activity.findViewById(resId)
    }

    /**
     * activity中通过Id获取控件的方法，根据声明的控件类型强制类型转换
     *
     * @param view  控件所在的View对象
     * @param resId 控件的id
     * @param <E>   继承自View的控件类型
     * @return 控件声明的类型
    </E> */
    fun <E : View?> findViewById(view: View?, @IdRes resId: Int): E {
        requireNotNull(view) { "The argument view can not be null,please check your argument!" }
        return view?.findViewById(resId)
    }


    /**
     * 释放view上的所有图片资源
     * @param view
     */
    @SuppressLint("NewApi")
    fun unbindDrawables(view: View?) {
        var view: View? = view ?: return
        if (null != view?.background) {
            view.background.callback = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.background = null
            } else {
                view.setBackgroundDrawable(null)
            }
        }
        if (view is ImageView) {
            val imageView: ImageView = view
            var drawable: Drawable? = imageView.drawable
            if (null != drawable) {
                if (drawable is BitmapDrawable) {
                    var bitmap: Bitmap? = drawable.bitmap
                    if (null != bitmap && !bitmap.isRecycled) {
                        bitmap.recycle()
                        bitmap = null
                    }
                } else if (drawable is StateListDrawable) {
                    var bitmap: Bitmap?
                    var layerDrawable: Drawable?
                    val stateDrawable: StateListDrawable? = drawable
                    val constantState: Drawable.ConstantState? = stateDrawable?.constantState
                    if (null != constantState && constantState is DrawableContainer.DrawableContainerState) {
                        layerDrawable = constantState.getChild(1)
                        if (layerDrawable is LayerDrawable) {
                            layerDrawable = layerDrawable.getDrawable(1)
                            if (layerDrawable is BitmapDrawable) {
                                bitmap = layerDrawable.bitmap
                                if (null != bitmap) {
                                    bitmap.recycle()
                                    bitmap = null
                                }
                                layerDrawable = null
                            }
                        }
                    }
                }
                drawable = null
                imageView.drawable.callback = null
                imageView.setImageDrawable(null)
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                unbindDrawables(view.getChildAt(i))
            }
            if (view !is AdapterView<*>) {
                view.removeAllViews()
            }
        }
        view = null
    }


    /********************************************检查是不是电视*******************************************/

    /**
     * 检查当前屏幕的物理尺寸
     * 小于6.4认为是手机,否则是电视
     * @param ctx
     * @return true 手机   false 电视
     */
    private fun checkScreenIsPhone(ctx: Context): Boolean {
        val wm: WindowManager = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        //Math.pow(x,y)这个函数是求x的y次方
        val x = (dm.widthPixels / dm.xdpi).toDouble().pow(2.0)
        val y = (dm.heightPixels / dm.ydpi).toDouble().pow(2.0)
        //Math.sqrt(x)求x平方根
        val screenInches = sqrt(x + y)
        return screenInches < 6.5
    }

    /**
     * 检查当前设备的布局尺寸
     * 如果是 SIZE_LARGE 就认为是大屏幕
     * @param ctx
     * @return
     */
    private fun checkScreenLayoutIsPhone(ctx: Context): Boolean {
        return ((ctx.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_LAYOUTDIR_MASK)
                <= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    /**
     * 检查SIM卡的状态,如果没有检测到,认为是电视
     * @param ctx
     * @return
     */
    private fun checkTelephonyIsPhone(ctx: Context): Boolean {
        val telephony: TelephonyManager =
            ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephony.phoneType !== TelephonyManager.PHONE_TYPE_NONE
    }

    /**
     * 检查当前电源输入状态来判断是电视还是手机
     * @param ctx
     * @return
     */
    private fun checkBatteryIsPhone(ctx: Context): Boolean {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus: Intent = ctx.registerReceiver(null, intentFilter)
        //当前电池的状态
        val status: Int = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        //当前电量是否充满
        val isCharging = status == BatteryManager.BATTERY_STATUS_FULL
        //当前充电状态
        val chargePlug: Int = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        //当前是否为AC交流电
        val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
        //当前电量一定是满的 并且是AC交流电接入才认为是电视
        return !(isCharging && acCharge)
    }


    /**
     * 判断是否运行在手机端
     * @param ctx
     * @return true 手机  false 电视
     */
    fun isPhoneRunning(ctx: Context): Boolean {
        return (checkScreenIsPhone(ctx)
                && checkScreenLayoutIsPhone(ctx)
                && checkTelephonyIsPhone(ctx)
                && checkBatteryIsPhone(ctx))
    }


    /********************************************检查是不是电视*******************************************/

    fun setMaxWidth(view: View, value: Int) {
        setValue(view, METHOD_SET_MAX_WIDTH, value)
    }

    fun setMaxHeight(view: View, value: Int) {
        setValue(view, METHOD_SET_MAX_HEIGHT, value)
    }

    fun setMinWidth(view: View, value: Int) {
        view.minimumWidth = value
    }

    fun setMinHeight(view: View, value: Int) {
        view.minimumHeight = value
    }

    fun getMaxWidth(view: View): Int {
        return getValue(view, METHOD_GET_MAX_WIDTH)
    }

    fun getMaxHeight(view: View): Int {
        return getValue(view, METHOD_GET_MAX_HEIGHT)
    }

    @SuppressLint("NewApi")
    fun getMinWidth(view: View): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.minimumWidth
        } else {
            getValue(view, METHOD_GET_MIN_WIDTH)
        }
    }

    @SuppressLint("NewApi")
    fun getMinHeight(view: View): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.minimumHeight
        } else {
            getValue(view, METHOD_GET_MIN_HEIGHT)
        }
    }

    private fun getValue(
        view: View,
        getterMethodName: String
    ): Int {
        var result = 0
        try {
            val getValueMethod =
                view.javaClass.getMethod(getterMethodName)
            result = getValueMethod.invoke(view) as Int
        } catch (e: java.lang.Exception) {
            // do nothing
        }
        return result
    }

    private fun setValue(
        view: View,
        setterMethodName: String,
        value: Int
    ) {
        try {
            val setValueMethod: Method =
                view.javaClass.getMethod(setterMethodName, Int::class.java)
            setValueMethod.invoke(view, value)
        } catch (e: Exception) {
            // do nothing
        }
    }


    fun getLocationInView(parent: View?, child: View?): Rect? {
        require(!(child == null || parent == null)) { "parent and child can not be null." }
        var decorView: View? = null
        val context = child.context
        if (context is Activity) {
            decorView = context.window.decorView
        }
        val result = Rect()
        val tmpRect = Rect()
        var tmp = child
        if (child === parent) {
            child.getHitRect(result)
            return result
        }
        while (tmp !== decorView && tmp !== parent) {
            tmp!!.getHitRect(tmpRect)
            if (tmp.javaClass.simpleName != FRAGMENT_CON) {
                result.left += tmpRect.left
                result.top += tmpRect.top
            }
            tmp = tmp.parent as View
            requireNotNull(tmp) { "the view is not showing in the window!" }
            //added by isanwenyu@163.com fix bug #21 the wrong rect user will received in ViewPager
            if (tmp.parent != null && tmp.parent is ViewPager) {
                tmp = tmp.parent as View
            }
        }
        result.right = result.left + child.measuredWidth
        result.bottom = result.top + child.measuredHeight
        return result
    }


    /**
     * 动态设置一个View的margin值, 控件必须是在XML中定义的才有效，
     * 如果是直接new的view可能会报空指针异常
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun setMargins(
        view: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (null == view) return
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p =
                view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }


}