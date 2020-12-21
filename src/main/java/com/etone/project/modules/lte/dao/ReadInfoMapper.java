package com.etone.project.modules.lte.dao;

import com.etone.ee.modules.persistence.MyBatisRepository;
import com.etone.project.core.model.CommentInfo;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.ReadInfo;
import com.etone.project.core.model.Statistics;

import java.util.List;
import java.util.Map;

/**
 */
@MyBatisRepository
public interface ReadInfoMapper {
    /**
     * 根据用户ID查询阅读模块数据
     * @param
     * @return
     */
    public List<ReadInfo> selectById(Map map);

    public List<ReadInfo> selectRloudById(Map map);

    public List<CommentInfo> selectCommentById(Map map);

    public List<Map> findUnFinishRead(Map map);

    public List<Map> findUnFinishTalk(Map map);

    public List<Map> findUnFinishDp(Map map);

    public List<Map> selectReadNum(Map map);

    public List<Map> selectTalkNum(Map map);

    public List<Map> selectDpNum(Map map);

    public List<Map> getQuestFinish(Map map);

    public void update(Map map);

    public void save(Map map);

    public String queryFinishRead(QueryCriteria criteria);

    public String queryFinishReadAloud(QueryCriteria criteria);

    public String queryFinishComment(QueryCriteria criteria);
}