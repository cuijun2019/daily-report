package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-10-10 上午9:57:59
 */
public interface ILteCommentManager {

    public String getToken(String corpid, String corpsecret);

    public String downloadMedia(String token, String serverIds, String filePath, String fileType);

    public List<Map> queryArticle(QueryCriteria criteria);

    public List<Map> queryComment(QueryCriteria criteria);

    public boolean saveThumbSup(long readAloudId, long isRead);

    public void removeThumbSup(long readAloudId);

    public void saveReadAloudInfo(QueryCriteria criteria);

    public void saveReadInfo(QueryCriteria criteria);

    public void saveComment(QueryCriteria criteria);

    public List<Map> queryAudio(String date);

    public boolean validCurrentTime(String date);

    public boolean validSameComment(QueryCriteria criteria);

    public List<Map> queryReadStatistics(QueryCriteria criteria);

    public List<Map> queryReadAloudStatistics(QueryCriteria criteria);

    public List<Map> queryCommentStatistics(QueryCriteria criteria);

    public String queryReadUnfinishUser(String createTime);

    public String queryAloudUnfinishUser(String createTime);

    public String queryCommentUnfinishUser(String createTime);

    public void deleteComment(QueryCriteria criteria);

    public List<Map> queryReadInfo(QueryCriteria criteria);

    public List<Map> queryReadAloudInfo(QueryCriteria criteria);

    public List<Map> queryCommentInfo(QueryCriteria criteria);

    public void setUserLocalId(QueryCriteria criteria);

    public void updatePictureAddress(String serverIds, String filePath, String userId);

    public String findHeadPortraitAddress(String account);
}