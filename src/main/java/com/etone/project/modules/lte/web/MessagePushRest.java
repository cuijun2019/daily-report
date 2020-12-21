package com.etone.project.modules.lte.web;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/messagePush")
public class MessagePushRest {
    private final String TOKEN = "1qaz2wsx";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws IOException {
        response.getWriter().print("Hello World");
    }

    /**
     * 第三方回复加密消息给公众平台
     */
    @ResponseBody
    @RequestMapping(value = "/push", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public void authGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (signature == null || "".equals(signature) || timestamp == null || "".equals(timestamp)
                || nonce == null || "".equals(nonce) || echostr == null || "".equals(echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (checkSignature(signature, TOKEN, timestamp, nonce)) {
            System.out.println("echostr:" + echostr);
            response.getWriter().print(echostr);
        }
    }

    private boolean checkSignature(String signature, String token, String timestamp, String nonce) {
        String tmpStr;
        try {
            tmpStr = com.qq.weixin.mp.aes.SHA1.getSHA1(token, timestamp, nonce, "");
            return tmpStr.equals(signature);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/templateSend", method = RequestMethod.GET)
    public void templateSend(HttpServletRequest request) {
        System.out.println("templateSend：");
        String appId = request.getParameter("appId");
        String secret = request.getParameter("secret");
        String code = request.getParameter("code");
        net.sf.json.JSONObject jsonParam = new net.sf.json.JSONObject();
        jsonParam.put("touser", WeixinUtil.getJscode2session(appId, secret, code));
        jsonParam.put("template_id", request.getParameter("templateId"));
        jsonParam.put("page", request.getParameter("page"));
        jsonParam.put("form_id", request.getParameter("formId"));
        jsonParam.put("emphasis_keyword", request.getParameter("emphasisKeyword"));

        net.sf.json.JSONObject data = new net.sf.json.JSONObject();
        net.sf.json.JSONObject keyword = new net.sf.json.JSONObject();
        data.put("value", request.getParameter("keyword1"));
        keyword.put("keyword1", data);
        jsonParam.put("data", keyword);
        data.put("value", request.getParameter("keyword2"));
        keyword.put("keyword2", data);
        jsonParam.put("data", keyword);
        data.put("value", request.getParameter("keyword3"));
        keyword.put("keyword3", data);
        jsonParam.put("data", keyword);
        data.put("value", request.getParameter("keyword4"));
        keyword.put("keyword4", data);
        jsonParam.put("data", keyword);

        WeixinUtil.sendTemplate(appId, secret, jsonParam);
    }
}
