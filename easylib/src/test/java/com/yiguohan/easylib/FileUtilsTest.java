package com.yiguohan.easylib;


import junit.framework.Assert;

import org.junit.Test;

import static com.yiguohan.easylib.TestConfig.PATH_FILE;

/**
 * Created by yiguohan on 2018/1/29.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */
public class FileUtilsTest {

    @Test
    public void getFileByPath() throws Exception {
        Assert.assertNull(FileUtils.getFileByPath(" "));
        Assert.assertNotNull(FileUtils.getFileByPath(PATH_FILE));
    }

    @Test
    public void isFileExists() throws Exception {
        Assert.assertTrue(FileUtils.isFileExists(PATH_FILE + "UTF8.txt"));
        Assert.assertFalse(FileUtils.isFileExists(PATH_FILE + "UTF8"));
    }
}
