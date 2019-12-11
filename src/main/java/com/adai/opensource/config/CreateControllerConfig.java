package com.adai.opensource.config;

/**
 * @author zhouchengpei
 * date    2019/12/11 13:48
 * description CreateController 配置类
 */
public class CreateControllerConfig {
    /**
     * controller文件生成的路径
     */
    public static String LOCATION = ParentConfig.BASE_LOCATION + "java\\" + ParentConfig.BASE_DIRECTORY_PATH + "controller\\";

    /**
     * controller的名字
     */
    public static String CONTROLLER_NAME = ParentConfig.BASE_NAME + "Controller";

    /**
     * controller 所在的包 对应class最上方的 package...
     */
    public static String PACKAGE_NAME = ParentConfig.BASE_PACKAGE_NAME + ".controller";
}
