package com.yiguohan.easylib;

/**
 * 单元测试配置文件
 * Created by yiguohan on 2018/1/29.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class TestConfig {
    static final String FILE_SEP = System.getProperty("file.separator");

    static final String LINE_SEP = System.getProperty("line.separator");

    static final String TEST_PATH;

    static {
        String projectPath = System.getProperty("user.dir");
        if (projectPath.contains("utilcode")) {
            projectPath += FILE_SEP + "utilcode";
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res" + FILE_SEP;
    }

    static final String PATH_TEMP = TEST_PATH + "temp" + FILE_SEP;

    static final String PATH_CACHE = TEST_PATH + "temp" + FILE_SEP;

    static final String PATH_ENCRYPT = TEST_PATH + "encrypt" + FILE_SEP;//加密

    static final String PATH_FILE = TEST_PATH + "file" + FILE_SEP;

    static final String PATH_ZIP = TEST_PATH + "zip" + FILE_SEP;
}
