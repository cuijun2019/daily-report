package Alert.weChat;

import java.io.IOException;

public class test {
    public static void main(String[] args) {
        send_weChatMsg sw = new send_weChatMsg();
        try {
            String token = sw.getToken("wxf6eb3a37aa3b2042","PEdriORVCelwC_dfMB_MwNGfs-dTgvfGIncTTliUpjk");
            //String postdata = sw.createpostdata("Luosu", "text", 1, "content","This alert Email come from IapppayBJQA");
            //String resp = sw.post("utf-8", send_weChatMsg.CONTENT_TYPE,(new urlData()).getSendMessage_Url(), postdata, token);
            System.out.println("获取到的token======>" + token);
            //System.out.println("请求数据======>" + postdata);
            //System.out.println("发送微信的响应数据======>" + resp);
            String code = "";
            String userId = sw.getUserId(token, code);
            System.out.println("获取到的userId======>" + userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}