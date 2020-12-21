package com.etone.project.modules.lte.manager;

import javax.servlet.http.HttpServletRequest;

/**
 * Mr预统计模块
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2014-4-10 上午9:57:59
 */
public interface ILteSysLogManager {
	
	/**
	 * 日志插入
	 * @param criteria
	 * @return
	 */
	public void insertSysLog(String sysType,HttpServletRequest request);
	
}