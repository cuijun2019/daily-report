package com.etone.project.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class DataTypeUtil {
    
    private static Document doc = null;
    
    public static List<String> getAllDataType() {
        
        
        Element root = getDoc().getRootElement();
        List<Element> children = root.getChildren("entity");
        
        ArrayList<String> dataTypeNameList = new ArrayList();
        
        for(Element entityElement : children) {
            Element nameElement = entityElement.getChild("name");
            if(nameElement != null) {
                String name = nameElement.getText();
                dataTypeNameList.add(name);
            }
        }
        
        return dataTypeNameList;
    }
    
    private static Document getDoc() {
        if(doc == null) {
            doc = DocumentUtil.getDocument("ehcache/dataType.xml");
        }
        
        return doc;
    }
    
    /**
     * 根据名字获取entity
     * @param entityName
     * @return 
     */
    public static Map<String, String> getEntityByName(String entityName) {
        Element root = getDoc().getRootElement();
        List<Element> entityElementList = root.getChildren();
        
        
        
        for(Element entityElement : entityElementList) {
            Element nameElement = entityElement.getChild("name");
            if(nameElement != null) {
                String name = nameElement.getText();
                if(name.equals(entityName)) {
                    Map<String, String> entityMap = new HashMap();
                    List<Element> children = entityElement.getChildren();
                    for(Element element : children) {
                        entityMap.put(element.getName(), element.getText());
                    }
                    
                    return entityMap;
                }
            }
        }
        
        return null;
        
    }
    
    /**
     * 根据实体类名获取相应配置文件里的 name配置
     * @param entityName
     * @return 
     */
    public static String getNameByEntityName(String entityName) {
        Element root = getDoc().getRootElement();
        List<Element> entityElementList = root.getChildren();
        
        
        
        for(Element entityElement : entityElementList) {
            Element entityNameElement = entityElement.getChild("entityName");
            if(entityNameElement != null) {
                String text = entityNameElement.getText();
                
                if(text.equals(entityName)) {
                    String name = entityElement.getChild("name").getText();
                    
                    return name;
                }
            }
        }
        
        return null;
        
    }
    
    
    /**
     * 根据值获取entity
     * @param value
     * @return 
     */
    public static Map<String, String> getEntityByValue(String value) {
        Element root = getDoc().getRootElement();
        List<Element> entityElementList = root.getChildren();
        
        
        
        for(Element entityElement : entityElementList) {
            Element nameElement = entityElement.getChild("value");
            if(nameElement != null) {
                String text = nameElement.getText();
                if(text.equals(value)) {
                    Map<String, String> entityMap = new HashMap();
                    List<Element> children = entityElement.getChildren();
                    for(Element element : children) {
                        entityMap.put(element.getName(), element.getText());
                    }
                    
                    return entityMap;
                }
            }
        }
        
        return null;
        
    }
    
    public static void main(String[] args) {
        List<String> allDataType = DataTypeUtil.getAllDataType();
        
        for(String name : allDataType) {
            System.out.print(name + "\t");
        }
        Map<String, String> entityByName = DataTypeUtil.getEntityByValue("3");
        if(entityByName != null) {
            System.out.println(entityByName.toString());
        }
        
    }
}
