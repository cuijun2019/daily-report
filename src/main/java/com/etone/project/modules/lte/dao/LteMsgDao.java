/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etone.project.modules.lte.dao;

import com.etone.project.core.db.factory.ABaseMySQLDAO;
import com.etone.project.core.model.QueryCriteria;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入库dao
 * @author guojian
 * @version $$Revision: 1.1 $$
 * @date 2014-04-07
 */
public class LteMsgDao extends ABaseMySQLDAO<Object> {

    private static Logger logger = LoggerFactory.getLogger(LteMsgDao.class);

    /**
     * 查询用户通信过程记录数
     * @return
     */
    public Map<String,Object> findUserMsgList(QueryCriteria criteria){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String,Object> resultMap = new HashMap<String, Object>();
        List<Map> resultList = Lists.newArrayList();
        int count = 0;
        String sqlView1 = "",sqlView2 = "",sqlView3 = "";
        /**
         * 从连接池中取出可用的连接
         */
        conn = getConn();
        try {

            List<String> sourceTableNameUuSessList = (ArrayList)criteria.get("sourceTableNameUuSessList");
            List<String> sourceTableNameUuMsgList = (ArrayList)criteria.get("sourceTableNameUuMsgList");
            List<String> sourceTableNameUuExtenMsgList = (ArrayList)criteria.get("sourceTableNameUuExtenMsgList");
            List<String> haveTableNameUuSessList = Lists.newArrayList();
            List<String> haveTableNameUuMsgList = Lists.newArrayList();
            List<String> haveTableNameUuExtenMsgList = Lists.newArrayList();

            /**
             * 判断数据库是否存在表
             */
            for(String tableName : sourceTableNameUuSessList){
                String sql = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='lte_meter' and `TABLE_NAME`='"+tableName+"' ";
                logger.info("Execute SQL:{}",sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                boolean haveTable = false;
                if (rs != null) {
                    while (rs.next()) {
                        haveTable = true;
                    }
                }
                if(haveTable){
                    haveTableNameUuSessList.add(tableName);
                }
            }
            for(String tableName : sourceTableNameUuMsgList){
                String sql = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='lte_meter' and `TABLE_NAME`='"+tableName+"' ";
                logger.info("Execute SQL:{}",sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                boolean haveTable = false;
                if (rs != null) {
                    while (rs.next()) {
                        haveTable = true;
                    }
                }
                if(haveTable){
                    haveTableNameUuMsgList.add(tableName);
                }
            }
            for(String tableName : sourceTableNameUuExtenMsgList){
                String sql = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='lte_meter' and `TABLE_NAME`='"+tableName+"' ";
                logger.info("Execute SQL:{}",sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                boolean haveTable = false;
                if (rs != null) {
                    while (rs.next()) {
                        haveTable = true;
                    }
                }
                if(haveTable){
                    haveTableNameUuExtenMsgList.add(tableName);
                }
            }

            /**
             * 创建视图
             */

            for(String tableName : haveTableNameUuSessList){
                sqlView1 = "create view "+criteria.get("vwAnalyse_UuSession")+" as";
                sqlView1 += " select * from "+tableName+" union all";
            }
            if(!"".endsWith(sqlView1)){
                sqlView1 = sqlView1.substring(0,sqlView1.length()-9);
                logger.info("Execute SQL:{}",sqlView1);
                ps = conn.prepareStatement(sqlView1);
                ps.execute();
            }
            for(String tableName : haveTableNameUuMsgList){
                sqlView2 = "create view "+criteria.get("vwAnalyse_UuAllMsg")+" as";
                sqlView2 += " select * from "+tableName+" union all";
            }
            if(!"".endsWith(sqlView2)){
                sqlView2 = sqlView2.substring(0,sqlView2.length()-9);
                logger.info("Execute SQL:{}",sqlView2);
                ps = conn.prepareStatement(sqlView2);
                ps.execute();
            }
            for(String tableName : haveTableNameUuExtenMsgList){
                sqlView3 = "create view "+criteria.get("vwAnalyse_UuExtendAllMsg")+" as";
                sqlView3 += " select * from "+tableName+" union all";
            }
            if(!"".endsWith(sqlView3)){
                sqlView3 = sqlView3.substring(0,sqlView3.length()-9);
                logger.info("Execute SQL:{}",sqlView3);
                ps = conn.prepareStatement(sqlView3);
                ps.execute();
            }

            /**
             * 业务查询
             */
            if(!"".equals(sqlView1)&&!"".equals(sqlView2)&&!"".equals(sqlView3)){
                String whereInfo = " where intENbID ="+criteria.get("intENbID")+" and intCellID ="+criteria.get("intCellID");
                if("MMEGI".equals(criteria.get("mme_name"))&&!"".equals(((String)criteria.get("mme_value")).trim())){
                    whereInfo += " and intMMEGI ="+criteria.get("mme_value");
                } else if("MMEC".equals(criteria.get("mme_name"))&&!"".equals(((String)criteria.get("mme_value")).trim())){
                    whereInfo += " and intMMEC ="+criteria.get("mme_value");
                }
                if("IMSI".equals(criteria.get("imue_name"))){
                    //whereInfo += " and intIMSI ="+criteria.get("imue_value");
                } else if("MMEUEID".equals(criteria.get("imue_name"))&&!"".equals(((String)criteria.get("imue_value")).trim())){
                    whereInfo += " and intMMEUEID ="+criteria.get("imue_value");
                }
                String sqlLast = "select t1.intMMEUEID\n" +
                        ",vcRRCConnectSuccessRate,vcRRCReconnectSuccessRate,vcRRCConnectDurationRate\n" +
                        ",vcRsrpAvg,vcRsrqAvg\n" +
                        ",vcMrTAAvg,vcMrPhrAvg,vcMrRttdAvg,vcUp64QAMAcco,vcUpSinrAvg,vcUpPrbRate,vcUpHarqRsRate\n" +
                        ",vcDown64QamRate,vcDownPrbRate,vcDownHarqRate,vcUpULLossRate,vcUpULServiceBytes,vcUserPlaneULServiceAvg\n" +
                        ",vcUserPlaneULServiceRate,vcUpDLLossRate,vcUpDLServiceBytes,vcUserPlaneULServiceAvg,vcUserPlaneDLServiceRate,vcDLSentPDCPSDUPacketRate\n" +
                        "FROM\n" +
                        "(\n" +
                        "select intMMEUEID\n" +
                        ",case when sum(case when intSessType=200 then 1 else 0 end)>0 then sum(case when intSessType=200 and intResult=0 then 1 else 0 end)/sum(case when intSessType=200 then 1 else 0 end)*100 else 0 end vcRRCConnectSuccessRate\n" +
                        ",case when sum(case when intSessType=201 then 1 else 0 end)>0 then sum(case when intSessType=201 and intResult=0 then 1 else 0 end)/sum(case when intSessType=201 then 1 else 0 end)*100 else 0 end vcRRCReconnectSuccessRate\n" +
                        ",case when sum(case when intSessType=200 then 1 else 0 end)>0 then sum(case when intSessType=200 and intResult=0 then TIMEDIFF(dtETime,dtSTime) else 0 end)/sum(case when intSessType=200 then 1 else 0 end)*100 else 0 end vcRRCConnectDurationRate\n" +
                        "from "+criteria.get("vwAnalyse_UuSession")+whereInfo+"  GROUP BY intMMEUEID\n" +
                        ") t1 LEFT JOIN\n" +
                        "(\n" +
                        "select intMMEUEID\n" +
                        ",case when sum(case when intScRsrp>=0 and intScRsrp<=97 then 1 else 0 end)>0 then sum(case when intScRsrp>=0 and intScRsrp<=97 then intScRsrp else 0 end)/sum(case when intScRsrp>=0 and intScRsrp<=97 then 1 else 0 end) else 0 end - 141 vcRsrpAvg\n" +
                        ",cast((case when sum(case when intScRsrq>=0 and intScRsrq<=97 then 1 else 0 end)>0 then sum(case when intScRsrq>=0 and intScRsrq<=97 then intScRsrq else 0 end)/sum(case when intScRsrq>=0 and intScRsrq<=97 then 1 else 0 end) else 0 end - 40)/2 as decimal(10,3)) vcRsrqAvg\n" +
                        ",case when sum(case when intMR_TA>=0 and intMR_TA<=1282 then 1 else 0 end)>0 then sum(case when intMR_TA>=0 and intMR_TA<=1282 then intMR_TA else 0 end)/sum(case when intMR_TA>=0 and intMR_TA<=1282 then 1 else 0 end) else 0 end vcMrTAAvg\n" +
                        ",case when sum(case when intMR_UETransPowerMargin>=0 and intMR_UETransPowerMargin<=63 then 1 else 0 end)>0 then sum(case when intMR_UETransPowerMargin>=0 and intMR_UETransPowerMargin<=63 then intMR_UETransPowerMargin else 0 end)/sum(case when intMR_UETransPowerMargin>=0 and intMR_UETransPowerMargin<=63 then 1 else 0 end) else 0 end vcMrPhrAvg\n" +
                        ",case when count(intMR_UETransPowerMargin)>0 then sum(intMR_UETransPowerMargin)/count(intMR_UETransPowerMargin) else 0 end vcMrRttdAvg\n" +
                        ",case when sum(intSQ_UL_QPSK_TBAmount+intSQ_UL_16QAM_TBAmount+intSQ_UL_64QAM_TBAmount)>0 then sum(intSQ_UL_64QAM_TBAmount)/sum(intSQ_UL_QPSK_TBAmount+intSQ_UL_16QAM_TBAmount+intSQ_UL_64QAM_TBAmount)*100 else 0 end vcUp64QAMAcco\n" +
                        ",case when sum(case when intMR_ULSignalNoiseRatio<=36 then 1 else 0 end)>0 then sum(case when intMR_ULSignalNoiseRatio<=36 then intMR_ULSignalNoiseRatio else 0 end)/sum(case when intMR_ULSignalNoiseRatio<=36 then 1 else 0 end) else 0 end vcUpSinrAvg\n" +
                        ",case when sum(intSQ_UL_PRBOccupation)>0 then sum(intSQ_ULDRB_PRBOccupation)/sum(intSQ_UL_PRBOccupation)*100 else 0 end vcUpPrbRate\n" +
                        ",case when sum(intSQ_UL_QPSK_TBAmount+intSQ_UL_16QAM_TBAmount+intSQ_UL_64QAM_TBAmount-intSQ_UL_HARQRetrans_TBAmount)>0 then sum(intSQ_UL_HARQRetrans_TBAmount)/sum(intSQ_UL_QPSK_TBAmount+intSQ_UL_16QAM_TBAmount+intSQ_UL_64QAM_TBAmount-intSQ_UL_HARQRetrans_TBAmount)*100 else 0 end vcUpHarqRsRate\n" +
                        ",case when sum(intSQ_DL_QPSK_TBAmount+intSQ_DL_16QAM_TBAmount+intSQ_DL_64QAM_TBAmount)>0 then sum(intSQ_DL_64QAM_TBAmount)/sum(intSQ_DL_QPSK_TBAmount+intSQ_DL_16QAM_TBAmount+intSQ_DL_64QAM_TBAmount)*100 else 0 end vcDown64QamRate\n" +
                        ",case when sum(intSQ_DL_PRBOccupation)>0 then sum(intSQ_DLDRB_PRBOccupation)/sum(intSQ_UL_PRBOccupation)*100 else 0 end vcDownPrbRate\n" +
                        ",case when sum(intSQ_DL_QPSK_TBAmount+intSQ_DL_16QAM_TBAmount+intSQ_DL_64QAM_TBAmount-intSQ_DL_HARQRetrans_TBAmount)>0 then sum(intSQ_DL_HARQRetrans_TBAmount)/sum(intSQ_DL_QPSK_TBAmount+intSQ_DL_16QAM_TBAmount+intSQ_DL_64QAM_TBAmount-intSQ_DL_HARQRetrans_TBAmount)*100 else 0 end vcDownHarqRate\n" +
                        ",case when sum(intSQ_DRB_ULExpectRcvPDCPSDUPacket)>0 then sum(intSQ_DRB_PDCPSDU_ULLossPacket)/sum(intSQ_DRB_ULExpectRcvPDCPSDUPacket)*100 else 0 end vcUpULLossRate\n" +
                        ",sum(intCAP_UuUserPlaneULServiceBytes) vcUpULServiceBytes\n" +
                        ",case when count(intCAP_UuUserPlaneULServiceBytes)>0 then sum(intCAP_UuUserPlaneULServiceBytes)/count(intCAP_UuUserPlaneULServiceBytes)*100 else 0 end vcUserPlaneULServiceAvg\n" +
                        ",case when sum(case when intCAP_UuUserPlaneULServiceBytes>500*1024 then 1 else 0 end)>0 then sum(case when intCAP_UuUserPlaneDLServiceBytes>500*1024 then intCAP_UuUserPlaneULServiceBytes else 0 end)/sum(case when intCAP_UuUserPlaneULServiceBytes>500*1024 then 1 else 0 end) else 0 end vcUserPlaneULServiceRate\n" +
                        ",case when sum(intSQ_DRB_DLSentPDCPSDUPacket)>0 then sum(intSQ_DRB_PDCPSDU_DLLossPacket)/sum(intSQ_DRB_DLSentPDCPSDUPacket)*100 else 0 end vcUpDLLossRate\n" +
                        ",sum(intCAP_UuUserPlaneDLServiceBytes) vcUpDLServiceBytes\n" +
                        ",case when count(intCAP_UuUserPlaneDLServiceBytes)>0 then sum(intCAP_UuUserPlaneDLServiceBytes)/count(intCAP_UuUserPlaneDLServiceBytes)*100 else 0 end vcUserPlaneDLServiceAvg\n"+
                        ",case when sum(case when intCAP_UuUserPlaneDLServiceBytes>500*1024 then 1 else 0 end)>0 then sum(case when intCAP_UuUserPlaneDLServiceBytes>500*1024 then intCAP_UuUserPlaneDLServiceBytes else 0 end)/sum(case when intCAP_UuUserPlaneDLServiceBytes>500*1024 then 1 else 0 end) else 0 end vcUserPlaneDLServiceRate\n" +
                        ",case when sum(intSQ_DRB_DLSentPDCPSDUPacket)>0 then sum(intSQ_DRB_PDCPSDU_DLDiscardPacket)/sum(intSQ_DRB_DLSentPDCPSDUPacket)*100 else 0 end vcDLSentPDCPSDUPacketRate\n" +
                        "from "+criteria.get("vwAnalyse_UuAllMsg")+whereInfo+" GROUP BY intMMEUEID\n" +
                        ") t2 ON t1.intMMEUEID=t2.intMMEUEID\n" +
                        "LIMIT "+criteria.getRowStart()+","+criteria.getPageSize();
                logger.info("Execute SQL:{}",sqlLast);
                ps = conn.prepareStatement(sqlLast);
                rs = ps.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        Map mp = new HashMap();
                        mp.put("intMMEUEID",rs.getObject("intMMEUEID"));
                        mp.put("vcRRCConnectSuccessRate",rs.getDouble("vcRRCConnectSuccessRate"));
                        mp.put("vcRRCReconnectSuccessRate",rs.getDouble("vcRRCReconnectSuccessRate"));
                        mp.put("vcRRCConnectDurationRate",rs.getDouble("vcRRCConnectDurationRate"));
                        mp.put("vcRsrpAvg",rs.getDouble("vcRsrpAvg"));
                        mp.put("vcRsrqAvg",rs.getDouble("vcRsrqAvg"));
                        mp.put("vcMrTAAvg",rs.getDouble("vcMrTAAvg"));
                        mp.put("vcMrPhrAvg",rs.getDouble("vcMrPhrAvg"));
                        mp.put("vcMrRttdAvg",rs.getDouble("vcMrRttdAvg"));
                        mp.put("vcUp64QAMAcco",rs.getDouble("vcUp64QAMAcco"));
                        mp.put("vcUpSinrAvg",rs.getDouble("vcUpSinrAvg"));
                        mp.put("vcUpPrbRate",rs.getDouble("vcUpPrbRate"));
                        mp.put("vcUpHarqRsRate",rs.getDouble("vcUpHarqRsRate"));
                        mp.put("vcDown64QamRate",rs.getDouble("vcDown64QamRate"));
                        mp.put("vcDownPrbRate",rs.getDouble("vcDownPrbRate"));
                        mp.put("vcDownHarqRate",rs.getDouble("vcDownHarqRate"));
                        mp.put("vcUpULLossRate",rs.getDouble("vcUpULLossRate"));
                        mp.put("vcUpULServiceBytes",rs.getDouble("vcUpULServiceBytes"));
                        mp.put("vcUserPlaneULServiceAvg",rs.getDouble("vcUserPlaneULServiceAvg"));
                        mp.put("vcUserPlaneULServiceRate",rs.getDouble("vcUserPlaneULServiceRate"));
                        mp.put("vcUpDLLossRate",rs.getDouble("vcUpDLLossRate"));
                        mp.put("vcUpDLServiceBytes",rs.getDouble("vcUpDLServiceBytes"));
                        mp.put("vcUserPlaneULServiceAvg",rs.getDouble("vcUserPlaneULServiceAvg"));
                        mp.put("vcUserPlaneDLServiceRate",rs.getDouble("vcUserPlaneDLServiceRate"));
                        mp.put("vcDLSentPDCPSDUPacketRate",rs.getDouble("vcDLSentPDCPSDUPacketRate"));
                        resultList.add(mp);
                    }
                }
                //查询总数
                String sqlCount =  "select count(*) rt from (select intMMEUEID from "+criteria.get("vwAnalyse_UuSession")+whereInfo+"  GROUP BY intMMEUEID) t1";
                ps = conn.prepareStatement(sqlCount);
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        count = rs.getInt("rt");
                    }
                }
            }
            resultMap.put("resultList",resultList);
            resultMap.put("count",count);
        } catch (SQLException e) {
            logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                    { "查询异常:",e.getMessage(), e.getCause(), e.getClass() });

        } catch (Exception e) {
            logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                    { "查询异常:",e.getMessage(), e.getCause(), e.getClass() });
        } finally{
            try{
                /**
                 * 删除视图
                 */
                if(!"".equals(sqlView1)){
                    sqlView1 = "drop view " + criteria.get("vwAnalyse_UuSession");
                    ps = conn.prepareStatement(sqlView1);
                    ps.execute();
                    logger.info("Execute SQL:{}",sqlView1);
                }
                if(!"".equals(sqlView2)){
                    sqlView2 = "drop view " + criteria.get("vwAnalyse_UuAllMsg");
                    ps = conn.prepareStatement(sqlView2);
                    ps.execute();
                    logger.info("Execute SQL:{}",sqlView2);
                }
                if(!"".equals(sqlView3)){
                    sqlView3 = "drop view " + criteria.get("vwAnalyse_UuExtendAllMsg");
                    ps = conn.prepareStatement(sqlView3);
                    ps.execute();
                    logger.info("Execute SQL:{}",sqlView3);
                }
            }catch (Exception e){
                logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                        { "删除视图异常:",e.getMessage(), e.getCause(), e.getClass() });
                e.printStackTrace();;
            }
            /**
             * 将用完的连接放回连接池！！！注意，不然连接池的连接都会用完
             */
            super.closePreparedStatement(ps);
            releaseConn(conn);
        }
        return resultMap;
    }

    /**
     * 查询用户通信过程总数
     * @return
     */
    public int countUserMsgList(QueryCriteria criteria){
    	 Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         int count = 0;
         /**
          * 从连接池中取出可用的连接
          */
         conn = getConn();
         try {
         	String sql = "select count(*) ct from ftbUuExtendAllMsg300_2014070315";;
             ps = conn.prepareStatement(sql);
             rs = ps.executeQuery();
             if (rs != null) {
                 while (rs.next()) {
                	 count = rs.getInt("ct");
                 }
             }
         } catch (SQLException e) {
             logger.error(e.getMessage());

         } catch (Exception e) {
             logger.error(e.getMessage());
         } finally{
         	/**
              * 将用完的连接放回连接池！！！注意，不然连接池的连接都会用完
              */
         	super.closePreparedStatement(ps);
         	releaseConn(conn);
         }
         return count;
    }

    /**
     * 查询用户透视图
     * @return
     */
    public List<Map> findUserMsgChart(QueryCriteria criteria){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map> resultList = Lists.newArrayList();
        String sqlView2 = "",sqlView3 = "";
        /**
         * 从连接池中取出可用的连接
         */
        conn = getConn();
        try {
            List<String> haveTableNameUuMsgList = Lists.newArrayList();
            List<String> haveTableNameUuExtenMsgList = Lists.newArrayList();

            /**
             * 判断数据库是否存在表
             */
            if(criteria.get("sourceTableNameUuMsgList")!=null){
                List<String> sourceTableNameUuMsgList = (ArrayList)criteria.get("sourceTableNameUuMsgList");
                for(String tableName : sourceTableNameUuMsgList){
                    String sql = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='lte_meter' and `TABLE_NAME`='"+tableName+"' ";
                    logger.info("Execute SQL:{}",sql);
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    boolean haveTable = false;
                    if (rs != null) {
                        while (rs.next()) {
                            haveTable = true;
                        }
                    }
                    if(haveTable){
                        haveTableNameUuMsgList.add(tableName);
                    }
                }
            }
            if(criteria.get("sourceTableNameUuExtenMsgList")!=null){
                List<String> sourceTableNameUuExtenMsgList = (ArrayList)criteria.get("sourceTableNameUuExtenMsgList");
                for(String tableName : sourceTableNameUuExtenMsgList){
                    String sql = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='lte_meter' and `TABLE_NAME`='"+tableName+"' ";
                    logger.info("Execute SQL:{}",sql);
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    boolean haveTable = false;
                    if (rs != null) {
                        while (rs.next()) {
                            haveTable = true;
                        }
                    }
                    if(haveTable){
                        haveTableNameUuExtenMsgList.add(tableName);
                    }
                }
            }

            /**
             * 创建视图
             */
            if(criteria.get("vwAnalyse_UuAllMsg")!=null){
                for(String tableName : haveTableNameUuMsgList){
                    sqlView2 = "create view "+criteria.get("vwAnalyse_UuAllMsg")+" as";
                    sqlView2 += " select * from "+tableName+" union all";
                }
                if(!"".endsWith(sqlView2)){
                    sqlView2 = sqlView2.substring(0,sqlView2.length()-9);
                    logger.info("Execute SQL:{}",sqlView2);
                    ps = conn.prepareStatement(sqlView2);
                    ps.execute();
                }
            }
            if(criteria.get("vwAnalyse_UuExtendAllMsg")!=null){
                for(String tableName : haveTableNameUuExtenMsgList){
                    sqlView3 = "create view "+criteria.get("vwAnalyse_UuExtendAllMsg")+" as";
                    sqlView3 += " select * from "+tableName+" union all";
                }
                if(!"".endsWith(sqlView3)){
                    sqlView3 = sqlView3.substring(0,sqlView3.length()-9);
                    logger.info("Execute SQL:{}",sqlView3);
                    ps = conn.prepareStatement(sqlView3);
                    ps.execute();
                }
            }

            /**
             * 业务查询
             */
            if(!"".equals(sqlView2)||!"".equals(sqlView3)){
                String sqlLast = "";
                String whereInfo = " where intENbID ="+criteria.get("intENbID")+" and intCellID ="+criteria.get("intCellID")+" and intMMEUEID ="+criteria.get("intMMEUEID");
                if(!"".equals(sqlView2)){
                    sqlLast = "select dtTime,intENbID,intCellID,intMMEUEID,"+criteria.get("sqlField")+","+criteria.get("sqlField2")+" from "+criteria.get("vwAnalyse_UuAllMsg")+whereInfo+criteria.get("whereInfo");
                } if(!"".equals(sqlView3)){
                    sqlLast = "select dtTime,intENbID,intCellID,intMMEUEID,"+criteria.get("sqlField")+","+criteria.get("sqlField2")+" from "+criteria.get("vwAnalyse_UuExtendAllMsg")+whereInfo;
                }
                logger.info("Execute SQL:{}",sqlLast);
                ps = conn.prepareStatement(sqlLast);
                rs = ps.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        Map mp = new HashMap();
                        mp.put("dtTime",rs.getObject("dtTime"));
                        mp.put("intENbID",rs.getObject("intENbID"));
                        mp.put("intCellID",rs.getObject("intCellID"));
                        mp.put("intMMEUEID",rs.getObject("intMMEUEID"));
                        mp.put("drillField",rs.getObject("drillField"));
                        resultList.add(mp);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                    { "查询异常:",e.getMessage(), e.getCause(), e.getClass() });

        } catch (Exception e) {
            logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                    { "查询异常:",e.getMessage(), e.getCause(), e.getClass() });
        } finally{
            try{
                /**
                 * 删除视图
                 */
                if(!"".equals(sqlView2)){
                    sqlView2 = "drop view " + criteria.get("vwAnalyse_UuAllMsg");
                    ps = conn.prepareStatement(sqlView2);
                    ps.execute();
                    logger.info("Execute SQL:{}",sqlView2);
                }
                if(!"".equals(sqlView3)){
                    sqlView3 = "drop view " + criteria.get("vwAnalyse_UuExtendAllMsg");
                    ps = conn.prepareStatement(sqlView3);
                    ps.execute();
                    logger.info("Execute SQL:{}",sqlView3);
                }
            }catch (Exception e){
                logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                        { "删除视图异常:",e.getMessage(), e.getCause(), e.getClass() });
                e.printStackTrace();;
            }
            /**
             * 将用完的连接放回连接池！！！注意，不然连接池的连接都会用完
             */
            super.closePreparedStatement(ps);
            releaseConn(conn);
        }
        return resultList;
    }

    /**
     * 查询用户饼图
     * @return
     */
    public Map<String,Object> findUserPieList(QueryCriteria criteria){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String,Object> resultMap = new HashMap<String, Object>();
        List<Map> resultList = Lists.newArrayList();
        int count = 0;
        String sqlView1 = "";
        /**
         * 从连接池中取出可用的连接
         */
        conn = getConn();
        try {

            List<String> sourceTableNameUuSessList = (ArrayList)criteria.get("sourceTableNameUuSessList");
            List<String> haveTableNameUuSessList = Lists.newArrayList();

            /**
             * 判断数据库是否存在表
             */
            for(String tableName : sourceTableNameUuSessList){
                String sql = "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='lte_meter' and `TABLE_NAME`='"+tableName+"' ";
                logger.info("Execute SQL:{}",sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                boolean haveTable = false;
                if (rs != null) {
                    while (rs.next()) {
                        haveTable = true;
                    }
                }
                if(haveTable){
                    haveTableNameUuSessList.add(tableName);
                }
            }

            /**
             * 创建视图
             */

            for(String tableName : haveTableNameUuSessList){
                sqlView1 = "create view "+criteria.get("vwAnalyse_UuSession")+" as";
                sqlView1 += " select * from "+tableName+" union all";
            }
            if(!"".endsWith(sqlView1)){
                sqlView1 = sqlView1.substring(0,sqlView1.length()-9);
                logger.info("Execute SQL:{}",sqlView1);
                ps = conn.prepareStatement(sqlView1);
                ps.execute();
            }

            /**
             * 业务查询
             */
            if(!"".equals(sqlView1)){
                String sqlLast = (String)criteria.get("sqlField");
                logger.info("Execute SQL:{}",sqlLast);
                ps = conn.prepareStatement(sqlLast);
                rs = ps.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        Map mp = new HashMap();
                        mp.put("pieceName1",rs.getObject("pieceName1"));
                        mp.put("pieceValue1",rs.getDouble("pieceValue1"));
                        mp.put("pieceName2",rs.getObject("pieceName2"));
                        mp.put("pieceValue2",rs.getDouble("pieceValue2"));
                        mp.put("pieceName3",rs.getObject("pieceName3"));
                        mp.put("pieceValue3",rs.getDouble("pieceValue3"));
                        mp.put("pieceName4",rs.getObject("pieceName4"));
                        mp.put("pieceValue4",rs.getDouble("pieceValue4"));
                        resultList.add(mp);
                    }
                }
            }
            resultMap.put("resultList",resultList);
            resultMap.put("count",count);
        } catch (SQLException e) {
            logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                    { "查询异常:",e.getMessage(), e.getCause(), e.getClass() });

        } catch (Exception e) {
            logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                    { "查询异常:",e.getMessage(), e.getCause(), e.getClass() });
        } finally{
            try{
                /**
                 * 删除视图
                 */
                if(!"".equals(sqlView1)){
                    sqlView1 = "drop view " + criteria.get("vwAnalyse_UuSession");
                    ps = conn.prepareStatement(sqlView1);
                    ps.execute();
                    logger.info("Execute SQL:{}",sqlView1);
                }

            }catch (Exception e){
                logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",new Object[]
                        { "删除视图异常:",e.getMessage(), e.getCause(), e.getClass() });
                e.printStackTrace();;
            }
            /**
             * 将用完的连接放回连接池！！！注意，不然连接池的连接都会用完
             */
            super.closePreparedStatement(ps);
            releaseConn(conn);
        }
        return resultMap;
    }
}
