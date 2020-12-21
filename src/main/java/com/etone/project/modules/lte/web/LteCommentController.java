package com.etone.project.modules.lte.web;

import Alert.weChat.Sign;
import Alert.weChat.send_weChatMsg;
import com.etone.project.base.entity.share.User;
import com.etone.project.base.support.security.Auditmeta;
import com.etone.project.base.support.security.ShiroDbRealm;
import com.etone.project.core.model.QueryCriteria;
import com.etone.project.core.model.Result;
import com.etone.project.core.web.control.GenericController;
import com.etone.project.modules.lte.manager.ILteCommentManager;
import com.etone.project.utils.Common;
import com.etone.project.utils.ResponseUtils;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/modules/ltecomment")
@Auditmeta(code = "003", name = "评论信息", symbol = "")
public final class LteCommentController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(LteCommentController.class);

    @Autowired
    private ILteCommentManager lteCommentManager;

    @ResponseBody
    @RequestMapping(value = "/genSign", method = {RequestMethod.GET, RequestMethod.POST})
    public void genSign(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String corpid = request.getParameter("corpid");
        String corpsecret = request.getParameter("corpsecret");
        String token = lteCommentManager.getToken(corpid, corpsecret);
        String url = request.getParameter("url");
        Sign sign = new Sign();
        JSONObject json = sign.sign(token, url);

        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/getTempVoice", method = {RequestMethod.GET, RequestMethod.POST})
    public void getTempVoice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = "";
        String corpid = request.getParameter("corpid");
        String corpsecret = request.getParameter("corpsecret");
        String serverIds = request.getParameter("serverIds");
        String path = request.getSession().getServletContext().getRealPath("/file/upload/temp/");
        send_weChatMsg sw = new send_weChatMsg();
//        String userId = sw.getUserId(corpid, corpsecret, code);
        String token = lteCommentManager.getToken(corpid, corpsecret);
        String filePath = lteCommentManager.downloadMedia(token, serverIds, path, "amr");

        Common.exec("sox /opt/tomcat/webapps/ROOT/file/upload/temp/" + serverIds + ".amr /opt/tomcat/webapps/ROOT/file/upload/temp/" + serverIds + ".mp3");
        Common.exec("rm -rf /opt/tomcat/webapps/ROOT/file/upload/temp/*.amr");

        JSONObject json = new JSONObject();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser != null) {
            message = shiroUser.getUser().getFullName();
        } else {
            message = "崔军";
        }
        json.put("message", Common.judgeString(filePath) ? message : "上传失败");
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryArticle", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("searchContext", request.getParameter("searchContext"));
        criteria.put("rowStart", request.getParameter("rowStart"));
        criteria.put("pageSize", request.getParameter("pageSize"));
        List<Map> list = lteCommentManager.queryArticle(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryComment", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("readAloudId", request.getParameter("readAloudId"));
        criteria.put("isRead", request.getParameter("isRead"));
        List<Map> list = lteCommentManager.queryComment(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/saveThumbSup", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveThumbSup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = lteCommentManager.saveThumbSup(Long.parseLong(request.getParameter("readAloudId")), Long.parseLong(request.getParameter("isRead")));

        JSONObject json = new JSONObject();
        json.put("flag", flag);
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/removeThumbSup", method = {RequestMethod.GET, RequestMethod.POST})
    public void removeThumbSup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        lteCommentManager.removeThumbSup(Long.parseLong(request.getParameter("readAloudId")));
    }

    @ResponseBody
    @RequestMapping(value = "/saveComment", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = "";
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("readAloudId", request.getParameter("readAloudId"));
        criteria.put("commentContent", request.getParameter("commentContent"));
        criteria.put("isRead", Long.parseLong(request.getParameter("isRead")));
        if (lteCommentManager.validSameComment(criteria)) {
            message = "不要太贪心,相同的评论发一次就够了";
        } else {
            lteCommentManager.saveComment(criteria);
        }

        JSONObject json = new JSONObject();
        json.put("message", message);
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryAudio", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryAudio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map> list = lteCommentManager.queryAudio(request.getParameter("date"));
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/validCurrentTime", method = {RequestMethod.GET, RequestMethod.POST})
    public void validCurrentTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = lteCommentManager.validCurrentTime(request.getParameter("date"));

        JSONObject json = new JSONObject();
        json.put("flag", flag);
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
     @RequestMapping(value = "/saveReadAloudInfo", method = {RequestMethod.GET, RequestMethod.POST})
     public void saveReadAloudInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("article", request.getParameter("article"));
        criteria.put("recordAddress", request.getParameter("recordAddress"));
        criteria.put("date", request.getParameter("date"));
        criteria.put("isEditable", "true".equals(request.getParameter("isEditable")) ? 1 : 2);
        criteria.put("removeAddress", request.getParameter("removeAddress"));

        try {
            lteCommentManager.saveReadAloudInfo(criteria);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result = new Result(Result.ERROR, "保存失败", "保存失败");
        } finally {
            writer = response.getWriter();
            System.out.println(result.toString());
            writer.println(result.toString());  //想办法把map转成json
            writer.flush();
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveReadInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void saveReadInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter writer = null;
        Result result = new Result(Result.SUCCESS, "保存成功！", "保存成功！");
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("article", request.getParameter("article"));
        criteria.put("thought", request.getParameter("thought"));

        try {
            lteCommentManager.saveReadInfo(criteria);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result = new Result(Result.ERROR, "保存失败", "保存失败");
        } finally {
            writer = response.getWriter();
            System.out.println(result.toString());
            writer.println(result.toString());  //想办法把map转成json
            writer.flush();
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryReadStatistics", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryReadStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        List<Map> list = lteCommentManager.queryReadStatistics(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryReadAloudStatistics", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryReadAloudStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        List<Map> list = lteCommentManager.queryReadAloudStatistics(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryCommentStatistics", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryCommentStatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        List<Map> list = lteCommentManager.queryCommentStatistics(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryReadUnfinishUser", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryReadUnfinishUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        json.put("unfinishUser", lteCommentManager.queryReadUnfinishUser(request.getParameter("createTime")));
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryAloudUnfinishUser", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryAloudUnfinishUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        json.put("unfinishUser", lteCommentManager.queryAloudUnfinishUser(request.getParameter("createTime")));
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryCommentUnfinishUser", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryCommentUnfinishUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        json.put("unfinishUser", lteCommentManager.queryCommentUnfinishUser(request.getParameter("createTime")));
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/getCurrentUser", method = {RequestMethod.GET, RequestMethod.POST})
    public void getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        long userId = 0;
        String currentUser = "";
        String currentFullName = "";
        String headPortraitAddress = "";
        User user;
        if (shiroUser != null) {
            user = shiroUser.getUser();
            userId = shiroUser.getId();
            currentUser = user.getAccount();
            currentFullName = user.getFullName();
            headPortraitAddress = lteCommentManager.findHeadPortraitAddress(currentUser);
            System.out.println("----" + headPortraitAddress);
        } else {
            currentUser = "3223";
            currentFullName = "崔军";
        }
        json.put("userId", userId);
        json.put("currentUser", currentUser);
        json.put("currentFullName", currentFullName);
        json.put("headPortraitAddress", headPortraitAddress);
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/deleteComment", method = {RequestMethod.GET, RequestMethod.POST})
    public void deleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("commentPersonCode", request.getParameter("commentPersonCode"));
        criteria.put("commentTime", request.getParameter("commentTime"));
        lteCommentManager.deleteComment(criteria);
        JSONObject json = new JSONObject();
        ResponseUtils.print(response, json.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/queryReadInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryReadInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("studentNo", request.getParameter("studentNo"));
        List<Map> list = lteCommentManager.queryReadInfo(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryReadAloudInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryReadAloudInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("studentNo", request.getParameter("studentNo"));
        List<Map> list = lteCommentManager.queryReadAloudInfo(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/queryCommentInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void queryCommentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("startDate", request.getParameter("startDate"));
        criteria.put("endDate", request.getParameter("endDate"));
        criteria.put("studentNo", request.getParameter("studentNo"));
        List<Map> list = lteCommentManager.queryCommentInfo(criteria);
        ResponseUtils.printArrayList(response, list);
    }

    @ResponseBody
    @RequestMapping(value = "/setUserLocalId", method = {RequestMethod.GET, RequestMethod.POST})
    public void setUserLocalId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryCriteria criteria = new QueryCriteria();
        criteria.put("userId", request.getParameter("userId"));
        criteria.put("localId", request.getParameter("localId"));
        lteCommentManager.setUserLocalId(criteria);
    }

    @ResponseBody
    @RequestMapping(value = "/getPictureAddress", method = {RequestMethod.GET, RequestMethod.POST})
    public void getPictureAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = "";
        String corpid = request.getParameter("corpid");
        String corpsecret = request.getParameter("corpsecret");
        String serverIds = request.getParameter("serverIds");
        String path = request.getSession().getServletContext().getRealPath("/file/upload/");
        send_weChatMsg sw = new send_weChatMsg();
//        String userId = sw.getUserId(corpid, corpsecret, code);
        String token = lteCommentManager.getToken(corpid, corpsecret);
        String filePath = lteCommentManager.downloadMedia(token, serverIds, path, "png");
        lteCommentManager.updatePictureAddress(serverIds, filePath, request.getParameter("userId"));

        JSONObject json = new JSONObject();
        json.put("filePath", filePath);
        json.put("message", Common.judgeString(filePath) ? message : "上传失败");
        ResponseUtils.print(response, json.toString());
    }
}