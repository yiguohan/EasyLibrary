package com.yiguohan.easylib;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 *
 * Created by yiguohan on 2017/11/13.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class ToastUtils {
    /**
     * 显示吐司(LENGTH.LONG)
     *
     * @param context 上下文
     * @param str     显示内容
     */
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG);
    }
}
