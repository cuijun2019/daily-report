package com.qq.weixin.mp.aes;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;

public class WeixinUtil {
    private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    public final static String token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    public final static String jscode2session = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&grant_type=authorization_code&js_code=CODE";
    public final static String templateSend = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=TOKEN";
    public final static String customSend = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=TOKEN";

    public static String getToken(String appid, String appsecret) {
        String requestUrl = token.replace("APPID", appid).replace("SECRET", appsecret);
        JSONObject jsonObject = httpGet(requestUrl);
        return jsonObject.getString("access_token");
    }

    public static String getJscode2session(String appid, String appsecret, String code) {
        String requestUrl = jscode2session.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        JSONObject jsonObject = httpGet(requestUrl);
        return jsonObject.getString("openid");
    }

    public static void sendTemplate(String appid, String appsecret, JSONObject jsonParam) {
        String requestUrl = templateSend.replace("TOKEN", getToken(appid, appsecret));
        httpPost(requestUrl, jsonParam);
    }

    public static void sendCustom(String appid, String appsecret, JSONObject jsonParam) {
        String requestUrl = customSend.replace("TOKEN", getToken(appid, appsecret));
        httpPost(requestUrl, jsonParam);
    }

    public static JSONObject httpGet(String url) {
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = HttpClients.createDefault().execute(request);

            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                String strResult = EntityUtils.toString(response.getEntity());
                /** 把json字符串转换成json对象 **/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }

        return jsonResult;
    }

    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        return httpPost(url, jsonParam, false);
    }


    public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
        //post请求返回结果
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = HttpClients.createDefault().execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str;
                try {
                    /** 读取服务器返回过来的json字符串数据 **/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /** 把json字符串转换成json对象 **/
                    jsonResult = JSONObject.fromObject(str);

                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }

        return jsonResult;
    }
}
