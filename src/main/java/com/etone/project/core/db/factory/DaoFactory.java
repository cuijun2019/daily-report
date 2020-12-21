package com.etone.project.core.db.factory;

import com.etone.project.modules.lte.dao.LteMsgDao;
/**
 * 一个DAO工厂类，用于生成各种DAO DAO以单态的方式生成
 * 
 * @author guojian
 * @version $$Revision: 1.1 $$
 * @date 2014-04-03
 */
public class DaoFactory {
    private static LteMsgDao lteMsgDao;
    
    private DaoFactory() {
    }
    
    public static LteMsgDao getLteMsgDao(){
        if (lteMsgDao == null) {
            lteMsgDao = new LteMsgDao();
        }
        return lteMsgDao;
    }
    
    
    
}
