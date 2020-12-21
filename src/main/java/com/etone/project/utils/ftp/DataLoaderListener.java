package com.etone.project.utils.ftp;

import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;

import com.etone.project.core.web.SpringContextHolder;
import com.etone.project.utils.geos.GeoServerPropertyUtil;

public class DataLoaderListener extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;

    //初始化对象
	public DataLoaderListener(){
		applicationContext = SpringContextHolder.getApplicationContext();
		init();
	}
	
	public void init(){
		String status = GeoServerPropertyUtil.getFtpStartStatus();
		if("yes".equals(status)){
			DataLoaderTread dataLoaderTread = new DataLoaderTread(applicationContext);
			new Thread(dataLoaderTread).start();
		}
	}

}
