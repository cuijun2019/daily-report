package Alert.weChat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class send_weChatMsg {
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;//用于提交登陆数据
    private HttpGet httpGet;//用于获得登录后的页面
    public static final String CONTENT_TYPE = "Content-Type";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//


    private static Gson gson = new Gson();


    /**
     * 微信授权请求，GET类型，获取授权响应，用于其他方法截取token
     * @param Get_Token_Url
     * @return String 授权响应内容
     * @throws IOException
     */
    public String toAuth(String Get_Token_Url) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Token_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        return resp;
    }

    public  String toUserAuth(String Get_User_Url) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_User_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        System.out.println("Get_User_Url:" + Get_User_Url);
        return resp;
    }

    protected String toJssdkTicketAuth(String Get_Jssdk_Ticket) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Jssdk_Ticket);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        return resp;
    }

    public String toTempVoiceAuth(String Get_Temp_Voice_Url) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Temp_Voice_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        return resp;
    }

    /**
     * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
     //* @param corpid应用组织编号   corpsecret应用秘钥
     */
    public String getToken(String corpid,String corpsecret) throws IOException {
        send_weChatMsg sw = new send_weChatMsg();
        urlData uData = new urlData();
        uData.setGet_Token_Url(corpid,corpsecret);
        String resp = sw.toAuth(uData.getGet_Token_Url());

        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        return map.get("access_token").toString();
    }

    public String getUserId(String accesstoken, String code) throws IOException {
        send_weChatMsg sw = new send_weChatMsg();
        urlData uData = new urlData();
        uData.setGet_User_Url(accesstoken,code);
        String resp = sw.toUserAuth(uData.getGet_User_Url());

        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        return "ok".equals(map.get("errmsg").toString()) ? map.get("UserId").toString() : "";
    }

    public String getJssdkTicket(String accesstoken) throws IOException {
        send_weChatMsg sw = new send_weChatMsg();
        urlData uData = new urlData();
        uData.setGet_Jssdk_Ticket(accesstoken);
        String resp = sw.toJssdkTicketAuth(uData.getGet_Jssdk_Ticket());

        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        return "ok".equals(map.get("errmsg").toString()) ? map.get("ticket").toString() : "";
    }

//    public static void main(String[] args) {
//        String resp = "{\"errcode\":0,\"errmsg\":\"ok\",\"ticket\":\"sM4AOVdWfPE4DxkXGEs8VOPVDHcfFcSHCsYIBD5vfCEtK3z7tUS4fqsa88ChsfWZPSuwwN8QxqQEu0GbHbkopw\",\"expires_in\":7200}";
//        Map<String, Object> map = gson.fromJson(resp,
//                new TypeToken<Map<String, Object>>() {
//                }.getType());
//        System.out.println(map);
//    }

    /**
     * @Title:创建微信发送请求post数据
     //* @param  touser发送消息接收者    ，msgtype消息类型（文本/图片等），
     //* @param application_id应用编号。
     //* @param 本方法适用于text型微信消息，contentKey和contentValue只能组一对
     * @return String
     */
    protected String createpostdata(String touser, String msgtype,
                                    int application_id, String contentKey ,String contentValue) {
        weChatData wcd = new weChatData();
        wcd.setTouser(touser);
        wcd.setAgentid(application_id);
        wcd.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey,contentValue+"\n--------\n"+df.format(new Date()));
        wcd.setText(content);
        return gson.toJson(wcd);
    }

    /**
     * @Title  创建微信发送请求post实体
     //* @param  charset消息编码    ，contentType消息体内容类型，
     //* @param  url微信消息发送请求地址，data为post数据，token鉴权token
     * @return String
     */
    public String post(String charset, String contentType, String url,
                       String data,String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httpPost = new HttpPost(url+token);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(
                "call [{}], param:{}, resp:{}", url, data, resp);
        return resp;
    }

    public String getUserId(String corpid, String corpsecret, String code) throws IOException {
        send_weChatMsg sw = new send_weChatMsg();
//        urlData uData = new urlData();
//        uData.setGet_Token_Url(corpid, corpsecret);
//        Map<String, Object> map = gson.fromJson(sw.toAuth(uData.getGet_Token_Url()),
//                new TypeToken<Map<String, Object>>() {
//                }.getType());
////        return map.get("access_token").toString();
//        uData.setGet_User_Url(map.get("access_token").toString(), code);
//        System.out.println(sw.toUserAuth(uData.getGet_User_Url()));
//        map = gson.fromJson(sw.toUserAuth(uData.getGet_User_Url()),
//                new TypeToken<Map<String, Object>>() {
//                }.getType());
//        return "ok".equals(map.get("errmsg").toString()) ? map.get("UserId").toString() : "";
        String token = sw.getToken(corpid,corpsecret);
        System.out.println("token:" + token);
        return sw.getUserId(token, code);
    }

    public String getJssdkTicket(String corpid, String corpsecret) throws IOException {
        send_weChatMsg sw = new send_weChatMsg();
        String token = sw.getToken(corpid,corpsecret);
        System.out.println("token:" + token);
        return sw.getJssdkTicket(token);
    }
}