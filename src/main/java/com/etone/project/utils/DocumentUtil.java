package com.etone.project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class DocumentUtil {

    public static Document getDocument(InputStream inputStream) {
        SAXBuilder sb = new SAXBuilder();
        Document doc = null; //构造文档对象
        
        try {
            doc = sb.build(inputStream);
        } catch (Exception ex) {
            Logger.getLogger(DocumentUtil.class.getName()).log(Level.SEVERE, "exception 打不开XML，或许格式有误", ex);
        } catch( Throwable t) {
            Logger.getLogger(DocumentUtil.class.getName()).log(Level.SEVERE, "打不开XML，或许格式有误", t);
        }

        return doc;
    }
    
    public static Document getDocument(String filePath) {
        
        SAXBuilder sb = new SAXBuilder();

        Document doc = null; 
        try {
            doc = sb.build(DocumentUtil.class.getClassLoader().getResourceAsStream(filePath)); //构造文档对象
        } catch (JDOMException ex) {
            Logger.getLogger(DocumentUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return doc;
    }
    
    public static List<Element> getChildren(Element currentElement, String elementName) {
        List<Element> childrenElementList = new ArrayList();
        List<Element> children = currentElement.getChildren(elementName);
        
        if(children.isEmpty()) {
            List<Element> allChilren = currentElement.getChildren();
            
            if(allChilren.isEmpty()) {
                return new ArrayList();
            } else {
                for(Element element : allChilren) {
                    List<Element> children1 = getChildren(element, elementName);
                    if(!children1.isEmpty()) {
                        return children1;
                    }
                }
            }
        } else {
            return children;
        }
        
        return childrenElementList;
    }
}
