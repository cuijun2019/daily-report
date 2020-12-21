package com.etone.project.modules.lte.web;

import Alert.weChat.send_weChatMsg;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteTcyyManager;
import com.etone.project.utils.ResponseUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modules/lteTcyy")
@Auditmeta(code = "003", name = "通产运营", symbol = "")
public final class LteTcyyController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(LteTcyyController.class);

    @Autowired
        private ILteTcyyManager lteTcyyManager;

    @ResponseBody
         @RequestMapping(value = "/queryContractDetails", method = {RequestMethod.GET, RequestMethod.POST})
         public void queryContractDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
//        JSONObject json = new JSONObject();
        List<Map> list = lteTcyyManager.queryContractDetails(criteria);
//        json.put("employee", map.get("employee"));
//        json.put("position", map.get("position"));
//        json.put("provinces", map.get("provinces"));

        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryProjectLineDetails", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProjectLineDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
//        JSONObject json = new JSONObject();
        List<Map> list = lteTcyyManager.queryProjectLineDetails(criteria);
//        json.put("employee", map.get("employee"));
//        json.put("position", map.get("position"));
//        json.put("provinces", map.get("provinces"));

        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryInvoiceByLine", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryContractReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = lteTcyyManager.queryInvoiceByLine();
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryInvoiceByDepart", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryInvoiceByDepart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = lteTcyyManager.queryInvoiceByDepart();
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryInvoiceByProject", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryInvoiceByProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("rowStart", request.getParameter("rowStart"));
        criteria.put("pageSize", request.getParameter("pageSize"));
        List<Map> list = lteTcyyManager.queryInvoiceByProject(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryTradeOperation", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryTradeOperation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("searchContext", request.getParameter("searchContext"));
        List<Map> list = lteTcyyManager.queryTradeOperation(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryProjectCodeAndName", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryProjectCodeAndName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> list = lteTcyyManager.queryProjectCodeAndName();
        ResponseUtils.printArrayList(response, list);
    }
}