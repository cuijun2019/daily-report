package com.etone.project.modules.lte.dao;


import org.apache.ibatis.mapping.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.apache.ibatis.session.SqlSessionFactory;

import com.etone.project.core.db.CustomSqlSessionTemplate;


import org.springframework.context.ApplicationContextAware;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-5-24
 * Time: 下午3:17
 *  获得执行的sql的工具类
 */
 @Component
public class IbatisUtil implements ApplicationContextAware  {

    @Autowired
    private static ApplicationContext applicationContext; // Spring应用上下文环境

         /*

          * 实现了ApplicationContextAware 接口，必须实现该方法；

          *通过传递applicationContext参数初始化成员变量applicationContext

          */

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }



    /**
     * 获得实际执行的sql        h
     * @param id xml 中sql的id ，如 <select id="XXX">中的"XXX"
     * @param parameterObject 传给这条sql的参数
     * @return
     */
    public String getIbatisSql(String id, Object parameterObject) {

       // ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
       // ApplicationContext ac = this.getWebApplicationContext();

        CustomSqlSessionTemplate csst =  (CustomSqlSessionTemplate)applicationContext.getBean("sqlSessionTemplate");
        SqlSessionFactory sqlSessionFactory = csst.getSqlSessionFactory();

        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(id);
        BoundSql boundSql = ms.getBoundSql(parameterObject);

      /*  List<ResultMap> ResultMaps=ms.getResultMaps();
        if(ResultMaps!=null&&ResultMaps.size()>0){
            ResultMap ResultMap = ms.getResultMaps().get(0);
            Object o = ResultMap.getType();      // ----------------
        }*/


        String sql = boundSql.getSql();    // ----------------
       // System.out.println("-----------打印sql: "+sql);

   /*     List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Object[] parameterArray = new Object[parameterMappings.size()];
            MetaObject metaObject = parameterObject == null ? null : MetaObject.forObject(parameterObject,null,null);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = MetaObject.forObject(value,null,null).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    parameterArray[i] = value;
                }
            }
          //  parameterArray;
        }*/

        return sql;
    }
}
