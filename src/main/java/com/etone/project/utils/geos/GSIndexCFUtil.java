package com.etone.project.utils.geos;

import org.dom4j.Node;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 讀取指標配置文件
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-4-21
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class GSIndexCFUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GeoServerPropertyUtil.class);

    /**
     * 根據  templatefile/Kpi_config.xml 文件配置 返回各項指標的顏色集合
     * @return
     */
    public static List<Map> getKpiConfig(String filePath) {
        XMLReader xmlReader = new XMLReader(filePath);
        String s = xmlReader.getRootName();
        List<Node> nodeList = xmlReader.getNodes("//kpi");

        List<Map> list = new ArrayList<Map>(); //存放所有指標
        for (Node node : nodeList) {
            Map<String,Object> map = new HashMap<String,Object>();  //存放

            String kpiName = xmlReader.getAttributeValue(node, "name").trim();
            List<Node> nlist =  node.selectNodes("interval");

            List<Map> l = new ArrayList<Map>();
            for(Node n : nlist){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("color",xmlReader.getAttributeValue(n, "color").trim());
                  StringBuffer sb = new StringBuffer();
                  sb.append(xmlReader.getAttributeValue(n, "min").trim());
                  sb.append("<=X<");
                  sb.append(xmlReader.getAttributeValue(n, "max").trim());
                m.put("p_min_max",sb.toString());
                l.add(m);
            }
            map.put(kpiName,l);
            list.add(map);
        }
        return list;
    }
}
