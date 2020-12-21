package com.etone.project.core.model;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * easyUI 异常返回对象
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-9-4
 */
public class ExceptionReturn {

	public ExceptionReturn() {
	}

	/**
	 * 异常时的构造方法
	 * 
	 * @param msg
	 *            异常消息
	 */
	public ExceptionReturn(Throwable exceptionMessage) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exceptionMessage.printStackTrace(pw);
		// 异常情况
		this.success = false;
		//太详细了
		// this.exceptionMessage = sw.toString();
		this.exceptionMessage = exceptionMessage.getMessage();
	}

	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 异常的消息
	 */
	private Object exceptionMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(Object exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
