package com.yiguohan.easylib;

import org.junit.Test;
import org.junit.Assert;

/**
 * StringUtils 单元测试
 * Created by yiguohan on 2018/1/29.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class StringUtilsTest {

    @Test
    public void isEmpty() throws Exception {
        Assert.assertTrue(StringUtils.isEmpty(""));
        Assert.assertFalse(StringUtils.isEmpty(" "));
        Assert.assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void isSpace() throws Exception {
        Assert.assertTrue(StringUtils.isSpace(" "));
        Assert.assertTrue(StringUtils.isSpace(null));
        Assert.assertTrue(StringUtils.isSpace(""));
        Assert.assertTrue(StringUtils.isSpace("  \n\t\r"));
    }

    @Test
    public void equals() throws Exception {
        Assert.assertTrue(StringUtils.equals(null, null));
        Assert.assertTrue(StringUtils.equals("yiguohan", "yiguohan"));
        Assert.assertFalse(StringUtils.equals("Yiguohan", "yiguohan"));
    }

    @Test
    public void equalsIgnoreCase() throws Exception {
        Assert.assertTrue(StringUtils.equalsIgnoreCase(null, null));
        Assert.assertTrue(StringUtils.equalsIgnoreCase("yiguohan", "yiguohan"));
        Assert.assertTrue(StringUtils.equalsIgnoreCase("Yiguohan", "yiguohan"));
        Assert.assertFalse(StringUtils.equalsIgnoreCase(null, ""));
        Assert.assertFalse(StringUtils.equalsIgnoreCase(null, "yiguohan"));
        Assert.assertFalse(StringUtils.equalsIgnoreCase("ygh", "yiguohan"));

    }

    @Test
    public void null2Length0() throws Exception {
        Assert.assertEquals("", StringUtils.null2Length0(null));
        Assert.assertEquals("yiguohan", StringUtils.null2Length0("yiguohan"));
    }

    @Test
    public void length() throws Exception {
        Assert.assertEquals(0, StringUtils.length(null));
        Assert.assertEquals(0, StringUtils.length(""));
        Assert.assertEquals(8, StringUtils.length("yiguohan"));
    }

    @Test
    public void upperFirstLetter() throws Exception {
        Assert.assertEquals("Yiguohan", StringUtils.upperFirstLetter("yiguohan"));
        Assert.assertEquals("Yiguohan", StringUtils.upperFirstLetter("Yiguohan"));
        Assert.assertEquals("1Yiguohan", StringUtils.upperFirstLetter("1Yiguohan"));
    }

    @Test
    public void lowerFirstLetter() throws Exception {
        Assert.assertEquals("yiguohan", StringUtils.lowerFirstLetter("Yiguohan"));
        Assert.assertEquals("yiguohan", StringUtils.lowerFirstLetter("yiguohan"));
        Assert.assertEquals("1Yiguohan", StringUtils.lowerFirstLetter("1Yiguohan"));
    }

    @Test
    public void reverse() throws Exception {
        Assert.assertEquals("12345", StringUtils.reverse("54321"));
        Assert.assertEquals("文中试测", StringUtils.reverse("测试中文"));
        Assert.assertNull(StringUtils.reverse(null));
    }

    @Test
    public void toDBC() throws Exception {
        Assert.assertEquals(" ,.&", StringUtils.toDBC("　，．＆"));
        Assert.assertEquals(" ,.&", StringUtils.toDBC(" ,.&"));
        char[] space = {12288};
        Assert.assertEquals(" ", StringUtils.toDBC(new String(space)));
    }


    @Test
    public void toSBC() throws Exception {
        Assert.assertEquals("　，．＆", StringUtils.toSBC(" ,.&"));
        Assert.assertEquals("　，．＆", StringUtils.toSBC("　，．＆"));
    }
}
