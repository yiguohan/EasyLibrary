package com.yiguohan.easylib;

import android.content.Context;
import android.content.res.Resources;

/**
 * Bar相关工具类
 * Created by yiguohan on 2017/11/13.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class BarUtils {

    private BarUtils() {
    }

    //-----------------------------STATUS BAR-----------------------------//

    /**
     * 获取状态栏高度px
     *
     * @param context 上下文
     * @return 状态栏高度px
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
