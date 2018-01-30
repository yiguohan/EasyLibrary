package com.yiguohan.easylib;

import java.io.File;

/**
 * 文件工具类
 * Created by yiguohan on 2018/1/29.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class FileUtils {

    public FileUtils() {
        throw new UnsupportedOperationException("不能初始化该类为对象！");
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 路径对应的文件
     */
    public static File getFileByPath(final String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 根据文件路径判断文件是否存在
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 根据文件对象判断文件是否存在
     *
     * @param file 文件对象
     * @return
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 根据文件路径对文件改名
     *
     * @param filePath 文件路径
     * @param newName  更改后的名称
     * @return
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 根据文件对象对文件改名
     *
     * @param file    文件对象
     * @param newName 更改后的名称
     * @return
     */
    public static boolean rename(final File file, final String newName) {
        //对象是否为空
        if (file == null) {
            return false;
        }
        //对象是否存在
        if (!file.exists()) {
            return false;
        }
        //新的文件名是否为空格
        if (StringUtils.isSpace(newName)) {
            return false;
        }
        //新的文件名是否和旧的相同
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        //如果重命名的文件已存在返回 false
        return !newFile.exists() && file.renameTo(newFile);
    }

    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }
}
