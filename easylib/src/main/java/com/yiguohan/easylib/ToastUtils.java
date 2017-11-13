package com.yiguohan.easylib;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 * <p>
 * Created by yiguohan on 2017/11/13.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class ToastUtils {

    private ToastUtils() {
    }

    /**
     * 显示短时间的吐司
     *
     * @param context
     * @param str
     */
    public static void showShort(Context context, String str) {
        show(context, str, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间的吐司
     *
     * @param context
     * @param str
     */
    public static void showLong(Context context, String str) {
        show(context, str, Toast.LENGTH_LONG);
    }

    /**
     * 显示吐司
     *
     * @param context 上下文
     * @param str     显示内容
     */
    private static void show(Context context, String str, int duration) {
        Toast.makeText(context, str, duration);
    }
}
