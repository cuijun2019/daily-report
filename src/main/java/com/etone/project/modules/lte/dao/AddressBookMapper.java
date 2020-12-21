package com.etone.project.modules.lte.dao;

import com.etone.project.core.model.QueryCriteria;

import java.util.List;
import java.util.Map;

public interface AddressBookMapper {

    void saveAddressBook(QueryCriteria criteria);

    void updateAddressBook(QueryCriteria criteria);

    void deleteAddressBook(int id);

    void deleteAddressBooks(QueryCriteria criteria);

    List<Map> queryAddressBooks(QueryCriteria criteria);

    int countAddressBook(QueryCriteria criteria);

    List<Map> queryAddressBookNames(QueryCriteria criteria);
}