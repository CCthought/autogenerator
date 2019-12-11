package com.adai.opensource.config;

/**
 * @author zhouchengpei
 * date    2019/12/11 11:06
 * description 统一配置
 */
public class ParentConfig {

    /**
     * 创建 controller、service、dao、mapper的基本名字
     * 假如 BASE_NAME = OperationLog 那么相应的Controller、Service、Dao、Mapper如下
     * Controller: OperationLogController
     * Service:    IOperationLogService
     * imp:        OperationLogServiceImpl
     * Dao:        OperationLogDao
     * Mapper:     OperationLogMapper
     */
    static String BASE_NAME = "Student";

    /**
     * 基本的路径
     */
    public static String BASE_LOCATION = "F:\\JavaCode\\Idea\\Study\\Temp\\src\\main\\";

    /**
     * 基本的包名 注意 该值要和BASE_DIRECTORY_PATH一一对应
     */
    static String BASE_PACKAGE_NAME = "com.adai";

    /**
     * 基本的路径名 注意 该值要和BASE_PACKAGE_NAME一一对应
     */
    public static String BASE_DIRECTORY_PATH = "com\\adai\\";

    /**
     * 作者的姓名 生成的entity 需要一些注释
     */
    public static String AUTHOR = "zhouchengpei";

}
