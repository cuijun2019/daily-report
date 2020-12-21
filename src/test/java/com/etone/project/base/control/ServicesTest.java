package com.etone.project.base.control;

import javax.annotation.Resource;
import javax.sql.DataSource;

//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Services基本测试类<br>
 * 其他类继承即可
 * 
 * @author guojian
 * @date 2013-9-7 下午2:27:04
 
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ServicesTest extends AbstractTransactionalJUnit4SpringContextTests {

}
*/