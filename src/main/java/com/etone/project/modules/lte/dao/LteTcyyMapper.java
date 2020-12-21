package com.etone.project.modules.lte.dao;

import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

public interface LteTcyyMapper {

    public List<Map> queryContractDetails(QueryCriteria criteria);

    public List<Map> queryProjectLineDetails(QueryCriteria criteria);

    public List<Map> queryInvoiceByLine();

    public List<Map> queryInvoiceByDepart();

    public List<Map> queryInvoiceByProject(QueryCriteria criteria);

    public List<Map> queryTradeOperation(QueryCriteria criteria);

    public List<String> queryProjectCodeAndName();

}