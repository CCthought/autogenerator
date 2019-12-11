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

}
