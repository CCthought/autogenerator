package com.adai.opensource.config;

/**
 * @author zhouchengpei
 * date    2019/12/11 13:48
 * description CreateDao 配置类
 */
public class CreateDaoConfig {
    /**
     * dao文件生成的路径
     */
    public static String LOCATION = ParentConfig.BASE_LOCATION + "java\\" + ParentConfig.BASE_DIRECTORY_PATH + "dao\\";

    /**
     * dao的名字
     */
    public static final String DAO_NAME = ParentConfig.BASE_NAME + "Dao";

    /**
     * dao 所在的包 对应class最上方的 package...
     */
    public static final String PACKAGE_NAME = ParentConfig.BASE_PACKAGE_NAME + ".dao";
}
