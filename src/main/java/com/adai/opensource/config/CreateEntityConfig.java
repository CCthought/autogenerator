package com.adai.opensource.config;

/**
 * @author zhouchengpei
 * date    2019/12/11 10:01
 * description Pojo变量设置值 抛弃xml配置 而是用Java类配置
 */
public class CreateEntityConfig {

    /**
     * entity 生成的路径
     */
    public static final String LOCATION = "F:\\JavaCode\\Idea\\github\\autogenerator\\src\\main\\java\\com\\adai\\opensource\\core\\";

    /**
     * entity 所在的包 对应class最上方的 package...
     */
    public static final String PACKAGE_NAME = "com.adai.opensource.core;";

    /**
     * 作者的姓名 生成的entity 需要一些注释
     */
    public static final String AUTHOR = "zhouchengpei";
}