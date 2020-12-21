package com.etone.project.modules.lte.manager;

import com.etone.project.core.model.QueryCriteria;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IAddressBookManager {

    String saveOrUpdateAddressBook(QueryCriteria criteria);

    void deleteAddressBook(int id);

    void deleteAddressBooks(QueryCriteria criteria);

    List<Map> queryAddressBooks(QueryCriteria criteria);

    List<Map> queryAddressBookNames(QueryCriteria criteria);

    void exportAddressBooks(OutputStream os, QueryCriteria criteria);

    boolean sendMail(String recipient, String path);
}