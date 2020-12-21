package com.etone.project.utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个XML的解析工具，提供了一个些解析XML的方法，依赖于dom4j的开发包
 *
 * @author WuHongyun
 * @date May 31, 2009
 * @since 1.0
 */
public class XMLReadUtils {
    private static Logger logger = LoggerFactory.getLogger(XMLReadUtils.class);

    private SAXReader reader = null;

    private Document doc = null;

    private Element rootElement = null;

    /**
     * 默认构造器
     */
    public XMLReadUtils() {

    }

    /**
     * 使用流方式去读取XML文件，支持读取不同Jar包中的资源文件
     *
     * @param stream
     * @since 1.1 ZengJun
     */
    public XMLReadUtils(InputStream stream) {
        openStream(stream);
    }

    /**
     * 使用路径的方式去读取XML文件
     *
     * @param filePath
     */
    public XMLReadUtils(String filePath) {
        openXML(filePath);
    }

    /**
     * 使用路径打开一个XML的文件
     *
     * @param filePath XML的文件路径
     * @return
     */
    public void openXML(String filePath) {
        /**
         * 判断文件是否存在
         */
        File xmlFile = new File(filePath);
        if (xmlFile.exists()) {
            try {
                reader = getReader();
                doc = reader.read(filePath);
            } catch (DocumentException e) {
                logger.error("openXML Exception: " + e.getMessage(), e);
            } catch (SAXException e) {
                logger.error("openXML Exception: " + e.getMessage(), e);
            }
        } else {
            /**
             * 如果找不到文件，使用resource的方式去加载类路径下面的文件
             */
            InputStream returnValue = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(filePath);
            // 加载文件
            openStream(returnValue);
        }
    }

    /**
     * 使用流方式去读取XML文件
     *
     * @param stream XML文件流
     * @since 1.1 ZengJun
     */
    public void openStream(InputStream stream) {
        try {
            reader = getReader();
            doc = reader.read(stream);
            if (doc != null) {
                rootElement = doc.getRootElement();
            }
        } catch (DocumentException e) {
            // 加载失败，证明文件不存在，或文件格式不对
            logger.error("openXML Exception: " + e.getMessage(), e);
        } catch (SAXException e) {
            logger.error("openXML Exception: " + e.getMessage(), e);
        }
        reader = null;
    }

    /**
     * 获取根节点的名
     *
     * @return
     */
    public String getRootName() {
        return doc.getRootElement().getName();
    }

    /**
     * @param elementName 节点名称
     * @return
     * @since 1.1 ZengJun
     */
    public List<Element> getElements(String elementName) {
        List<Element> elements = new ArrayList<Element>();
        // 使用XPath
        if (elementName.indexOf("/") != -1) {
            List<Node> nodes = doc.selectNodes(elementName);
            if (nodes != null && !nodes.isEmpty()) {
                for (Node node : nodes) {
                    elements.add((Element) node);
                }
            }
        } else {
            // 一层一层查找
            if (rootElement != null) {
                elements = findElements(rootElement.elements(), elementName);
            }
        }
        return elements;
    }

    /**
     * 获取单个节点的值
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public String getSingleNodeValue(String elementPath) {
        return doc.selectSingleNode(elementPath).getText();
    }

    /**
     * 在一个节点下，获取当中单个节点的值
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public String getSingleNodeValue(Node node, String elementPath) {
        return node.selectSingleNode(elementPath).getText();
    }

    /**
     * 获取单个节点的Node对像
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public Node getSingleNode(String elementPath) {
        return doc.selectSingleNode(elementPath);
    }

    /**
     * 在一个节点下，获取当中单个节点的Node对像
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public Node getSingleNode(Node node, String elementPath) {
        return node.selectSingleNode(elementPath);
    }

    /**
     * 获取多个相同节点名的节点的值
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public List<String> getNodesValue(String elementPath) {
        List<String> list = new ArrayList<String>();

        for (Object o : doc.selectNodes(elementPath)) {
            Node node = (Node) o;
            list.add(node.getText());
        }

        return list;
    }

    /**
     * 在一个节点下，获取当中多个相同节点名的节点的值
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public List<String> getNodesValue(Node node, String elementPath) {
        List<String> list = new ArrayList<String>();

        for (Object o : node.selectNodes(elementPath)) {
            Node n = (Node) o;
            list.add(n.getText());
        }

        return list;
    }

    /**
     * 获取多个相同节点名的节点的Node对像
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public List<Node> getNodes(String elementPath) {
        List<Node> list = new ArrayList<Node>();

        for (Object o : doc.selectNodes(elementPath)) {
            Node node = (Node) o;
            list.add(node);
        }

        return list;
    }

    /**
     * 在一个节点下，获取当中多个相同节点名的节点的Node对像
     *
     * @param elementPath 其中elementPath可以是节点名，也可以是基于Xpath语法的路径名
     * @return
     */
    public List<Node> getNodes(Node node, String elementPath) {
        List<Node> list = new ArrayList<Node>();

        for (Object o : node.selectNodes(elementPath)) {
            Node n = (Node) o;
            list.add(n);
        }

        return list;
    }

    /**
     * 获取一个节点下属性的值
     *
     * @param node          节点
     * @param attributeName 节点属性
     * @return
     */
    public String getAttributeValue(Node node, String attributeName) {
        return node.valueOf("@" + attributeName);
    }

    //private

    /**
     * 获取SAXReader
     *
     * @return
     * @since 1.1 ZengJun
     */
    private SAXReader getReader() throws SAXException {
        reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return reader;
    }

    /**
     * 查找所有指出名称节点元素
     *
     * @param elements    查询范围
     * @param elementName 查询节点
     * @return
     */
    private List<Element> findElements(List<Element> elements, String elementName) {
        List<Element> actual = new ArrayList<Element>();
        if (elements != null && elements.isEmpty()) {
            for (Element element : elements) {
                if (element.getName().equals(elementName)) {
                    actual.add(element);
                } else {
                    actual = findElements(element.elements(), elementName);
                }
            }
        }
        return actual;
    }

    // inner class

}
