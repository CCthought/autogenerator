package com.adai.opensource.util;

import java.io.*;

/**
 * @author zhouchengpei
 * date    2019/12/11 11:18
 * description 公共通用方法
 */
public class CommonUtils {
    /**
     * 获取字符输出流
     *
     * @param fileName 文件名
     * @return BufferedWriter
     */
    public static PrintWriter getPrintWriter(String location, String fileName) {
        try {
            File file = new File(location + fileName);
            if (!file.exists()) {
                return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            } else {
                throw new RuntimeException(location + fileName + "已经存在");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("获取输出流失败");
        }
    }
}
