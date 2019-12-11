package com.adai.opensource;

import com.adai.opensource.config.*;
import com.adai.opensource.core.*;

import java.io.File;

/**
 * @author zhouchengpei
 * date    2019/12/11 10:12
 * description 只需要更改ParentConfig的BASE_LOCATION 和 BASE_PACKAGE_NAME | BASE_DIRECTORY_PATH
 * 切记 BASE_PACKAGE_NAME 和 BASE_DIRECTORY_PATH需要一一对应
 * <p>
 * 如果需要自动生成 entity、controller、service、impl、dao、mapper 需要调用ifAutoGeneratorDirectory();
 * <p>
 * executeIntoMaven需要ParentConfig.LOCATION 在一个Maven目录下
 * 标准格式Maven项目/src/main/
 * <p>
 * executeSpecialLocation指定一个目录即可 然后所有文件都会生成在下面
 */
public class GeneratorUtils {

    public static void main(String[] args) {
        ifAutoGeneratorDirectory(true);
        executeIntoMaven("adai", "student");
//        executeSpecialLocation("adai", "student", "E:\\Temp2\\");
    }

    /**
     * 生成的文件符合Maven风格
     *
     * @param database  数据库
     * @param tableName 表名
     */
    private static void executeIntoMaven(String database, String tableName) {
        CreateController.execute(database, tableName);
        CreateDao.execute(database, tableName);
        CreateEntity.execute(database, tableName);
        CreateMapper.execute(database, tableName);
        CreateService.execute(database, tableName);
        CreateServiceImpl.execute(database, tableName);
    }

    /**
     * 所有生成文件在一个目录下
     *
     * @param database  数据库
     * @param tableName 表名
     * @param location  指定目录
     */
    private static void executeSpecialLocation(String database, String tableName, String location) {
        CreateServiceConfig.IS_SAME_PATH = true;
        CreateEntityConfig.LOCATION = location;
        CreateControllerConfig.LOCATION = location;
        CreateServiceConfig.LOCATION = location;
        CreateDaoConfig.LOCATION = location;
        CreateMapperConfig.LOCATION = location;

        CreateController.execute(database, tableName);
        CreateDao.execute(database, tableName);
        CreateEntity.execute(database, tableName);
        CreateMapper.execute(database, tableName);
        CreateService.execute(database, tableName);
        CreateServiceImpl.execute(database, tableName);
    }


    /**
     * 是否自动生成 目录
     * java/com.adai
     * -------controller
     * -------service
     * ----------impl
     * -------dao
     * java/resources
     * --------mapper
     * 当该值为true时 会根据ParentConfig的BASE_LOCATION和BASE_PACKAGE_NAME
     * 自动生成标准的controller、service、impl、dao、mapper 如果已经存在那么就跳过
     * 如果不存在就会自动包目录或文件目录外围
     * 如果STANDARD_MVC = false 那么目录文件就必须要存在 否则会报错
     * 注意 项目必须要符合maven风格
     */
    private static void ifAutoGeneratorDirectory(Boolean b) {
        if (b) {
            String baseJavaPath = ParentConfig.BASE_LOCATION + "java\\";
            if (!ifFileExists(baseJavaPath)) {
                throw new RuntimeException(baseJavaPath + "目录不存在");
            }

            String baseResourcesPath = ParentConfig.BASE_LOCATION + "resources\\";
            if (!ifFileExists(baseResourcesPath)) {
                throw new RuntimeException(baseResourcesPath + "目录不存在");
            }

            String basePackagePath = baseJavaPath + ParentConfig.BASE_DIRECTORY_PATH;
            if (!ifFileExists(basePackagePath)) {
                createDirectory(basePackagePath);
            }

            String entityPath = basePackagePath + "entity\\";
            if (!ifFileExists(entityPath)) {
                createDirectory(entityPath);
            }

            String controllerPath = basePackagePath + "controller\\";
            if (!ifFileExists(controllerPath)) {
                createDirectory(controllerPath);
            }

            String servicePath = basePackagePath + "service\\";
            if (!ifFileExists(servicePath)) {
                createDirectory(servicePath);
            }

            String serviceImplPath = basePackagePath + "service\\impl\\";
            if (!ifFileExists(serviceImplPath)) {
                createDirectory(serviceImplPath);
            }

            String dapPath = basePackagePath + "dao\\";
            if (!ifFileExists(dapPath)) {
                createDirectory(dapPath);
            }

            String mapperPath = baseResourcesPath + "mapper\\";
            if (!ifFileExists(mapperPath)) {
                createDirectory(mapperPath);
            }
        }
    }

    private static Boolean ifFileExists(String file) {
        File fileObject = new File(file);
        return fileObject.exists();
    }

    private static void createDirectory(String path) {
        File file = new File(path);
        boolean newDirectory = file.mkdirs();
        if (!newDirectory) {
            throw new RuntimeException(String.format("创建路径%s失败", path));
        }
    }
}
