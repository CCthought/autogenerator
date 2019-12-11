package com.adai.opensource.config;

/**
 * @author zhouchengpei
 * date    2019/12/11 14:24
 * description CreateService 配置类 注意 CreateServiceImpl也引用了该类 只不过在代码里面多加了 impl
 */
public class CreateServiceConfig {

    /**
     * service文件生成的路径
     */
    public static String LOCATION = ParentConfig.BASE_LOCATION + "java\\" + ParentConfig.BASE_DIRECTORY_PATH + "service\\";

    /**
     * service 的名字
     */
    public static final String SERVICE_NAME = "I" + ParentConfig.BASE_NAME + "Service";

    /**
     * Service 和 impl 是否在同一个目录
     */
    public static Boolean IS_SAME_PATH = false;

    /**
     * service 所在的包 对应class最上方的 package...
     */
    public static final String PACKAGE_NAME = ParentConfig.BASE_PACKAGE_NAME + ".service";
}
