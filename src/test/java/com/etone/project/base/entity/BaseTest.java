/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity;

import com.etone.commons.json.JsonUtil;
import com.etone.project.base.entity.share.*;
import com.etone.project.base.support.BaseManager;
import com.etone.project.base.support.result.Results;
import com.etone.project.base.type.LogLevel;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 测试
 * 
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14125 $$

public class BaseTest {
	// members

	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

	// static block

	// constructors
	// properties
	// public methods
	@Test
	public void strongConvert() {
		OperateLog operateLog = new OperateLog();
		operateLog.account = "admin";
		operateLog.logLevel = LogLevel.DEBUG;
		operateLog.logLevelName = LogLevel.DEBUG;
		SystemLog systemLog = JsonUtil.update(operateLog, new SystemLog());
		logger.info("{}, {}", JsonUtil.toJson(systemLog), 1);

		Privilege privilege = new Privilege();
		privilege.name = "test";
		User user = new User();
		user.account = "admin";
		Role role = new Role();
		role.name = "admin";

		role.grantUser(Lists.newArrayList(user));
		// privilege.grantRole(Lists.newArrayList(role));
		role.grantPrivilege(Lists.newArrayList(privilege));
		user.grantRole(Lists.newArrayList(role));
		logger.info(JsonUtil.toJson(user));
	}

	@Test
	public void otherTest() {
		// JPA查询测试
		List<Privilege> privileges = Lists.newArrayList();
		Privilege privilege = new Privilege();
		privilege.code = "001";
		privileges.add(privilege);
		PageImpl<Privilege> page = new PageImpl<Privilege>(privileges, new PageRequest(1, privileges.size(), new Sort("id")), privileges.size());
		Results<Privilege> actual = Results.getPage(page, Privilege.class);
		String json = JsonUtil.toJson(actual);
		logger.info(json);

		Results<Privilege> reverse = Results.deserialize(json, Privilege.class);
		logger.info("{}", reverse);

		// 动态查询测试
		BaseManager<Privilege, Long> baseManager = new BaseManager<Privilege, Long>() {
			@Override
			public void setRepository() {
			}
		};
		List<HashMap> content = JsonUtil.update(JsonUtil.toJson(privileges), new ArrayList<HashMap>());
		Page<HashMap> actualPage = baseManager.buildPage(content, JsonUtil.toMap(json), HashMap.class);
		logger.info("{}", actualPage);
	}

	@Test
	public void logbackTest() {
		logger.info("a\nb\nc\r\n");
	}
	// protected methods
	// friendly methods
	// private methods
	// inner class
	// test main
} */
