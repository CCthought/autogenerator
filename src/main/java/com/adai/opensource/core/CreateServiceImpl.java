package com.adai.opensource.core;

import com.adai.opensource.config.CreateServiceConfig;
import com.adai.opensource.config.ParentConfig;
import com.adai.opensource.config.SwitchConfig;
import com.adai.opensource.database.CoreDbUtils;
import com.adai.opensource.pojo.TableAndColumnInfo;
import com.adai.opensource.util.CommonUtils;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouchengpei
 * date    2019/12/11 15:27
 * description service impl生成类
 */
public class CreateServiceImpl {

    /**
     * 生成 数据库 对应表 的 service impl 文件
     *
     * @param database  数据库
     * @param tableName 表
     */
    public static void execute(String database, String tableName) {
        TableAndColumnInfo data = CoreDbUtils.getTableAndColumnsInfo(database, tableName);
        PrintWriter writer = CommonUtils.getPrintWriter(CreateServiceConfig.LOCATION + "impl\\", CreateServiceConfig.SERVICE_NAME + "Impl" + ".java");

        // 输出包名
        writer.println(String.format("package %s;", CreateServiceConfig.PACKAGE_NAME + ".impl"));
        writer.println();

        writer.println("import org.springframework.stereotype.Service;");
        writer.println(String.format("import %s.%s;", CreateServiceConfig.PACKAGE_NAME, CreateServiceConfig.SERVICE_NAME));
        // 开启了slf4j
        if (SwitchConfig.IS_OPEN_SLF4J) {
            writer.println("import org.slf4j.Logger;");
            writer.println("import org.slf4j.LoggerFactory;");
        }
        writer.println();
        // 输出作者信息
        writer.println("/**");
        writer.println(String.format(" * @author %s", ParentConfig.AUTHOR));
        writer.println(String.format(" * date   %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        writer.println(String.format(" * description %s表对应Service impl实现类", data.getTableName()));
        writer.println(" */");

        // 输出类
        writer.println("@Service");
        writer.println(String.format("public class %s implements %s{", CreateServiceConfig.SERVICE_NAME + "Impl", CreateServiceConfig.SERVICE_NAME));
        writer.println();

        // 开启了slf4j
        if (SwitchConfig.IS_OPEN_SLF4J) {
            writer.println(String.format("    private static final Logger logger = LoggerFactory.getLogger(%s.class);", CreateServiceConfig.SERVICE_NAME + "Impl"));
            writer.println();
        }

        writer.println("}");
        // 结尾
        writer.flush();
        writer.close();
    }

    public static void main(String[] args) {
        execute("adai", "student");
    }

}
