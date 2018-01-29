package com.yiguohan.easylib;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * 单元测试工具类
 * Created by yiguohan on 2018/1/29.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUtils {
    public static void init() {
        GlobalUtils.init(RuntimeEnvironment.application);
    }

    @Test
    public void test() throws Exception {

    }
}
