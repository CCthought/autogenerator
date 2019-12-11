package com.adai.opensource.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouchengpei
 * date   2019/12/9 20:13
 * description .
 */
public class TableAndColumnInfo {

    /**
     * 主键
     * <p>
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableRemarks;

    private List<ColumnInfo> columnInfoList = new ArrayList<>(30);

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableRemarks() {
        return tableRemarks;
    }

    public void setTableRemarks(String tableRemarks) {
        this.tableRemarks = tableRemarks;
    }

    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

    @Override
    public String toString() {
        return "TableAndColumnInfo{" +
                "tableName='" + tableName + '\'' +
                ", tableRemarks='" + tableRemarks + '\'' +
                ", columnInfoList=" + columnInfoList +
                '}';
    }

    public static class ColumnInfo {

        /**
         * 数据库保存的原始字段名
         */
        private String originalColumnName;
        /**
         * 字段名字 驼峰处理过后
         */
        private String columnName;

        /**
         * 字段类型
         */
        private String columnType;

        /**
         * 字段注释
         */
        private String columnRemarks;

        /**
         * 字段是否可以为null
         */
        private Boolean ifNull;

        /**
         * 字段是否是主键
         */
        private Boolean ifPrimaryKey;

        /**
         * 主键字段是否自增
         */
        private Boolean ifAutoincrement;

        /**
         * 警告信息 目前只用于主键上 主键没有自动递增
         */
        private String notAutoincrementWarnMsg;

        /**
         * 警告信息 目前只用于主键上 主键是多个字段组成，而非单个字段
         */
        private String multiplePrimaryKeyColumnWarnMsg;

        public ColumnInfo() {
        }

        public ColumnInfo(String originalColumnName, String columnName, String columnType, String columnRemarks, Boolean ifNull, Boolean ifPrimaryKey, Boolean ifAutoincrement) {
            this.originalColumnName = originalColumnName;
            this.columnName = columnName;
            this.columnType = columnType;
            this.columnRemarks = columnRemarks;
            this.ifNull = ifNull;
            this.ifPrimaryKey = ifPrimaryKey;
            this.ifAutoincrement = ifAutoincrement;
        }

        public String getOriginalColumnName() {
            return originalColumnName;
        }

        public void setOriginalColumnName(String originalColumnName) {
            this.originalColumnName = originalColumnName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public String getColumnRemarks() {
            return columnRemarks;
        }

        public void setColumnRemarks(String columnRemarks) {
            this.columnRemarks = columnRemarks;
        }

        public Boolean getIfNull() {
            return ifNull;
        }

        public void setIfNull(Boolean ifNull) {
            this.ifNull = ifNull;
        }

        public Boolean getIfPrimaryKey() {
            return ifPrimaryKey;
        }

        public void setIfPrimaryKey(Boolean ifPrimaryKey) {
            this.ifPrimaryKey = ifPrimaryKey;
        }

        public Boolean getIfAutoincrement() {
            return ifAutoincrement;
        }

        public void setIfAutoincrement(Boolean ifAutoincrement) {
            this.ifAutoincrement = ifAutoincrement;
        }

        public String getNotAutoincrementWarnMsg() {
            return notAutoincrementWarnMsg;
        }

        public void setNotAutoincrementWarnMsg(String notAutoincrementWarnMsg) {
            this.notAutoincrementWarnMsg = notAutoincrementWarnMsg;
        }

        public String getMultiplePrimaryKeyColumnWarnMsg() {
            return multiplePrimaryKeyColumnWarnMsg;
        }

        public void setMultiplePrimaryKeyColumnWarnMsg(String multiplePrimaryKeyColumnWarnMsg) {
            this.multiplePrimaryKeyColumnWarnMsg = multiplePrimaryKeyColumnWarnMsg;
        }

        @Override
        public String toString() {
            return "ColumnInfo{" +
                    "originalColumnName='" + originalColumnName + '\'' +
                    ", columnName='" + columnName + '\'' +
                    ", columnType='" + columnType + '\'' +
                    ", columnRemarks='" + columnRemarks + '\'' +
                    ", ifNull=" + ifNull +
                    ", ifPrimaryKey=" + ifPrimaryKey +
                    ", ifAutoincrement=" + ifAutoincrement +
                    ", notAutoincrementWarnMsg='" + notAutoincrementWarnMsg + '\'' +
                    ", multiplePrimaryKeyColumnWarnMsg='" + multiplePrimaryKeyColumnWarnMsg + '\'' +
                    '}';
        }
    }

}
