package com.adai.opensource.core;

import com.adai.opensource.config.CreateDaoConfig;
import com.adai.opensource.config.ParentConfig;
import com.adai.opensource.database.CoreDbUtils;
import com.adai.opensource.pojo.TableAndColumnInfo;
import com.adai.opensource.util.CommonUtils;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouchengpei
 * date    2019/12/11 13:46
 * description 生成DAO文件
 */
public class CreateDao {

    /**
     * 生成 数据库 对应表 的 dao文件
     *
     * @param database  数据库
     * @param tableName 表
     */
    public static void execute(String database, String tableName) {
        TableAndColumnInfo data = CoreDbUtils.getTableAndColumnsInfo(database, tableName);
        PrintWriter writer = CommonUtils.getPrintWriter(CreateDaoConfig.LOCATION, CreateDaoConfig.DAO_NAME + ".java");

        // 输出包名
        writer.println(String.format("package %s;", CreateDaoConfig.PACKAGE_NAME));
        writer.println();

        // 输出作者信息
        writer.println("/**");
        writer.println(String.format(" * @author %s", ParentConfig.AUTHOR));
        writer.println(String.format(" * date   %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        writer.println(String.format(" * description %s表对应DAO接口", data.getTableName()));
        writer.println(" */");

        // 输出接口名
        writer.println(String.format("public interface %s {", CreateDaoConfig.DAO_NAME));
        writer.println();
        writer.println("}");

        // 结尾
        writer.flush();
        writer.close();
    }

}
