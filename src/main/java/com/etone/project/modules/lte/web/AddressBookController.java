package com.etone.project.modules.lte.web;

import com.etone.project.base.support.BaseController;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.modules.lte.manager.IAddressBookManager;
import com.etone.project.utils.Common;
import com.etone.project.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/addressBook")
public class AddressBookController extends BaseController {

    @Autowired
    private IAddressBookManager addressBookManager;

    @ResponseBody
    @RequestMapping(value = "/queryAddressBooks", method = RequestMethod.GET)
    public List<Map> queryAddressBooks(HttpServletRequest request) {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("id", request.getParameter("id"));
        criteria.put("name", request.getParameter("name"));
        criteria.put("searchContent", request.getParameter("searchContent"));
        return addressBookManager.queryAddressBooks(criteria);
    }

    @ResponseBody
    @RequestMapping(value = "/queryAddressBookNames", method = RequestMethod.GET)
    public void queryAddressBookNames(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("name", request.getParameter("name"));
        List<Map> list = addressBookManager.queryAddressBookNames(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAddressBook", method = RequestMethod.PUT)
    public void deleteAddressBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result(Result.SUCCESS, "删除成功！", "删除成功！");

        try {
            addressBookManager.deleteAddressBook(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.ERROR, "删除失败", e.getMessage());
        } finally {
            finalOperate(result, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAddressBooks", method = RequestMethod.PUT)
    public void deleteAddressBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result(Result.SUCCESS, "删除成功！", "删除成功！");

        try {
            QueryCriteria criteria = new QueryCriteria();
            criteria.put("searchContent", request.getParameter("searchContent"));
            addressBookManager.deleteAddressBooks(criteria);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.ERROR, "删除失败", e.getMessage());
        } finally {
            finalOperate(result, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveAddressBook", method = RequestMethod.POST)
    public void saveAddressBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");

        try {
            QueryCriteria criteria = new QueryCriteria();
            criteria.put("id", request.getParameter("id"));
            criteria.put("name", request.getParameter("name"));
            criteria.put("telephone", request.getParameter("telephone"));
            criteria.put("city", request.getParameter("city"));
            criteria.put("company", request.getParameter("company"));
            criteria.put("job", request.getParameter("job"));
            criteria.put("school", request.getParameter("school"));
            criteria.put("education", request.getParameter("education"));
            criteria.put("remark", request.getParameter("remark"));

            String message = addressBookManager.saveOrUpdateAddressBook(criteria);
            if (Common.judgeString(message)) {
                result = new Result(Result.ERROR, message, "保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(Result.ERROR, e.getMessage(), "保存失败");
        } finally {
            finalOperate(result, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exportAddressBooks", method = RequestMethod.GET)
    public void exportAddressBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentTime = String.valueOf(request.getSession().getAttribute("currentTime"));
        System.out.println("currentTime:" + currentTime + ";System.currentTimeMillis():" + System.currentTimeMillis());
        System.out.println("==" + (System.currentTimeMillis() - Long.parseLong(Common.judgeString(currentTime) ? currentTime : "0")));
        if (Common.judgeString(currentTime) && (System.currentTimeMillis() - Long.parseLong(currentTime)) < 5000) {
            return;
        }
        request.getSession().setAttribute("currentTime", System.currentTimeMillis());
        Result result = new Result(Result.SUCCESS, "发送成功！", "发送成功！");

        String fileName = "通讯录信息";
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("searchContent", request.getParameter("searchContent"));
        try {
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1" ) + "_" + System.currentTimeMillis() + ".xls";
//            fileName = fileName + "_" + System.currentTimeMillis() + ".xls";
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            String path = request.getSession().getServletContext().getRealPath("/file/upload/" + fileName);
            OutputStream os = new FileOutputStream(path);

            addressBookManager.exportAddressBooks(os, criteria);

            boolean isSuccess = addressBookManager.sendMail(request.getParameter("recipient"), path);
            if (!isSuccess) {
                result = new Result(Result.ERROR, "邮件有误", "发送失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = new Result(Result.ERROR, "发送失败", "发送失败");
        } finally {
            finalOperate(result, response);
        }
    }

    private void finalOperate(Result result, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        System.out.println(result.toString());
        writer.println(result.toString());
        writer.flush();
        if (writer != null) {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
