package com.etone.project.modules.lte.dao;

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
public interface LteCommentMapper {

    public List<Map> queryArticle(QueryCriteria criteria);

    public List<Map> queryComment(QueryCriteria criteria);

    public void insertThumbSup(QueryCriteria criteria);

    public void insertReadThumbSup(QueryCriteria criteria);

    public void deleteThumbSup(QueryCriteria criteria);

    public void saveReadAloudInfo(QueryCriteria criteria);

    public void saveReadInfo(QueryCriteria criteria);

    public int validThumbSup(QueryCriteria criteria);

    public void insertComment(QueryCriteria criteria);

    public void insertReadComment(QueryCriteria criteria);

    public void saveRecordInfo(QueryCriteria criteria);

    public List<Map> queryAudio(QueryCriteria criteria);

    public String validReadAloudInfo(QueryCriteria criteria);

    public void deleteRecordInfo(int id);

    public void updateReadAloudInfo(QueryCriteria criteria);

    public List<Map> queryThumbsup(QueryCriteria criteria);

    public int countSameComment(QueryCriteria criteria);

    public List<Map> queryReadStatistics(QueryCriteria criteria);

    public List<Map> queryReadAloudStatistics(QueryCriteria criteria);

    public List<Map> queryCommentStatistics(QueryCriteria criteria);

    public String queryReadTime(QueryCriteria criteria);

    public String queryReadAloudTime(QueryCriteria criteria);

    public String queryCommentTime(QueryCriteria criteria);

    public Integer countUser();

    public String queryReadUnfinishUser(String createTime);

    public String queryAloudUnfinishUser(String createTime);

    public String queryCommentUnfinishUser(String createTime);

    public void deleteComment(QueryCriteria criteria);

    public List<Map> queryReadInfo(QueryCriteria criteria);

    public List<Map> queryReadAloudInfo(QueryCriteria criteria);

    public List<Map> queryCommentInfo(QueryCriteria criteria);

    public void setUserLocalId(QueryCriteria criteria);

    public void updatePictureAddress(QueryCriteria criteria);

    public String findHeadPortraitAddress(String account);
}