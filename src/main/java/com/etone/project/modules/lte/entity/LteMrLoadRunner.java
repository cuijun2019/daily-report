package com.etone.project.modules.lte.entity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteMrAdvanceMapper;
import com.etone.project.modules.lte.manager.ILteMrAdvanceManager;


public class LteMrLoadRunner implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(LteMrLoadRunner.class);
	private ILteMrAdvanceManager lteMrAdvanceManager;
	private List<QueryCriteria> dateQueryList;
	
	public LteMrLoadRunner(ILteMrAdvanceManager lteMrAdvanceManager,List<QueryCriteria> dateQueryList) {
        this.init();
        this.lteMrAdvanceManager = lteMrAdvanceManager;
        this.dateQueryList = dateQueryList;
    }
	/**
     * 初始化
     */
    private void init() {
    }
	
    @Override
	public void run() {
		if (lteMrAdvanceManager != null) {
            try {
            	loadMrTable();//实现算法并入库
            } catch (Exception e) {
                logger.error("Mr预统计出错!", e);
            }
        }
	}
    
    public void loadMrTable(){
    	for(QueryCriteria qc : dateQueryList){
    		try {
				lteMrAdvanceManager.advanceCalcul(qc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
