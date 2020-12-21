package com.etone.project.modules.lte.manager.impl;

import Alert.weChat.send_weChatMsg;
import Alert.weChat.urlData;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteCommentMapper;
import com.etone.project.modules.lte.manager.ILteCommentManager;
import com.etone.project.utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
@Service
@Transactional
public final class LteCommentManager implements ILteCommentManager {

	private static final Logger logger = LoggerFactory.getLogger(LteCommentManager.class);

	@Autowired
	private LteCommentMapper lteCommentMapper;

    @Cacheable(value="accessTokenCache")
    public String getToken(String corpid, String corpsecret) {
        send_weChatMsg sw = new send_weChatMsg();
        Gson gson = new Gson();
        urlData uData = new urlData();
        try {
            uData.setGet_Token_Url(corpid,corpsecret);
            String resp = sw.toAuth(uData.getGet_Token_Url());

            Map<String, Object> map = gson.fromJson(resp,
                    new TypeToken<Map<String, Object>>() {
                    }.getType());
            return map.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String downloadMedia(String token, String serverIds, String filePath, String fileType) {
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        URL u = null;
        String url = "";
        for (String serverId : serverIds.split(",")) {
            url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + token + "&media_id=" + serverId;
            try {
                System.out.println("-------------------:" + url);
                u = new URL(url);
                conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(conn.getConnectTimeout() + 30 * 1000);

                bis = new BufferedInputStream(conn.getInputStream());
                fos = new FileOutputStream(new File(filePath + serverId + "." + fileType));
                byte[] buf = new byte[8096];
                int size = 0;
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
                String info = String.format("下载媒体文件成功, filePath=" + filePath + serverId + "." + fileType);
                System.out.println(info);
            } catch (Exception e) {
                e.printStackTrace();
                filePath = null;
                String error = String.format("下载媒体文件失败:%s", e);
                System.out.println(error);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                        fos = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bis != null) {
                    try {
                        bis.close();
                        bis = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                conn.disconnect();
            }
        }
        System.out.println("filePath:" + filePath + serverIds + "." + fileType);
        return filePath + serverIds + "." + fileType;
    }

    @Override
    public List<Map> queryArticle(QueryCriteria criteria) {
        List<Map> list = lteCommentMapper.queryArticle(criteria);
        return list;
    }

    @Override
    public List<Map> queryComment(QueryCriteria criteria) {
        List<Map> list = lteCommentMapper.queryComment(criteria);
        if (list == null || list.isEmpty()) {
            list = lteCommentMapper.queryThumbsup(criteria);
        }
        return list;
    }

    @Override
    public boolean saveThumbSup(long readAloudId, long isRead) {
        QueryCriteria criteria = new QueryCriteria();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("thumbsUpPersonCode", shiroUser.getUser().getAccount());
            criteria.put("thumbsUpPerson", shiroUser.getUser().getFullName());
        } else {
            criteria.put("thumbsUpPersonCode", "3223");
            criteria.put("thumbsUpPerson", "崔军");
        }
        criteria.put("readAloudId", readAloudId);
        criteria.put("isRead", isRead);
        if (lteCommentMapper.validThumbSup(criteria) > 0) {
            lteCommentMapper.deleteThumbSup(criteria);
            return false;
        } else {
            if (isRead == 1) {
                lteCommentMapper.insertThumbSup(criteria);
            } else {
                lteCommentMapper.insertReadThumbSup(criteria);
            }
            return true;
        }
    }

    @Override
    public void removeThumbSup(long readAloudId) {
        QueryCriteria criteria = new QueryCriteria();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("account", shiroUser.getUser().getAccount());
        } else {
            criteria.put("account", "3223");
        }
        criteria.put("readAloudId", readAloudId);
        lteCommentMapper.deleteThumbSup(criteria);
    }

    @Override
    public void saveComment(QueryCriteria criteria) {
        long isRead = Long.parseLong(String.valueOf(criteria.get("isRead")));
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("commentPersonCode", shiroUser.getUser().getAccount());
            criteria.put("commentPerson", shiroUser.getUser().getFullName());
        } else {
            criteria.put("commentPersonCode", "3223");
            criteria.put("commentPerson", "崔军");
        }
        if (isRead == 1) {
            lteCommentMapper.insertComment(criteria);
        } else {
            lteCommentMapper.insertReadComment(criteria);
        }
    }

    @Override
    public void saveReadAloudInfo(QueryCriteria criteria) {
        String recordAddress = String.valueOf(criteria.get("recordAddress"));
        String removeAddress =  String.valueOf(criteria.get("removeAddress"));
        String recordPath = "";
        String recordName = "";

        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("studentNo", shiroUser.getUser().getAccount());
            criteria.put("studentName", shiroUser.getUser().getFullName());
        } else {
            criteria.put("studentNo", "");
            criteria.put("studentName", "");
        }
//        判断当前天
        String idStr = lteCommentMapper.validReadAloudInfo(criteria);
        if (Common.judgeString(idStr)) {
            criteria.put("id", Integer.parseInt(idStr));
            lteCommentMapper.deleteRecordInfo(Integer.parseInt(idStr));
            lteCommentMapper.updateReadAloudInfo(criteria);
        } else {
            lteCommentMapper.saveReadAloudInfo(criteria);
        }

        for (String ra : recordAddress.split(";")) {
            recordPath = ra.split(",")[0];
            System.out.println(ra + "--" + recordPath);
            recordName = recordPath.substring(recordPath.lastIndexOf("/") + 1);
            if (recordPath.contains("/temp/")) {
                Common.exec("mv /opt/tomcat/webapps/ROOT/file/upload/temp/" + recordName + " /opt/tomcat/webapps/ROOT/file/upload/" + recordName);
            }
            criteria.put("recordAddress", recordPath.replace("temp/", ""));
            criteria.put("createTime", ra.split(",")[1]);
            lteCommentMapper.saveRecordInfo(criteria);
        }

        if (Common.judgeString(removeAddress)) {
            System.out.println("--------------removeAddress:" + removeAddress);
            if (removeAddress.startsWith(",")) {
                for (String remove : removeAddress.substring(1).split(",")) {
                    remove = remove.replace("http://wdr.etonetech.com", "/opt/tomcat/webapps/ROOT");
                    Common.exec("rm -rf " + remove);
                }
            }
        }
    }

    @Override
    public void saveReadInfo(QueryCriteria criteria) {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("studentNo", shiroUser.getUser().getAccount());
            criteria.put("studentName", shiroUser.getUser().getFullName());
        } else {
            criteria.put("studentNo", "");
            criteria.put("studentName", "");
        }
        lteCommentMapper.saveReadInfo(criteria);
    }

    @Override
    public List<Map> queryAudio(String date) {
        QueryCriteria criteria = new QueryCriteria();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("account", shiroUser.getUser().getAccount());
        } else {
            criteria.put("account", "3223");
        }
        criteria.put("date", date);
        return lteCommentMapper.queryAudio(criteria);
    }

    @Override
    public boolean validCurrentTime(String date) {
        return Common.getDateString(new Date()).equals(date) ? true : false;
    }

    @Override
    public boolean validSameComment(QueryCriteria criteria) {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            criteria.put("commentPersonCode", shiroUser.getUser().getAccount());
        } else {
            criteria.put("commentPersonCode", "");
        }
        return lteCommentMapper.countSameComment(criteria) > 0 ? true : false;
    }

    @Override
    public List<Map> queryReadStatistics(QueryCriteria criteria) {
        List<Map> newList = new ArrayList<Map>();
        Map tempMap = null;
        List<Map> list = lteCommentMapper.queryReadStatistics(criteria);
        String readAloudTime  = lteCommentMapper.queryReadTime(criteria);
        int userCount = lteCommentMapper.countUser();
        String endDate = String.valueOf(criteria.get("endDate"));
        String startDate = String.valueOf(criteria.get("startDate"));
        while (endDate.compareTo(startDate) >= 0) {
            if (Common.judgeString(readAloudTime)) {
                if (readAloudTime.contains(endDate)) {
                    for (Map map : list) {
                        if (0 == Integer.parseInt(String.valueOf(map.get("count")))) {
                            break;
                        }
                        if (endDate.equals(String.valueOf(map.get("createTime")))) {
                            newList.add(map);
                        }
                    }
                } else {
                    tempMap = new HashMap();
                    tempMap.put("count", userCount);
                    tempMap.put("createTime", endDate);
                    newList.add(tempMap);
                }
            } else {
                tempMap = new HashMap();
                tempMap.put("count", userCount);
                tempMap.put("createTime", endDate);
                newList.add(tempMap);
            }
            endDate = getPreDay(endDate);
        }
        return newList;
    }

    @Override
    public List<Map> queryReadAloudStatistics(QueryCriteria criteria) {
        List<Map> newList = new ArrayList<Map>();
        Map tempMap = null;
        List<Map> list = lteCommentMapper.queryReadAloudStatistics(criteria);
        String readAloudTime  = lteCommentMapper.queryReadAloudTime(criteria);
        int userCount = lteCommentMapper.countUser();
        String endDate = String.valueOf(criteria.get("endDate"));
        String startDate = String.valueOf(criteria.get("startDate"));
        while (endDate.compareTo(startDate) >= 0) {
            if (Common.judgeString(readAloudTime)) {
                if (readAloudTime.contains(endDate)) {
                    for (Map map : list) {
                        if (0 == Integer.parseInt(String.valueOf(map.get("count")))) {
                            break;
                        }
                        if (endDate.equals(String.valueOf(map.get("createTime")))) {
                            newList.add(map);
                        }
                    }
                } else {
                    tempMap = new HashMap();
                    tempMap.put("count", userCount);
                    tempMap.put("createTime", endDate);
                    newList.add(tempMap);
                }
            } else {
                tempMap = new HashMap();
                tempMap.put("count", userCount);
                tempMap.put("createTime", endDate);
                newList.add(tempMap);
            }
            endDate = getPreDay(endDate);
        }
        return newList;
    }

    @Override
    public List<Map> queryCommentStatistics(QueryCriteria criteria) {
        List<Map> newList = new ArrayList<Map>();
        Map tempMap = null;
        List<Map> list = lteCommentMapper.queryCommentStatistics(criteria);
        String readAloudTime  = lteCommentMapper.queryCommentTime(criteria);
        int userCount = lteCommentMapper.countUser();
        String endDate = String.valueOf(criteria.get("endDate"));
        String startDate = String.valueOf(criteria.get("startDate"));
        while (endDate.compareTo(startDate) >= 0) {
            if (Common.judgeString(readAloudTime)) {
                if (readAloudTime.contains(endDate)) {
                    for (Map map : list) {
                        if (0 == Integer.parseInt(String.valueOf(map.get("count")))) {
                            break;
                        }
                        if (endDate.equals(String.valueOf(map.get("createTime")))) {
                            newList.add(map);
                        }
                    }
                } else {
                    tempMap = new HashMap();
                    tempMap.put("count", userCount);
                    tempMap.put("createTime", endDate);
                    newList.add(tempMap);
                }
            } else {
                tempMap = new HashMap();
                tempMap.put("count", userCount);
                tempMap.put("createTime", endDate);
                newList.add(tempMap);
            }
            endDate = getPreDay(endDate);
        }
        return newList;
    }

    @Override
    public String queryReadUnfinishUser(String createTime) {
        return lteCommentMapper.queryReadUnfinishUser(createTime);
    }

    @Override
    public String queryAloudUnfinishUser(String createTime) {
        return lteCommentMapper.queryAloudUnfinishUser(createTime);
    }

    @Override
    public String queryCommentUnfinishUser(String createTime) {
        return lteCommentMapper.queryCommentUnfinishUser(createTime);
    }

    @Override
    public void deleteComment(QueryCriteria criteria) {
        lteCommentMapper.deleteComment(criteria);
    }

    @Override
    public List<Map> queryReadInfo(QueryCriteria criteria) {
         return lteCommentMapper.queryReadInfo(criteria);
    }

    @Override
    public List<Map> queryReadAloudInfo(QueryCriteria criteria) {
        return lteCommentMapper.queryReadAloudInfo(criteria);
    }

    @Override
    public List<Map> queryCommentInfo(QueryCriteria criteria) {
        return lteCommentMapper.queryCommentInfo(criteria);
    }

    @Override
    public void setUserLocalId(QueryCriteria criteria) {
        lteCommentMapper.setUserLocalId(criteria);
    }

    @Override
    public void updatePictureAddress(String serverIds, String filePath, String userId) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("serverIds", serverIds);
        criteria.put("filePath", filePath.replace("/opt/tomcat/webapps/ROOT", ""));
        criteria.put("userId", userId);
        lteCommentMapper.updatePictureAddress(criteria);
    }

    @Override
    public String findHeadPortraitAddress(String account) {
        return lteCommentMapper.findHeadPortraitAddress(account);
    }

    private String getPreDay(String date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date preDay = null;
        try {
            Date today = f.parse(date);
            c.setTime(today);
            c.add(Calendar.DAY_OF_MONTH, -1);// 今天+1天
            preDay = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f.format(preDay);
    }
}
