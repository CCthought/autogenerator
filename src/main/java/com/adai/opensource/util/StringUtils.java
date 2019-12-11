package com.adai.opensource.util;

import java.util.regex.Pattern;

/**
 * @author zhouchengpei
 * date   2019/12/10 10:02
 * description .
 */
public class StringUtils {

    /**
     * 只能包含 数字、字母、下划线
     */
    private static Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]+$");
    /**
     * 0 ASCII值
     */
    private static final Byte ZERO_ASCII = 48;
    /**
     * 9 ASCII值
     */
    private static final Byte NINE_ASCII = 57;

    /**
     * 下划线 _
     */
    private static final char UNDERLINE = '_';

    public static boolean isEmptyWithTrim(String str){
        return (str == null || "".equals(str.trim()));
    }

    /**
     * 以驼峰标准格式转换，详情注释见 String camelName(String name, boolean allToLowercase)
     *
     * @param name 转换前下划线字符串
     * @return 转换后驼峰命名的字符串
     */
    public static String camelName(String name) {
        return camelName(name, true);
    }

    /**
     * 驼峰转换 有两种情况
     * 当allToLowercase=true时，该字符串全部变成小写，下划线_去掉，并后面一个字符变成大写
     * 例如:
     * HELLO_WORLD -> helloWorld
     * * Hello_1_world -> hello1World
     * 当allToLowercase=false时，仅仅将下划线去掉，后面的字符变成大写，其余不变
     * HELLO_WORLD -> HELLOWORLD
     * heLLo_1_world -> heLLo1World
     *
     * @param name           转换前下划线字符串
     * @param allToLowercase 是否先将 name字符全部转换成小写，以符合标准的驼峰命名规则
     * @return 转换后驼峰命名的字符串
     * @throws RuntimeException 字符串为空 包含了除字母、数字、下划线以外的字符
     *                          以数字、下划线开头 以下划线结尾 包含__连续下划线 均会抛出异常
     */
    public static String camelName(String name, boolean allToLowercase) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("字符串为空");
        }
        if (!pattern.matcher(name).matches()) {
            throw new RuntimeException("字符串不符合规定，只能包含数字、字母、下划线");
        }
        byte c = (byte) name.charAt(0);
        if (c >= ZERO_ASCII && c <= NINE_ASCII) {
            throw new RuntimeException("字符串不符合规定，不能以数字开头");
        }
        if ((char) c == UNDERLINE) {
            throw new RuntimeException("字符串不符合规定，不能以下划线开头");
        }

        if (name.charAt(name.length() - 1) == UNDERLINE) {
            throw new RuntimeException("字符串不符合规定，不能以下划线结尾");
        }

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == UNDERLINE) {
                if (name.charAt(i + 1) == UNDERLINE) {
                    throw new RuntimeException("字符串不符合规定，不能包含__连续下划线");
                }
            }
        }

        if (allToLowercase) {
            name = name.toLowerCase();
        }

        // 没有包含 _ 直接返回该字符串
        if (!name.contains(Character.toString(UNDERLINE))) {
            return name;
        }

        StringBuilder result = new StringBuilder();
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 第一个驼峰片段不做任何变化
            if (result.length() == 0) {
                result.append(camel);
            } else {
                // 其他驼峰片段 首字母大写，其余不做任何变化
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1));
            }
        }
        return result.toString();
    }

}
