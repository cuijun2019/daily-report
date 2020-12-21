package com.etone.project.utils.ziputil;

import com.etone.commons.json.JsonUtil;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.etone.*;
//import com.etone.project.modules.lte.manager.impl.LteGisManager;
//import com.etone.project.modules.util.support.Env;
import org.springframework.http.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-4-15
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public  class CityInfoJsonRead {

   public static void main(String[] args) {
//       CityInfoJsonRead c=new CityInfoJsonRead();
     /*  String path=Env.getRoot();
       System.out.println("path:"+path);*/
//       List list=getCityInfo("ltemr/static/json/cityInfo.json");
    }
    /**
     * 获取地市信息
     * @param
     * @return
     */
    public  static List<Map>  getCityInfo(String path){
       /* String root=Env.getRoot();
        String realPath= root+"src\\main\\webapp\\static\\json\\cityInfo.json";
        String jsonData=ReadFile(realPath);

        Object obj=JsonUtil.fromJson(jsonData, Object.class);
        Map  map=JsonUtil.toMap(obj);
        List<Map> list_city = (ArrayList)map.get("city");
        System.out.println(list_city.size());*/
//        return list_city;
        return null;
    }
    /**
     * 读取json文件
     * @param path
     * @return
     */
    private static String ReadFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            //输入流
            InputStream input=new FileInputStream(file);
            //读取管道
            reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
            String tempString = null;
            //读取输入流的每行数据
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                }
            }
        }
        return laststr;
    }
}
