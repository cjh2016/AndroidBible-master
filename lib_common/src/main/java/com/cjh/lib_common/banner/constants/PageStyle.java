package com.cjh.lib_common.banner.constants;

/**
 * @author: caijianhui
 * @date: 2020/6/16 9:33
 * @description:
 */
public interface PageStyle {

    int NORMAL = 0;

    /**
     * @deprecated please use {@link com.zhpan.bannerview.BannerViewPager#setRevealWidth(int)} instead.
     */
    @Deprecated
    int MULTI_PAGE = 1 << 1;

    /**
     * Requires Api Version >= 21
     */
    int MULTI_PAGE_OVERLAP = 1 << 2;

    int MULTI_PAGE_SCALE = 1 << 3;
}
