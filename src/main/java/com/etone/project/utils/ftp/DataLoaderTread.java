package com.etone.project.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etone.project.utils.Global;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.etone.project.base.entity.share.Dictionary;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.manager.DictionaryManager;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.dbimp.manager.IImportDataManager;
import com.etone.project.modules.lte.manager.ILteMrAdvanceManager;
import com.etone.project.utils.ImportDetail;
import com.etone.project.utils.SearchFile;
import com.google.common.collect.Lists;

public class DataLoaderTread  implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(DataLoaderTread.class);
	private ApplicationContext applicationContext;
	private IImportDataManager importDataManager;
	private ILteMrAdvanceManager lteMrAdvanceManager;
	private DictionaryManager dictionaryManager;
	private User user;
	private List<String> fileList = Lists.newArrayList();
	
	private String username = "root";
	private String password = "1qaz2wsx";
	private String ip = "192.168.8.68";
	private String filedir = "/opt/ltenoa/mrdata";// FTP文件路径
	
	public DataLoaderTread(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
		this.importDataManager = applicationContext.getBean(IImportDataManager.class);
		this.lteMrAdvanceManager = applicationContext.getBean(ILteMrAdvanceManager.class);
		this.dictionaryManager = applicationContext.getBean(DictionaryManager.class);
	}
	
	/**
     * 取得当前用户信息
     *
     * @return
     */
    public User getUser() {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUser();
    }
    
	@Override
	public void run() {
		FTPHandle ftp = new FTPHandle();
		QueryCriteria ftpCriteria = new QueryCriteria();
		ftpCriteria.put("intStatus", 1);
		while(true){
			//获取ftp配置
			PageResult<Map> ftpConfigList = lteMrAdvanceManager.queryFtpConfig(ftpCriteria);
			List<Map> ftpCList = ftpConfigList.getResult();
			if(ftpCList.size()>0){
				Map ftpMap = ftpCList.get(0);
				this.username = (String)ftpMap.get("vcModuleCode");
				this.password = (String)ftpMap.get("vcModule");
				this.ip = (String)ftpMap.get("vcLogLevelName");
				this.filedir = (String)ftpMap.get("vcContent");
			}
			
			if("127.0.0.1".equals(ip)){
				localLoader();
			}else{
				//ftpLoader(ftp);
			}
			
			//间隔10秒扫描一次文件
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    /*
	public void ftpLoader(FTPHandle ftp){
		//通过ftp获取文件目录
		// 连接ftp服务器
		try {
			logger.info("开始连接FTP...IP:{} 用户:{} 文件路径:{} 已处理文件:{}",new Object[]{ip,username,filedir,fileList});
			ftp.connectServer(ip,username,password,filedir);
			//获取目录下面的文件列表
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String todayFileName = dateFormat.format(new Date());
			List<String> ftpFileList = ftp.getFileList(todayFileName);
			logger.info("获取文件列表:{}",ftpFileList);
			User user = new User();
			 user.setAccount("admin");
			 user.setId(1l);
			 //初始化阀指
			 List<Dictionary> dictionaryList = dictionaryManager.findAll();
			Map<String, Object> dictionaryMap = new HashMap<String, Object>();
			for (Dictionary dct : dictionaryList) {
                if("1".equals(dct.createUser)){
                    dictionaryMap.put(dct.getVcKey(), dct.getValue());
                }

			}
			for(String ftpFileName : ftpFileList){
				boolean next = true;
				if(fileList.contains(ftpFileName)){
					next = false;
				}
				if(next){
					//String sysPath = System.getProperty("user.dir"); 
					URL url = DataLoaderTread.class.getClassLoader().getResource("templatefile");
					String localFileName = todayFileName+"-mr.zip";
					String dataPath = url.getPath()+"\\"+localFileName;
					//将文件下载到系统中
					long downResult = 0;
					try {
						downResult = ftp.download(ftpFileName,dataPath);
					} catch (Exception e) {
						logger.info("下载Mr文件到本地出现错误.");
						logger.error("Exception: ", e);
					}
					
					if(downResult != 0){
						//将本地文件导入到数据库

						InputStream inputStream = new FileInputStream(new File(dataPath));
						Map<String, String> importMap = importDataManager.importData(inputStream,null,localFileName, user, null);
						long importThreadKey = Long.parseLong(importMap.get("importKey"));
						while(true){
							List<String> importResultList = ImportDetail.importMsgListMap.get(importThreadKey);
							if(importResultList!=null&&importResultList.size()>0){
								String importResultString = importResultList.get(importResultList.size()-1);
								if(importResultString.contains("Finish")){
									break;
								}
							}
							Thread.sleep(3000);
						}

						//执行预统计程序
						List<QueryCriteria> queryCriteriaList = null;
						try {
							queryCriteriaList = lteMrAdvanceManager.getQueryCriteriaList(user);
						} catch (Exception e) {
							logger.info("获取原始数据统计日期集合出现错误.");
							logger.error("Exception: ", e);
						}
						for(QueryCriteria qc : queryCriteriaList){
				    		try {
				    			Map<String, Object> condition = qc.getCondition();
				    			condition.putAll(dictionaryMap);
								lteMrAdvanceManager.advanceCalcul(qc);
							} catch (Exception e) {
								logger.info("预统计出现错误.");
								logger.error("Exception: ", e);
							}
				    	}
						//执行删除程序
						lteMrAdvanceManager.truncateDate();
					}
					//记录导入完成的文件
					fileList.add(ftpFileName);
				}
			}
		} catch (IOException e) {
			logger.info("连接ftp出现错误.错误信息：{}",e.getMessage());
			//logger.error("Exception: ", e);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
	}
	 */
	public void localLoader(){
		//获取指定目录文件
		 String filtPath = new StringBuffer(Global.getRoot()).append(filedir).toString();
		 //初始化用户
		 User user = new User();
		 user.setAccount("admin");
		 user.setId(1l);
		 //初始化阀指
		 List<Dictionary> dictionaryList = dictionaryManager.findAll();
		Map<String, Object> dictionaryMap = new HashMap<String, Object>();
		for (Dictionary dct : dictionaryList) {
            if("1".equals(dct.createUser)){
                dictionaryMap.put(dct.getVcKey(), dct.getValue());
            }
		}
		 try{
			 List<File> list = (List<File>) new SearchFile(filtPath).files();    
			 for (File file : list){
			    boolean next = true;
				if(fileList.contains(file.getName())){
					next = false;
				}
				if(next){
					//将本地文件导入到数据库
					InputStream inputStream = new FileInputStream(file);
					Map<String, String> importMap = importDataManager.importData(inputStream,null,file.getName(),user, null);
					long importThreadKey = Long.parseLong(importMap.get("importKey"));
					while(true){
						List<String> importResultList = ImportDetail.importMsgListMap.get(importThreadKey);
						if(importResultList!=null&&importResultList.size()>0){
							String importResultString = importResultList.get(importResultList.size()-1);
							if(importResultString.contains("Finish")){
								break;
							}
						}
						Thread.sleep(3000);
					}
					//执行预统计程序
					List<QueryCriteria> queryCriteriaList = null;
					try {
						queryCriteriaList = lteMrAdvanceManager.getQueryCriteriaList(user);
					} catch (Exception e) {
						logger.info("获取原始数据统计日期集合出现错误.");
						logger.error("Exception: ", e);
					}
					for(QueryCriteria qc : queryCriteriaList){
			    		try {
			    			Map<String, Object> condition = qc.getCondition();
			    			condition.putAll(dictionaryMap);
							lteMrAdvanceManager.advanceCalcul(qc);
						} catch (Exception e) {
							logger.info("预统计出现错误.");
							logger.error("Exception: ", e);
						}
			    	}
					//执行删除程序
					lteMrAdvanceManager.truncateDate();
					//记录导入完成的文件
					fileList.add(file.getName());
				}
			 }
		 }catch (Exception e) {
				logger.error("Exception: ", e);
		 }
	}
}
