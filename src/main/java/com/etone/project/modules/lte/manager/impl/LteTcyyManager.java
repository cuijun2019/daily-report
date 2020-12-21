package com.etone.project.modules.lte.manager.impl;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.LteTcyyMapper;
import com.etone.project.modules.lte.manager.ILteTcyyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public final class LteTcyyManager implements ILteTcyyManager {

	private static final Logger logger = LoggerFactory.getLogger(LteTcyyManager.class);

	@Autowired
	private LteTcyyMapper lteTcyyMapper;

    @Override
    public List<Map> queryContractDetails(QueryCriteria criteria) {
        return lteTcyyMapper.queryContractDetails(criteria);
    }

    @Override
    public List<Map> queryProjectLineDetails(QueryCriteria criteria) {
        return lteTcyyMapper.queryProjectLineDetails(criteria);
    }

    public List<Map> queryInvoiceByLine() {
        return lteTcyyMapper.queryInvoiceByLine();
    }

    public List<Map> queryInvoiceByDepart() {
        return lteTcyyMapper.queryInvoiceByDepart();
    }

    public List<Map> queryInvoiceByProject(QueryCriteria criteria) {
        return lteTcyyMapper.queryInvoiceByProject(criteria);
    }

    public List<Map> queryTradeOperation(QueryCriteria criteria) {
        return lteTcyyMapper.queryTradeOperation(criteria);
    }

    public List<String> queryProjectCodeAndName() {
        return lteTcyyMapper.queryProjectCodeAndName();
    }
}
