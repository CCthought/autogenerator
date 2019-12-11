package com.adai.opensource.config;

/**
 * @author zhouchengpei
 * date    2019/12/11 14:21
 * description 开关配置 里面的值 会最终影响到生成的文件
 */
public class SwitchConfig {
    /**
     * 是否开启slf4j 会自动在controller和service生成 logger
     * 如果为false 不会生成logger
     */
    public static final Boolean IS_OPEN_SLF4J = true;

    /**
     * 是否标准的MVC模式 这里主要指包路径是否符合标准的MVC模式
     * java/com.adai
     * -------controller
     * -------service
     * ----------impl
     * -------dao
     * <p>
     * java/resources
     * --------mapper
     * <p>
     * 当该值为true时 会根据ParentConfig的BASE_LOCATION和BASE_PACKAGE_NAME
     * 自动生成标准的controller、service、impl、dao、mapper 如果已经存在那么就跳过
     * 如果不存在就会自动包目录或文件目录
     *
     * 如果STANDARD_MVC = false 那么目录文件就必须要存在 否则会报错
     */
    public static final Boolean STANDARD_MVC = false;

}
