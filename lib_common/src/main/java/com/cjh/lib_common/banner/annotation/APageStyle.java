package com.cjh.lib_common.banner.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.cjh.lib_common.banner.constants.PageStyle.MULTI_PAGE;
import static com.cjh.lib_common.banner.constants.PageStyle.MULTI_PAGE_OVERLAP;
import static com.cjh.lib_common.banner.constants.PageStyle.MULTI_PAGE_SCALE;
import static com.cjh.lib_common.banner.constants.PageStyle.NORMAL;

/**
 * @author: caijianhui
 * @date: 2020/6/16 9:30
 * @description:
 */
@IntDef({NORMAL, MULTI_PAGE, MULTI_PAGE_OVERLAP, MULTI_PAGE_SCALE})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface APageStyle {
}
