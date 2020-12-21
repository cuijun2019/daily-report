package com.etone.project.modules.lte.manager.impl;

import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.dao.AddressBookMapper;
import com.etone.project.modules.lte.manager.IAddressBookManager;
import com.etone.project.utils.Common;
import com.etone.project.utils.mail.MailSenderFactory;
import com.etone.project.utils.mail.MailSenderType;
import com.etone.project.utils.mail.SimpleMailSender;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AddressBookManager implements IAddressBookManager {

    private static final Logger logger = LoggerFactory.getLogger(AddressBookManager.class);

    @Autowired
    private AddressBookMapper addressBookMapper;


    @Override
    public String saveOrUpdateAddressBook(QueryCriteria criteria) {
        String id = String.valueOf(criteria.get("id"));

        if (this.existAddressBook(criteria)) {
            return "该姓名和联系方式已经存在";
        }
        if (Common.judgeString(id)) {
            addressBookMapper.updateAddressBook(criteria);
        } else {
            addressBookMapper.saveAddressBook(criteria);
        }
        return "";
    }

    @Override
    public void deleteAddressBook(int id) {
        addressBookMapper.deleteAddressBook(id);
    }

    @Override
    public void deleteAddressBooks(QueryCriteria criteria) {
        addressBookMapper.deleteAddressBooks(criteria);
    }

    @Override
    public List<Map> queryAddressBooks(QueryCriteria criteria) {
        return addressBookMapper.queryAddressBooks(criteria);
    }

    @Override
    public List<Map> queryAddressBookNames(QueryCriteria criteria) {
        return addressBookMapper.queryAddressBookNames(criteria);
    }

    @Override
    public void exportAddressBooks(OutputStream os, QueryCriteria criteria) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("通讯录信息表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 50 * 256);
        sheet.setColumnWidth(5, 13 * 256);
        sheet.setColumnWidth(6, 30 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 50 * 256);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("联系方式");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("现居城市");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("工作单位");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("职务/职称");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("毕业高校");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("最高学历");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("备注");
        cell.setCellStyle(style);

        List<Map> mapList = queryAddressBooks(criteria);
        for(int i = 0; i < mapList.size(); i++) {
            row = sheet.createRow(i + 1);
            // 第四步，创建单元格，并设置值
            Map map = mapList.get(i);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(String.valueOf(map.get("name")));
            row.createCell(2).setCellValue(String.valueOf(map.get("telephone")));
            row.createCell(3).setCellValue(String.valueOf(map.get("city")));
            row.createCell(4).setCellValue(String.valueOf(map.get("company")));
            row.createCell(5).setCellValue(String.valueOf(map.get("job")));
            row.createCell(6).setCellValue(String.valueOf(map.get("school")));
            row.createCell(7).setCellValue(String.valueOf(map.get("education")));
            row.createCell(8).setCellValue(String.valueOf(map.get("remark")));
        }
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendMail(String recipient, String path) {
        boolean flag = true;
        // 发送邮件
        SimpleMailSender sms = MailSenderFactory.getSender(MailSenderType.SERVICE);
        try {
            sms.send(recipient, "通讯录列表", "通讯录列表见附件", path);
        } catch (AddressException e) {
            e.printStackTrace();
            flag = false;
        } catch (MessagingException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    private boolean existAddressBook(QueryCriteria criteria) {
        return addressBookMapper.countAddressBook(criteria) > 0 ? true : false;
    }
}
