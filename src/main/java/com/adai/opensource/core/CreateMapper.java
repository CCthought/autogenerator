package com.adai.opensource.core;

import com.adai.opensource.config.CreateDaoConfig;
import com.adai.opensource.config.CreateEntityConfig;
import com.adai.opensource.config.CreateMapperConfig;
import com.adai.opensource.database.CoreDbUtils;
import com.adai.opensource.pojo.TableAndColumnInfo;
import com.adai.opensource.util.CommonUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouchengpei
 * date    2019/12/11 11:03
 * description 生成mapper文件 主要是 sql片段
 */
public class CreateMapper {

    /**
     * 生成 数据库 对应表 的 mapper文件
     *
     * @param database  数据库
     * @param tableName 表
     */
    public static void execute(String database, String tableName) {
        TableAndColumnInfo data = CoreDbUtils.getTableAndColumnsInfo(database, tableName);
        List<TableAndColumnInfo.ColumnInfo> columnInfoList = data.getColumnInfoList();
        PrintWriter writer = CommonUtils.getPrintWriter(CreateMapperConfig.LOCATION, CreateMapperConfig.MAPPER_NAME);
        // 1.xml开头
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        writer.println(String.format("<mapper namespace=\"%s.%s\">", CreateDaoConfig.PACKAGE_NAME, CreateDaoConfig.DAO_NAME));
        writer.println();
        // 2.通用查询结果
        writer.println("    <!-- 通用查询映射结果 -->");
        writer.println(String.format("    <resultMap id=\"BaseResultMap\" type=\"%s.%s\">", CreateEntityConfig.PACKAGE_NAME,data.getTableName()));
        for (TableAndColumnInfo.ColumnInfo columnInfo : columnInfoList) {
            if (columnInfo.getIfPrimaryKey()) {
                writer.println(String.format("        <id column=\"%s\" property=\"%s\"/>", columnInfo.getOriginalColumnName(), columnInfo.getColumnName()));
            } else {
                writer.println(String.format("        <result column=\"%s\" property=\"%s\"/>", columnInfo.getOriginalColumnName(), columnInfo.getColumnName()));
            }
        }
        writer.println("    </resultMap>");

        // 3.通用sql查询列
        writer.println();
        writer.println("    <!-- 通用sql查询列 -->");
        writer.println("    <sql id=\"Base_Column_Select\">");
        List<String> baseColumnSelectList = columnInfoList.stream().map(TableAndColumnInfo.ColumnInfo::getOriginalColumnName).collect(Collectors.toList());
        StringBuilder baseColumnSelect = new StringBuilder("        ");
        for (int i = 0; i < baseColumnSelectList.size(); i++) {
            if (i != baseColumnSelectList.size() - 1) {
                baseColumnSelect.append(baseColumnSelectList.get(i)).append(", ");
            } else {
                baseColumnSelect.append(baseColumnSelectList.get(i));
            }
        }
        writer.println(baseColumnSelect.toString());
        writer.println("    </sql>");

        // 4.通用Java查询列
        writer.println();
        writer.println("    <!-- 通用Java查询列 -->");
        writer.println("    <sql id=\"Base_Java_Select\">");
        List<String> baseJavaSelectList = columnInfoList.stream().map(TableAndColumnInfo.ColumnInfo::getColumnName).collect(Collectors.toList());
        StringBuilder baseJavaSelect = new StringBuilder("        ");
        for (int i = 0; i < baseJavaSelectList.size(); i++) {
            if (i != baseJavaSelectList.size() - 1) {
                baseJavaSelect.append("#{").append(baseJavaSelectList.get(i)).append("}").append(", ");
            } else {
                baseJavaSelect.append("#{").append(baseJavaSelectList.get(i)).append("}");
            }
        }
        writer.println(baseJavaSelect.toString());
        writer.println("    </sql>");
        writer.println();

        // 5.结尾
        writer.println("</mapper>");
        writer.flush();
        writer.close();
    }
}
