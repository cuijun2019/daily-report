package Alert.weChat;

public class urlData {
    String corpid;
    String corpsecret;
    String Get_Token_Url;
    String SendMessage_Url;

    String accesstoken;
    String code;
    String Get_User_Url;
    String Get_Jssdk_Ticket;
    String Get_Temp_Voice_Url;

    public String getCorpid() {
        return corpid;
    }
    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }
    public String getCorpsecret() {
        return corpsecret;
    }
    public void setCorpsecret(String corpsecret) {
        this.corpsecret = corpsecret;
    }
    public void setGet_Token_Url(String corpid,String corpsecret) {
        this.Get_Token_Url ="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
    }
    public String getGet_Token_Url() {
        return Get_Token_Url;
    }
    public String getSendMessage_Url(){
        SendMessage_Url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
        return SendMessage_Url;
    }

    public String getAccesstoken() {
        return accesstoken;
    }
    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public void setGet_User_Url(String accesstoken, String code) {
        this.Get_User_Url ="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+accesstoken+"&code="+code;
    }
    public String getGet_User_Url() {
        return Get_User_Url;
    }

    public void setGet_Jssdk_Ticket(String accesstoken) {
        this.Get_Jssdk_Ticket = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token="+accesstoken;
    }
    public String getGet_Jssdk_Ticket() {
        return Get_Jssdk_Ticket;
    }

    public void setGet_Temp_Voice_Url(String accesstoken, String serverId) {
        this.Get_Temp_Voice_Url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + accesstoken + "&media_id=" + serverId;
    }
    public String getGet_Temp_Voice_Url() {
        return Get_Temp_Voice_Url;
    }
}