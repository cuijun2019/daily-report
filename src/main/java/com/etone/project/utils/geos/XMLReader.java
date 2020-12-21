package com.etone.project.utils.geos;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个XML的解析工具，提供了一个些解析XML的方法，依赖于dom4j的开发包
 *
 * @author guojian
 * @version $$Revision: 1.1 $$
 * @date 2014-04-03
 */
public class XMLReader {
    private static Logger logger = LoggerFactory.getLogger(XMLReader.class);

    private SAXReader reader = null;

    private Document doc = null;

    /**
     * 使用路径的方式去读取XML文件
     *
     * @param filePath
     */
    public XMLReader(String filePath) {
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
            reader = new SAXReader();

            try {
                doc = reader.read(filePath);
            } catch (DocumentException e) {
                logger.warn("openXML " + filePath + " Exception: " + e.getMessage(), e);
            }
        } else {
            /**
             * 如果找不到文件，使用resource的方式去加载类路径下面的文件
             */
            InputStream returnValue = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

            reader = new SAXReader();

            try {
                doc = reader.read(returnValue);
            } catch (DocumentException e) {
                /**
                 * 加载失败，证明文件不存在，或文件格式不对
                 */
                logger.warn("openXML " + filePath + " Exception: " + e.getMessage(), e);
            }
        }
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
     * @param node
     * @param attributeName
     * @return
     */
    public String getAttributeValue(Node node, String attributeName) {
        return node.valueOf("@" + attributeName);
    }
}
