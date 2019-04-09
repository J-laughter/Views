package com.laughter.views.util;

import android.content.Context;

/**
 * 作者： laughter_jiang
 * 创建时间： 2019/1/3
 * 描述： com.laughter.views.util
 */
public class DensityUtil {

    public static int dip2px(Context context, int value){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(value * density + 0.5f);
    }

    public static int px2dip(Context context, int value) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(value / density + 0.5f);
    }
}
