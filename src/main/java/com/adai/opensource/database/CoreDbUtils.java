package com.adai.opensource.database;

import com.adai.opensource.config.DatabaseConfig;
import com.adai.opensource.pojo.TableAndColumnInfo;
import com.adai.opensource.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouchengpei
 * date   2019/11/17 22:20
 * description 获取表结构和所有字段工具类
 */
public class CoreDbUtils {

    private static DatabaseMetaData metaData;

    /**
     * 字段如果以 is_ 开头 说明是一个boolean类型
     */
    private static final String IS_BOOLEAN = "is_";

    static {
        try {
            Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PWD);
            metaData = conn.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("创建数据库连接或获取连接元数据失败");
        }
    }

    /**
     * 根据数据库的连接参数，获取指定表的基本信息：表名、表注释
     * 注意: 该方法一次只能获取一张表结构信息
     *
     * @param tableAndColumnInfo 保存表结构信息对象
     * @param database           数据库
     * @param table              表名
     * @return TableInfo对象
     */
    private static TableAndColumnInfo getTableInfo(TableAndColumnInfo tableAndColumnInfo, String database, String table) throws SQLException {
        // 获取数据库表结构信息
        ResultSet tableSet = metaData.getTables(database, null, table, new String[]{"TABLE"});

        if (tableSet.next()) {
            String tableName = tableSet.getString("TABLE_NAME");
            String tableRemarks = tableSet.getString("REMARKS");
            tableName = StringUtils.camelName(tableName);
            // 驼峰过后，将第一个字母变成大写
            tableName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
            tableAndColumnInfo.setTableName(tableName);
            tableAndColumnInfo.setTableRemarks(tableRemarks);
        } else {
            String errorMsg = String.format("数据库%s不存在，或者表%s不存在，请检查", database, table);
            throw new RuntimeException(errorMsg);
        }
        return tableAndColumnInfo;
    }

    /**
     * 根据数据库的连接参数，获取指定表所有字段的基本信息：字段名、字段类型、字段注释、是否可以为null、是否是主键
     * 注意: 该方法一次只能获取一张表结构信息
     *
     * @param tableAndColumnInfo 保存表结构信息对象
     * @param database           数据库
     * @param table              表名
     * @return TableInfo对象
     */
    private static TableAndColumnInfo getColumnInfo(TableAndColumnInfo tableAndColumnInfo, String database, String table) throws SQLException {
        // 将主键放入primaryKeysList集合中
        List<String> primaryKeysList = new ArrayList<>();
        // 判断主键信息
        ResultSet primaryKeysSet = metaData.getPrimaryKeys(database, null, table);
        while (primaryKeysSet.next()) {
            String primaryKeyName = primaryKeysSet.getString("COLUMN_NAME");
            if (primaryKeyName.startsWith(IS_BOOLEAN)) {
                throw new RuntimeException("主键命名不能is_开头");
            }
            primaryKeyName = StringUtils.camelName(primaryKeyName);
            // 在MYSQL中 该值固定返回 PRIMARY 暂未测试其他数据库 为了后期最大向后兼容 需要判断该值
            String ifPrimary = primaryKeysSet.getString("PK_NAME");
            if ("PRIMARY".equals(ifPrimary)) {
                primaryKeysList.add(primaryKeyName);
            }
        }

        ResultSet columnsSet = metaData.getColumns(database, null, table, null);
        List<TableAndColumnInfo.ColumnInfo> columnInfoList = tableAndColumnInfo.getColumnInfoList();
        while (columnsSet.next()) {
            String columnName = columnsSet.getString("COLUMN_NAME");
            String columnType = changeDbTypeToJavaType(columnName, columnsSet.getString("TYPE_NAME"));
            if (columnName.startsWith(IS_BOOLEAN)) {
                // 将 is_替换成 if_ 避免序列化问题
                columnName = "if_" + columnName.substring(3);
            }
            String columnRemarks = columnsSet.getString("REMARKS");
            boolean isNull = columnsSet.getBoolean("IS_NULLABLE");
            boolean isAutoincrement = columnsSet.getBoolean("IS_AUTOINCREMENT");
            TableAndColumnInfo.ColumnInfo columnInfo = new TableAndColumnInfo.ColumnInfo(StringUtils.camelName(columnName), columnType, columnRemarks, isNull, false, isAutoincrement);


            if (primaryKeysList.contains(columnName)) {
                columnInfo.setIfPrimaryKey(true);
                if (!isAutoincrement) {
                    columnInfo.setNotAutoincrementWarnMsg("该主键没有自增auto_increment 数据类型为 " + columnType + "不符合阿里巴巴MYSQL建表规约");
                }
                if (primaryKeysList.size() > 1) {
                    StringBuilder warnMsg = new StringBuilder(String.format("%s表主键由", table));
                    for (String primaryKey : primaryKeysList) {
                        warnMsg.append(primaryKey).append(",");
                    }
                    warnMsg.append("联合构成 不符合阿里巴巴MYSQL建表规约");
                    columnInfo.setMultiplePrimaryKeyColumnWarnMsg(warnMsg.toString());
                }
            }

            columnInfoList.add(columnInfo);
        }

        if (columnInfoList.isEmpty()) {
            String warnMsg = String.format("数据库%s中的表%s为空，没有任何字段", database, table);
            System.out.println(warnMsg);
        }

        return tableAndColumnInfo;
    }

    private static String changeDbTypeToJavaType(String columnName, String dbType) {
        String dbTypeLowerCase = dbType.toLowerCase();
        switch (dbTypeLowerCase) {
            case "tinyint":
                if (columnName.startsWith(IS_BOOLEAN)) {
                    return "Boolean";
                }
                return "Byte";
            case "tinyint unsigned":
                if (columnName.startsWith(IS_BOOLEAN)) {
                    return "Boolean";
                }
                return "Integer";
            case "smallint":
            case "smallint unsigned":
            case "mediumint":
            case "mediumint unsigned":
            case "int":
                return "Integer";
            case "int unsigned":
            case "bigint":
                return "Long";
            case "bigint unsigned":
                return "java.math.BigInteger";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "decimal":
                return "java.math.BigDecimal";
            case "varchar":
            case "char":
                return "String";
            case "datetime":
            case "timestamp":
                return "java.sql.Timestamp";
            case "date":
                return "java.sql.Date";
            case "time":
                return "java.sql.Time";
            default:
                String errorMsg = String.format("不识别数据库中%s字段类型", dbType);
                throw new RuntimeException(errorMsg);
        }
    }

    /**
     * 静态方法 获取表结构和字段信息
     *
     * @param database 数据库
     * @param table    表
     * @return 表结构和字段信息对象
     */
    public static TableAndColumnInfo getTableAndColumnsInfo(String database, String table) {
        TableAndColumnInfo tableAndColumnInfo = new TableAndColumnInfo();
        try {
            TableAndColumnInfo tableAndColumnInfo2 = getTableInfo(tableAndColumnInfo, database, table);
            return getColumnInfo(tableAndColumnInfo2, database, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("获取表和字段信息失败");
    }
}
