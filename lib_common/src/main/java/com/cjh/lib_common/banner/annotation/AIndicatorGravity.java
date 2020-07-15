package com.cjh.lib_common.banner.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.cjh.lib_common.banner.constants.IndicatorGravity.CENTER;
import static com.cjh.lib_common.banner.constants.IndicatorGravity.END;
import static com.cjh.lib_common.banner.constants.IndicatorGravity.START;

/**
 * @author: caijianhui
 * @date: 2020/6/16 9:43
 * @description: 指示器显示位置
 */
@IntDef({CENTER, START, END})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface AIndicatorGravity {

}
