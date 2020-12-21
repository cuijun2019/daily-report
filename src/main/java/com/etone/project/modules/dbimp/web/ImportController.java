package com.etone.project.modules.dbimp.web;

import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.BaseConstants;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.web.*;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.dbimp.dao.ILoadTableDao;
import com.etone.project.modules.dbimp.dao.ImportLogDao;
import com.etone.project.modules.dbimp.entity.Mre;
import com.etone.project.modules.dbimp.manager.IImportDataManager;
import com.etone.project.utils.DataTypeUtil;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.WebUtils;

/**
 * 模块控制器(全Ajax风格示例)
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14169 $$
 */
@Controller
@RequestMapping("/modules/module1/import")
@Auditmeta(code = "003", name = "导入数据", symbol = "importdata")
public class ImportController extends GenericController  {
    // members

    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);
    private static final String VIEW_PREFIX = "modules/datamanager/fileupload/";
    private static final String VIEW_INDEX = VIEW_PREFIX + BaseConstants.VIEW_INDEX;
    private static final String VIEW_EDIT = VIEW_PREFIX + BaseConstants.VIEW_EDIT;
    
    @Autowired
    private IImportDataManager importDataManager;
    
    @Autowired
    private ILoadTableDao loadTableDao;
    @Autowired
    private ImportLogDao importLogDao;

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    //@RequiresPermissions("privilege:list")
    @Auditmeta(code = "01", name = "查询", symbol = "list", message = "{0}进行了分页查询", url = VIEW_PREFIX + "/import")
    @RequestMapping(value = "/importFile", method = {RequestMethod.GET, RequestMethod.POST})
    public Object importFile(HttpServletRequest request) {

        if (request instanceof ShiroHttpServletRequest) {
            return null;
        }
        
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        criteria.getCondition().putAll(query);
        

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得文件：   
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        Set<String> fileNameSet = fileMap.keySet();
        
        List fileResults = Lists.newArrayList();
        for (String fileName : fileNameSet) {

            CommonsMultipartFile file = (CommonsMultipartFile) fileMap.get(fileName);
            String name = file.getOriginalFilename();


            if (file == null) {
                logger.info("file not found!");
                return null;
            }
            InputStream inputStream = null;
            
            try {
                //获取文件流
                inputStream = file.getInputStream();
                logger.info("inport file:" + fileName);
                User user = this.getUser();
                Map<String, String> importDateResult = this.importDataManager.importData(inputStream, 
                        request.getParameter("type"), name, user, 
                        (query.get("isDelete") == null? null : query.get("isDelete").toString())
                        );

                fileResults.add(importDateResult);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                //关闭文件流
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage(), e);
                }
            }

        }

        
        Map<String, List> result = new HashMap();//返回json
        result.put("files", fileResults);
        return result;
    }

    @ResponseBody
    @Auditmeta(code = "01", name = "下载模板", symbol = "list", message = "{0}", url = VIEW_PREFIX + "/download")
    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST})
    public void download(HttpServletRequest request, HttpServletResponse response) {
    	
        response.setCharacterEncoding("gb2312");
        response.setContentType("multipart/form-data");
        //String typeName = UTF2GBK.utf82gbk(request.getParameter("type"));
        String typeName = request.getParameter("type");
        String name = request.getParameter("name");
        
        if (typeName == null) {
            return;
        }
        
        logger.info("typeName:" + typeName);
        Map<String, String> entity = DataTypeUtil.getEntityByValue(typeName);
        String fileName = entity.get("name");
        
        String gbk;
        String gb2312;
        String fileNameToIso88591;
        try {
            
            fileNameToIso88591 = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException ex) {
            fileNameToIso88591 = null;
            java.util.logging.Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setHeader("Content-Disposition", "attachment;fileName='" + fileNameToIso88591 + "." + entity.get("filetype") + "'");
        try {
            InputStream inputStream = ImportController.class.getClassLoader().getResourceAsStream(entity.get("templatefile"));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            inputStream.close();
            os.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } 
    }
    
    @ResponseBody
    @RequestMapping(value = "/queryImportLog", method = {RequestMethod.GET, RequestMethod.POST})
    public Object queryImportLog(HttpServletRequest request) {
    	
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        //根据ID反序
        //        criteria.setOrderByClause("id");
        //        criteria.setOrderType("DESC");
        User user = this.getUser();
        PageResult<Map> page = this.importDataManager.queryImportLog(criteria, user.id);
        
        if(page == null || page.getResult().isEmpty()) {
            return null;
        }
        
        return Results.getPage(page, page.getResult().get(0).keySet());
    }
    
    @ResponseBody
    @RequestMapping(value = "/queryTrafficImportLog", method = {RequestMethod.GET, RequestMethod.POST})
    public Object queryTrafficImportLog(HttpServletRequest request) {
    	
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        
        //根据ID反序
//        criteria.setOrderByClause("id");
//        criteria.setOrderType("DESC");
        
        PageResult<Map> page = this.importDataManager.queryTrafficImportLog(criteria);
        
        if(page == null || page.getResult().isEmpty()) {
            return null;
        }
        
        return Results.getPage(page, page.getResult().get(0).keySet());
    }
    
    @ResponseBody
    @RequestMapping(value = "/query/{entityName}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object query(@PathVariable String entityName,HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        
        String packagePath = Mre.class.getPackage().getName();
        Class<?> entityClass;
        try {
            entityClass = Class.forName(packagePath + "." + entityName);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
        
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        PageResult<Map> page = this.importDataManager.query(criteria, entityName);
        return Results.getPage(page, entityClass);
    }
    
    @ResponseBody
    @RequestMapping(value = "/clear/{entityName}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object clear(@PathVariable String entityName,HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");

        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        Object result = importDataManager.clear(criteria, entityName);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteImportLog/{entityName}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object deleteImportLog(@PathVariable String entityName,HttpServletRequest request) {
    	//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> query = WebUtils.getParametersStartingWith(request, "");
        QueryCriteria criteria = new QueryCriteria();
        initParameters(criteria, query);
        
        Object result = importDataManager.deleteImportLog(criteria, entityName);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getColumns/{entityName}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getColumns(@PathVariable String entityName,HttpServletRequest request) {
    	
        String packagePath = Mre.class.getPackage().getName();
        Class<?> entityClass;
        try {
            entityClass = Class.forName(packagePath + "." + entityName);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
        
        PageResult<Map> page = new PageResult();
        return Results.getPage(page, entityClass);
    }
    
    @ResponseBody
    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public Object test(HttpServletRequest request) {
        this.loadTableDao.test();
        return "";
    }
    
    @ResponseBody
    @RequestMapping(value = "/getImportDetail/{importKey}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getImportDetail(@PathVariable Long importKey,HttpServletRequest request) {
        long beginTime = System.currentTimeMillis();
        
        Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
        Object msgIndex = params.get("msgIndex");
        String queryImportDetail = this.importDataManager.queryImportDetail(importKey, msgIndex == null? null : Integer.parseInt(msgIndex.toString()));
        long endTime = System.currentTimeMillis();
        
        logger.info("cost time : " + (endTime - beginTime) + "ms");
        
        return queryImportDetail;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getImportPercent/{importKey}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object getImportPercent(@PathVariable Long importKey,HttpServletRequest request) {
        long beginTime = System.currentTimeMillis();
        
        String percent = this.importDataManager.queryImportPercent(importKey);
        long endTime = System.currentTimeMillis();
        
        logger.info("cost time : " + (endTime - beginTime) + "ms");
        
        return percent;
    }
}
