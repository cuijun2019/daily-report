package com.etone.project.modules.ltequality.manager.impl;

import com.etone.project.core.db.CustomerContextHolder;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.ltequality.dao.LteIndexMapper;
import com.etone.project.modules.ltequality.manager.ILteIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 90463 on 2016-12-15.
 */


@Service("LteIndexManager")
@Transactional
public class LteIndexManager implements ILteIndexManager {

    @Autowired
    private LteIndexMapper indexMapper;
    /**
     * 二级联动一级下拉获取
     */
    public List<Map> findFirstModule(QueryCriteria criteria){

        return indexMapper.findFirstModule(criteria);
    }

    /**
     * 二级联动二级下拉获取
     */
    public List<Map> findSecondModule(QueryCriteria criteria){
        return indexMapper.findSecondModule(criteria);
    }

    /**
     * 可查询数据SQL
     */
    public List<Map> findSecondTable(QueryCriteria criteria){
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_GP);
        Object choseTime=criteria.get("choseTime");
        Object day7=criteria.get("day7");
        Object ModuletableName=criteria.get("ModuletableName");
            String sql=" SELECT to_date(slicetime, 'yyyymmdd') intdateid,total,d_city_info.intcityname,isused FROM search_data_log LEFT JOIN d_city_info on search_data_log.intcityid= d_city_info.intcityid WHERE tablename = '"+ModuletableName+"' AND to_date(slicetime, 'yyyymmdd') >= '"+day7+"'AND to_date(slicetime, 'yyyymmdd') <= '"+choseTime+"'AND d_city_info.intcityname IS NOT NULL GROUP BY slicetime,d_city_info.intcityname,total,isused ORDER BY intdateid";
            criteria.put("sql",sql);
            List<Map> actual = indexMapper.findSecondTable(criteria);
            return actual;
    }

    /**
     * 地市查询
     */
    public List findCity(QueryCriteria criteria){

        List list=new ArrayList();
        list.add("广州");
        list.add("潮州");
        list.add("东莞");
        list.add("佛山");
        list.add("惠州");
        list.add("河源");
        list.add("揭阳");
        list.add("江门");
        list.add("梅州");
        list.add("茂名");
        list.add("清远");
        list.add("汕头");
        list.add("深圳");
        list.add("韶关");
        list.add("汕尾");
        list.add("阳江");
        list.add("云浮");
        list.add("珠海");
        list.add("中山");
        list.add("湛江");
        list.add("肇庆");

        return list;
    }

    /**
     * 查询模块用表的表名
     */
    public String findTableName(QueryCriteria criteria){
        return indexMapper.findTableName(criteria);
    }


}
