package com.etone.project.base.control;


//import static org.junit.Assert.*;

import java.util.Map;

import javax.servlet.http.HttpSession;

//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;

import com.etone.project.core.model.PageResult;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.modules.lte.manager.ILteMrAdvanceManager;
import com.etone.project.modules.lte.web.LteMrAdvanceController;


/**
 *
 *
 * @author guojian
 * @date 2013-9-7 下午2:27:04
 */

/*
//告诉framework怎么运行这个类
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//bean的配置文件路径，这个是Test类的classpath路径，如果是Spring推荐的目录结构，应该在：项目目录/src/test/resources/里  
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath:applicationContext-shiro.xml","classpath:captcha-context.xml" })
public class BaseModulesServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private ILteMrAdvanceManager lteMrAdvanceManager;
	

    @Autowired  
    private LteMrAdvanceController controller;  
  
    //这种方法适用于Springframework3.0，3.1换了handler的处理类。  
    @Autowired  
    private AnnotationMethodHandlerAdapter handlerAdapter;  
    private final MockHttpServletRequest request = new MockHttpServletRequest();  
    private final MockHttpServletResponse response = new MockHttpServletResponse();  
  
    //@Test  
    public void testMain4User() throws Exception {  
        request.setRequestURI("/main");  
        request.setMethod(HttpMethod.POST.name());  
        HttpSession session = request.getSession();  
        //设置 认证信息  
//        session.setAttribute(CommonConstants.SESSION_USER_TYPE, 1);  
//        session.setAttribute(CommonConstants.SESSION_USER_ID, 0);  
//        session.setAttribute(CommonConstants.SESSION_USER_ACC, "aa1");  
//  
//        ModelAndView mav = handlerAdapter.handle(request, response, controller);  
//        assertEquals("/regist", mav.getViewName());  
    }  
    
	@Test
	public void selectAllModules() {
		QueryCriteria criteria = new QueryCriteria();
		criteria.put("type", "mrskpi");
		PageResult<Map> pr =  lteMrAdvanceManager.findAdvance(criteria);
		System.out.println(pr);
	}
	
	
	
}
*/
