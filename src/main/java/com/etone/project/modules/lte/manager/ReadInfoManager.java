package com.etone.project.modules.lte.manager;

import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.*;
import com.etone.project.modules.lte.dao.ReadInfoMapper;
import com.etone.project.utils.Common;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-10-12
 * Time: 上午11:57
 * To change this template use File | Settings | File Templates.
 */

@Service
public class ReadInfoManager{
    private static final Logger logger = LoggerFactory.getLogger(ReadInfoManager.class);

    @Autowired
    private ReadInfoMapper infoManager;

    /**
     * 取得当前用户信息
     *
     * @return
     */
    public User getUser() {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUser();
    }

    public List<ReadInfo> selectById(String date, String userId) {
        if (!Common.judgeString(userId)) {
            userId = getUser().getAccount();
        }
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
//        String nowdate = dateFormat.format(now);
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("studentNo", userId);
        params.put("createTime", date);
//        List<Map> data = infoManager.selectById(String.valueOf(userId));
        List<ReadInfo> data = infoManager.selectById(params);
        return data;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ReadInfo> selectRloudById(String date){
        Map<String,Object> params=new HashMap<String, Object>();
        String userId = getUser().getAccount();
        params.put("studentNo", userId);
        params.put("createTime", date);
//        List<Map> data = infoManager.selectById(String.valueOf(userId));
        List<ReadInfo> data = infoManager.selectRloudById(params);
        return data;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<CommentInfo> selectCommentById(String date){
        Map<String,Object> params=new HashMap<String, Object>();
        String userId = getUser().getAccount();
        params.put("studentNo", userId);
        params.put("createTime", date);
//        List<Map> data = infoManager.selectById(String.valueOf(userId));
        List<CommentInfo> data = infoManager.selectCommentById(params);
        return data;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Result saveOrUpdate(Map map){
        Result result = null;
        Map<String,Object> newlist = new HashMap<String, Object>();
        String studentNo = getUser().getAccount();
        String studentName = getUser().getFullName();
//        Set<String> set=map.keySet();
        try{
            for (Object key :map.keySet()) {
                if(Integer.parseInt(String.valueOf(map.get("articleid"))) == -1){
//                    newlist.put
                    newlist.put("articleid",map.get("articleid"));
                    newlist.put("article",map.get("article"));
                    newlist.put("thought",map.get("thought"));
                    newlist.put("createTime",map.get("createTime"));
                    newlist.put("isSave",map.get("isSave"));
                    newlist.put("studentNo",studentNo);
                    newlist.put("studentName",studentName);
                    infoManager.save(newlist);
                    result = Result.successResult();
//                    map.remove("articleid");
                    break;
                }else{
                    newlist.put("articleid",map.get("articleid"));
                    newlist.put("article",map.get("article"));
                    newlist.put("thought",map.get("thought"));
                    newlist.put("createTime",map.get("createTime"));
                    newlist.put("isSave",map.get("isSave"));
                    newlist.put("studentNo",studentNo);
                    newlist.put("studentName",studentName);
                    infoManager.update(newlist);
                    result = Result.successResult();
//                    map.remove("articleid");
                    break;
                }
            }
        } catch(Exception e) {
            //将sqrt方法声明的可能抛出的Exception异常捕获
            //打印捕获的异常的堆栈信息，从堆栈信息中可以发现异常发生的位置和原因
            System.out.println("Got a Exception：" + e.getMessage());
            e.printStackTrace();
        }


        return result;
    }

    public List<Map> selectReadNum(String beginDate,String endDate){
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        return infoManager.selectReadNum(params);
    }

    public List<Map> selectTalkNum(String beginDate,String endDate){
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        return infoManager.selectTalkNum(params);
    }

    public List<Map> selectDpNum(String beginDate,String endDate){
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        return infoManager.selectDpNum(params);
    }

    public List<Map> getQuestFinish(String date){

        Map<String,Object> params=new HashMap<String, Object>();
        String userId = getUser().getAccount();
        params.put("studentNo", userId);
        params.put("createTime", date);
        return infoManager.getQuestFinish(params);
    }

    public List<Map> findUnFinishRead(String date){
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("createTime", date);

        return  infoManager.findUnFinishRead(params);
    }

    public List<Map> findUnFinishTalk(String date){
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("createTime", date);

        return  infoManager.findUnFinishTalk(params);
    }

    public List<Map> findUnFinishDp(String date){
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("createTime", date);

        return  infoManager.findUnFinishDp(params);
    }

    public List<Map> getTaskFinish(QueryCriteria criteria) {
        List<Map> list = new ArrayList<Map>();
        Map map = null;
        int totalDay = Integer.parseInt(criteria.get("totalDay").toString());
        String finishRead = infoManager.queryFinishRead(criteria);
        String finishReadAloud = infoManager.queryFinishReadAloud(criteria);
        String finishComment = infoManager.queryFinishComment(criteria);
        String currentDay = "";
        int finishCount = 0;
        for (int i = 1; i <= totalDay; i++) {
            map = new HashMap();
            finishCount = 0;
            if (i <= 9) {
                currentDay = "0" + i;
            } else {
                currentDay = i + "";
            }
            if (Common.judgeString(finishRead) && finishRead.contains(currentDay)) {
                finishCount++;
            }
            if (Common.judgeString(finishReadAloud) && finishReadAloud.contains(currentDay)) {
                finishCount++;
            }
            if (Common.judgeString(finishComment) && finishComment.contains(currentDay)) {
                finishCount++;
            }
            map.put("i", i);
            map.put("finishCount", finishCount);
            list.add(map);
        }
        return list;
    }
}
