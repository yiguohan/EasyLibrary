package com.yiguohan.easylib;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * SharedPreferences 相关方法
 * Created by yiguohan on 2017/11/13.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class SPUtils {

    private SharedPreferences sp;

    /**
     * 存放SharedPreference名称和对应SPUtils实例的Map
     */
    private static Map<String, SPUtils> SP_UTILS_MAP = new HashMap<>();

    /**
     * SPUtils构造函数
     *
     * @param spName
     */
    private SPUtils(String spName) {
        this(GlobalUtils.getApp().getApplicationContext(), spName);
    }

    /**
     * SPUtils构造函数，初始化sp
     *
     * @param context
     * @param spName
     */
    private SPUtils(Context context, String spName) {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 获取SPUtils实例（未命名SharedPreference名称，默认名称为“spUtils”）
     *
     * @return
     */
    public static SPUtils getInstance() {
        return getInstance("");
    }

    /**
     * 获取SPUtils实例
     *
     * @param spName 指定的SharedPreferences名称（未指定默认为“spUtils”）
     * @return
     */
    public static SPUtils getInstance(String spName) {
        if (StringUtils.isSpace(spName)) {
            //未命名的情况设置为默认值spUtils
            spName = "spUtils";
        }
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(spName);
            SP_UTILS_MAP.put(spName, spUtils);
        }
        return spUtils;
    }
}
