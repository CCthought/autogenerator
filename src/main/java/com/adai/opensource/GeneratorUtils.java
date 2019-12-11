package com.adai.opensource;

import com.adai.opensource.core.*;

/**
 * @author zhouchengpei
 * date    2019/12/11 10:12
 * description .
 */
public class GeneratorUtils {

    public static void main(String[] args) {
        execute("adai", "student");
    }

    private static void execute(String database, String tableName){
        CreateController.execute(database, tableName);
        CreateDao.execute(database, tableName);
        CreateEntity.execute(database, tableName);
        CreateMapper.execute(database, tableName);
        CreateService.execute(database, tableName);
        CreateServiceImpl.execute(database,tableName);
    }

}
