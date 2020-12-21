package com.etone.project.utils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public interface SetValueToEntity {
    public void setboolean(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setBoolean(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setString(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setint(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setInteger(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setlong(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setLong(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setfloat(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setFloat(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setdouble(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setDouble(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setDate(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setTimestamp(Method method, Object cell, Object entity, Map<String, String> regularMap);
    
    public void setBigDecimal(Method method, Object cell, Object entity, Map<String, String> regularMap);
}
