package com.etone.project.modules.lte.manager.impl;

import com.etone.project.base.entity.share.User;
import com.etone.project.core.db.CustomerContextHolder;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteLogMapper;
import com.etone.project.modules.lte.manager.ILteLogManager;
import com.etone.project.utils.BrowserAndIPAddrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-8-24
 * Time: 下午12:33
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class LteLogManager implements ILteLogManager {

    @Autowired
     private LteLogMapper lteLogMapper;
    /**
     * 日志插入
     * @param criteria,request
     * @return
     */
    public void insertLog(QueryCriteria criteria,HttpServletRequest request){
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        //时间信息
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        criteria.put("intYear",cal.get(Calendar.YEAR));
        criteria.put("intMonth",cal.get(Calendar.MONTH)+1);//Calendar的月份是从0开始数的，所以+1
        criteria.put("intDay",cal.get(Calendar.DAY_OF_MONTH));
        criteria.put("intHour",cal.get(Calendar.HOUR_OF_DAY));
        criteria.put("dtCreateTime",format.format(new Date()));
        //用户信息
        User user=(User)request.getSession().getAttribute("user");
        criteria.put("vcAccount",user.getAccount());
        criteria.put("intCityId",user.getIntCityId());
        criteria.put("vcRoom",user.getRoom());
        criteria.put("vcUserName",user.getFullName());
        criteria.put("vcIpv4", BrowserAndIPAddrUtils.getRemoteIpAddr( request));//IP
        criteria.put("vcBrowser", BrowserAndIPAddrUtils.checkBrowse( request)); //浏览器类型
        criteria.put("vcExpInfo","");
        this.lteLogMapper.insertLog(criteria);
    }

    /**
     * 日志查询-各地市登录次数统计
     * @param criteria,request
     * @return
     */
    public List<Map> getLoadCounts(QueryCriteria criteria ){
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        return this.lteLogMapper.getLoadCounts(criteria);
    }

    /**
     * 日志查询-各地市访问模块次数统计
     * @param criteria,request
     * @return
     */
    public List<Map> getModuleCounts(QueryCriteria criteria ){
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        return this.lteLogMapper.getModuleCounts(criteria);
    }

    /**
     * 日志查询-各地市访问模块次数柱状图查询
     * @param criteria,request
     * @return
     */
    public List<Map> fillTop5Chart_lastMonth(QueryCriteria criteria ){
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        return this.lteLogMapper.fillTop5Chart_lastMonth(criteria);
    }

    /**
     * 日志查询-各模块中访问次数TOP5数据查询
     * @param criteria,request
     * @return
     */
    public List<Map> fillTop5Chart_thisMonth(QueryCriteria criteria ){
        CustomerContextHolder.setContextType(CustomerContextHolder.SESSION_FACTORY_MYSQL);
        return this.lteLogMapper.fillTop5Chart_thisMonth(criteria);
    }

}
