package com.adai.opensource.core;

import com.adai.opensource.config.CreateEntityConfig;
import com.adai.opensource.database.CoreDbUtils;
import com.adai.opensource.pojo.TableAndColumnInfo;
import com.adai.opensource.util.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhouchengpei
 * date   2019/12/10 9:47
 * description .
 */
public class CreateEntity {

    /**
     * 获取字符输出流
     *
     * @param fileName 文件名
     * @return BufferedWriter
     */
    private static PrintWriter getPrintWriter(String fileName) {
        try {
            File file = new File(CreateEntityConfig.LOCATION + fileName);
            if (!file.exists()) {
                return new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            } else {
                throw new RuntimeException(CreateEntityConfig.LOCATION + fileName + "已经存在");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("获取输出流失败");
        }
    }

    /**
     * 生成 数据库 对应表 的entity
     *
     * @param database  数据库
     * @param tableName 表
     */
    public static void execute(String database, String tableName) {
        TableAndColumnInfo data = CoreDbUtils.getTableAndColumnsInfo(database, tableName);
        // 已经将第一个字母变成大写
        String className = data.getTableName();
        PrintWriter writer = getPrintWriter(className + ".java");

        // 1.输出包名
        writer.println("package " + CreateEntityConfig.PACKAGE_NAME);
        writer.println();
        // 2.输出 导入import包名
        List<TableAndColumnInfo.ColumnInfo> columnInfoList = data.getColumnInfoList();
        for (TableAndColumnInfo.ColumnInfo columnInfo : columnInfoList) {
            String columnType = columnInfo.getColumnType();
            if (columnType.contains(".")) {
                writer.println(String.format("import %s;", columnType));
            }
        }
        writer.println();

        //3.输出作者信息
        writer.println("/**");
        writer.println(String.format(" * @author %s", CreateEntityConfig.AUTHOR));
        writer.println(String.format(" * date   %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        if (StringUtils.isEmptyWithTrim(data.getTableRemarks())) {
            writer.println(String.format(" * description 数据库%s 表%s 没有表注释", database, tableName));
            writer.println(String.format(" * 注意 该类与数据库%s 表%s结构一一对应", database, tableName));
        } else {
            writer.println(String.format(" * description 数据库%s 表%s注释: %s", database, tableName, data.getTableRemarks()));
            writer.println(String.format(" * 注意 该类与数据库%s 表%s结构一一对应", database, tableName));
        }
        writer.println(" */");

        // 4.输出类名
        writer.println(String.format("public class %s {", className));

        // 5.输出属性和注释
        for (TableAndColumnInfo.ColumnInfo columnInfo : columnInfoList) {
            writer.println();
            writer.println("    /**");
            if (columnInfo.getIfPrimaryKey()) {
                if (columnInfo.getNotAutoincrementWarnMsg() == null) {
                    writer.println("     * !!!该字段是主键且自增");
                } else {
                    writer.println("     * !!!该字段是主键");
                    writer.println("     * " + columnInfo.getNotAutoincrementWarnMsg());
                }
                if (columnInfo.getMultiplePrimaryKeyColumnWarnMsg() != null) {
                    writer.println("     * " + columnInfo.getMultiplePrimaryKeyColumnWarnMsg());
                }
            }
            String remarks = String.format("     * %s", columnInfo.getColumnRemarks());
            remarks = String.format(remarks + " 该字段%s空", columnInfo.getIfNull() ? "可以" : "不可以");
            writer.println(remarks);
            writer.println("     */");
            String javaType = columnInfo.getColumnType();
            if (javaType.contains(".")) {
                writer.println(String.format("    private %s %s;", javaType.substring(javaType.lastIndexOf(".") + 1), columnInfo.getColumnName()));
            } else {
                writer.println(String.format("    private %s %s;", javaType, columnInfo.getColumnName()));
            }
        }
        // 6.属性 getter/setter 方法
        for (TableAndColumnInfo.ColumnInfo columnInfo : columnInfoList) {
            writer.println();
            String columnName = columnInfo.getColumnName();
            String columnNameFormat = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);

            String columnType = columnInfo.getColumnType();
            if (columnType.contains(".")) {
                columnType = columnType.substring(columnType.lastIndexOf(".") + 1);
            }
            writer.println(String.format("    public %s get%s() {", columnType, columnNameFormat));
            writer.println(String.format("        return %s;", columnName));
            writer.println("    }");
            writer.println();
            writer.println(String.format("    public void set%s(%s %s) {", columnNameFormat, columnType, columnName));
            writer.println(String.format("        this.%s = %s;", columnName, columnName));
            writer.println("    }");
        }

        // 7.toString() 方法
        writer.println();
        writer.println("    @Override");
        writer.println("    public String toString() {");
        writer.println(String.format("        return \"%s{\" +", className));

        for (int i = 0; i < columnInfoList.size(); i++) {
            String columnName = columnInfoList.get(i).getColumnName();
            if (i == 0) {
                writer.println(String.format("                \"%s=\" + %s +", columnName, columnName));
            } else {
                writer.println(String.format("                \", %s='\" + %s + '\\'' +", columnName, columnName));
            }
        }
        writer.println("                '}';");
        writer.println("    }");

        // 8.结尾
        writer.println("}");
        writer.flush();
        writer.close();
    }

}
