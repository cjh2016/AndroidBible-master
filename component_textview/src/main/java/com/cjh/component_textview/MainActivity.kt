package com.cjh.component_textview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cjh.component_textview.custom.ExpandableTextView
import com.cjh.component_textview.ui.activity.SpanActivity
import java.lang.StringBuilder

/**
 * @author: caijianhui
 * @date: 2019/10/9 9:33
 * @description:
 */
class MainActivity : AppCompatActivity() {

    var expandableTextView: ExpandableTextView? = null


    val String.lastChat: Char
        get() = get(length - 1)

    var StringBuilder.lastChat: Char
        get() = get(length - 1)
        set(value: Char) {
            setCharAt(length - 1, value)
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expandableTextView = findViewById(R.id.expanded_text)
        val viewWidth = windowManager.defaultDisplay.width - dp2px(this, 20f)
        expandableTextView?.run {
            initWidth(viewWidth)
            maxLines = 1
            setHasAnimation(true)
            setCloseInNewLine(true)
            setOpenSuffixColor(resources.getColor(R.color.colorAccent))
            setCloseSuffixColor(resources.getColor(R.color.colorAccent))
            setOriginalText("在全球，随着Flutter被越来越多的知名公司应用在自己的商业APP中，" +
                    "Flutter这门新技术也逐渐进入了移动开发者的视野，尤其是当Google在2018年IO大会上发布了第一个" +
                    "Preview版本后，国内刮起来一股学习Flutter的热潮。\n\n为了更好的方便帮助中国开发者了解这门新技术" +
                    "，我们，Flutter中文网，前后发起了Flutter翻译计划、Flutter开源计划，前者主要的任务是翻译" +
                    "Flutter官方文档，后者则主要是开发一些常用的包来丰富Flutter生态，帮助开发者提高开发效率。而时" +
                    "至今日，这两件事取得的效果还都不错！")
        }

        test()
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return when(dpValue < 0) {
            true -> -((-dpValue * scale + 0.5f).toInt())
            false -> (dpValue * scale + 0.5f).toInt()
        }
    }

    fun goSpanActivity(view: View?) {
        go(SpanActivity::class.java)
    }

    private fun go(clazz: Class<out Activity>) {
        val intent = Intent(this, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    private fun test() {
        val s = "abc"
        println(s.lastChat)
        val sb = StringBuilder("abc")
        println(sb.lastChat)
    }


}