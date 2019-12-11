package com.adai.opensource.core;

import com.adai.opensource.config.CreateControllerConfig;
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
 * date    2019/12/11 14:07
 * description 生成controller文件
 */
public class CreateController {

    /**
     * 生成 数据库 对应表 的 controller文件
     *
     * @param database  数据库
     * @param tableName 表
     */
    public static void execute(String database, String tableName) {
        TableAndColumnInfo data = CoreDbUtils.getTableAndColumnsInfo(database, tableName);
        PrintWriter writer = CommonUtils.getPrintWriter(CreateControllerConfig.LOCATION, CreateControllerConfig.CONTROLLER_NAME + ".java");

        // 输出包名
        writer.println(String.format("package %s;", CreateControllerConfig.PACKAGE_NAME));
        writer.println();

        //输出 导入import包名
        writer.println("import org.springframework.web.bind.annotation.RestController;");
        writer.println("import org.springframework.web.bind.annotation.RequestMapping;");
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
        writer.println(String.format(" * description %s表对应Controller类", data.getTableName()));
        writer.println(" */");

        // 输出类名
        writer.println("@RestController");
        writer.println(String.format("@RequestMapping(\"/api/v1/%s\")", tableName.toLowerCase()));
        writer.println(String.format("public class %s {", CreateControllerConfig.CONTROLLER_NAME));
        writer.println();
        // 开启了slf4j
        if (SwitchConfig.IS_OPEN_SLF4J) {
            writer.println(String.format("    private static final Logger logger = LoggerFactory.getLogger(%s.class);", CreateControllerConfig.CONTROLLER_NAME));
            writer.println();
        }
        writer.println("}");
        // 结尾
        writer.flush();
        writer.close();
    }
}
