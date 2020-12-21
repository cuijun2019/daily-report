/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.core.web.control;

import com.etone.commons.json.JsonUtil;
import com.etone.ee.modules.web.Servlets;
import com.etone.project.base.entity.share.Dictionary;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.DictionaryManager;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.QueryCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.etone.project.modules.lte.manager.ILteUserExperManager;
import com.etone.project.utils.Global;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础控制器
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-9-4
 */
public abstract class GenericController {
    // members

    // static block
    // constructors
    // properties
    @Autowired
    private DictionaryManager dictionaryManager;
    @Autowired
    private ILteUserExperManager lteUserExperManager;
    
    private Map<String, String> faZhi;
    private Map<String, Map> kpi;
    
    public Map<String, String> getFaZhi() {
        if(this.faZhi == null) {
            this.faZhi = new HashMap();
        }
        
        List<Dictionary> dictionaryList = dictionaryManager.findAll();

        for(Dictionary dictionary : dictionaryList) {
            if(dictionary.vcKey!=null&&dictionary.createUser!=null&&(dictionary.createUser).equals(String.valueOf(this.getUser().id))){
                faZhi.put(dictionary.vcKey, dictionary.value);
            }
        }
        
        return faZhi;
    }
    
    /**
     * 将阈值设置到criteria
     * @param criteria 
     */
    public void setFaZhitoQueryCriteria(QueryCriteria criteria) {
        
        Map<String, String> faZhi1 = this.getFaZhi();
        
        Map<String, Object> condition = criteria.getCondition();
        if(condition == null) {
            criteria.setCondition(new HashMap<String, Object>());
            condition = criteria.getCondition();
        }
        
        condition.putAll(faZhi1);
    }

    /**
     * Kpi设置到criteria
     * @param criteria
     */
    public void setKpiCriteria(QueryCriteria criteria) {
        Object intKpiClassify = criteria.get("intKpiClassify");
        if(intKpiClassify == null){
            criteria.put("intKpiClassify",1);
        }
        if(this.kpi == null) {
            this.kpi = new HashMap();
        }
     /*   List<Map> kpiList = lteUserExperManager.findKpiTree(criteria);
        for(Map map : kpiList) {
            kpi.put(map.get("id")+"",map);
        }
        criteria.put("kpiMap",kpi);*/
    }


    /**
     * 取得当前用户信息
     *
     * @return
     */
    public User getUser() {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUser();
    }

    // public methods
    // protected methods
    /**
     * 提取查询参数
     *
     * @param request
     * @return
     */
    protected Map<String, Object> getParameters(HttpServletRequest request) {
        return Servlets.getParameters(request, "");
    }

    /**
     * 初始化查询参数
     *
     * @param query
     * @return
     */
    protected void initParameters(QueryCriteria criteria, Map<String, Object> query) {
        //query.put("sorting", "{'orderType':'ASC','orderName':'name1,name2'}");
        String paginationString = (String) query.get("pagination");
        if (paginationString != null && !"".equals(paginationString)) {
            Map<String, Integer> pagination = JsonUtil.toMap(paginationString);
            String orderType = (String) query.get("direction");
            String orderName = (String) query.get("field");
            if (pagination != null) {
                Integer pageSize = pagination.get("pageSize");
                Integer currentPage = pagination.get("currentPage");
                // 设置分页信息
                if (pageSize != null && currentPage != null) {
                    criteria.setRowEnd(pageSize * currentPage);
                    criteria.setRowStart(pageSize * (currentPage - 1));
                    criteria.setPageNo(currentPage);
                    criteria.setPageSize(pageSize);
                }
            }
            if (orderName != null && !"".equals(orderName.trim()) && orderType != null && !"".equals(orderType.trim())) {
                // 排序信息
                criteria.setOrderByClause(orderName);
                criteria.setOrderType(orderType);
            }
        }
        User user = this.getUser();
        Map<String, Object> condition = criteria.getCondition();
        if(condition == null) {
            criteria.setCondition(new HashMap<String, Object>());
            condition = criteria.getCondition();
        }
        condition.put("userId", user.id);   //将用户ID加入参数中
        condition.put("tipAnswer", user.tipAnswer);   //将用户ID加入参数中
        this.setFaZhitoQueryCriteria(criteria);
        //初始化指标
        this.setKpiCriteria(criteria);
        Global.checkProperty();
    }

    /**
     * 初始化数据字典参数
     *
     * @param
     * @return
     */
    protected void initDictionary(QueryCriteria criteria) {
        List<Dictionary> dictionaryList = dictionaryManager.findAll();
        Map<String, String> dictionaryMap = new HashMap<String, String>();
        for (Dictionary dct : dictionaryList) {
            dictionaryMap.put(dct.getCode(), dct.getValue());
        }
        criteria.put("dictionaryMap", dictionaryMap);
    }
    // friendly methods
    // private methods
    // inner class
    // test main
}
