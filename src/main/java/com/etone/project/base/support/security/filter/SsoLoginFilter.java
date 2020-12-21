package com.etone.project.base.support.security.filter;

import com.etone.project.base.support.security.CaptchaUsernamePasswordToken;
import com.etone.project.base.support.security.SsoLoginToken;
import com.etone.project.core.utils.encode.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User:chz
 * Date:13-12-17
 */
public class SsoLoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SsoLoginFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession();
        Map userName = request.getParameterMap();

        Subject s = SecurityUtils.getSubject();

        if(userName.get("city") != null){ //名通 状态库上的工单 提供 URL链接
            try {  //http://localhost:8980/ltemr/sso?city=guangzhou&flon=113.401535&flat=23.139785
                if (s.isAuthenticated())
                    s.logout();

                    Date d = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    int x=(int)(Math.random()*10);//生成一个个位的随机数 ,用来随机加几天
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(d);
                    calendar.add(calendar.DATE,x);//把日期往后增加一天.整数往后推,负数往前移动
                    String yanzhengma = x+"yanmafalsegdltemr"+dateFormat.format(calendar.getTime());  //生成验证码，用以比对跳过验证码
                    CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken("gd_mingtong","mingtong",true,null,yanzhengma); //yanzhengma 跳过验证
                    s.login(token);

                    String[] city = (String[]) userName.get("city");  //传来的经纬度对应的城市
                    String[] flon =  (String[])userName.get("flon"); // 经度
                    String[] flat =  (String[])userName.get("flat"); // 纬度
                if (userName.containsKey("city") && userName.containsKey("flon")  && userName.containsKey("flat")&&!userName.containsKey("message")) {
                    response.sendRedirect(request.getContextPath() + "/modules/lterd/lte_gis_fl_rd_index.jsp?city="+city[0]+"&flon="+flon[0]+"&flat="+flat[0]);
                }
                else if (userName.containsKey("city") && userName.containsKey("flon")  && userName.containsKey("flat")&& userName.containsKey("message")) {
                    response.sendRedirect(request.getContextPath() + "/modules/lteFlAndRdMR/findWeakMRkpi?city="+city[0]+"&flon="+flon[0]+"&flat="+flat[0]); //返回需要的字符串
                } else {
                    logger.error(" 名通工单URL：参数缺失！"+new Date());
                    response.sendRedirect(request.getContextPath() + "/login/error");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/login/error");
            }
        }else if( userName.get("loginName") != null){   //接入性能管理平台
                try {  //http://localhost:8980/ltemr/sso?loginName=gd_chenbin&soc_password=9BC9D388DF097018B3BB69DA96646AEB
                    if (s.isAuthenticated())
                        s.logout();
                    if (userName.containsKey("loginName") && userName.containsKey("soc_password")) {
                        String[] params = (String[]) userName.get("loginName");  //登陆名
                        String[] soc_password =  (String[])userName.get("soc_password"); // 秘钥； gdltemr+当前日期的  MD5加密

                        Date d = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        String value = "gdltemr";
                        value +=dateFormat.format(d);
                        String verify = MD5Util.getStringMD5(value).toUpperCase(); //    gdltemr+当前日期md5加密，并转大写；例如  gdltemr20160831 ->  DBF58B23A03438F21C76751750C2D8AF
                        if(!verify.equals(soc_password[0])){  //如果秘钥验证不通过
                            logger.error(params[0]+" 秘钥验证不通过！"+new Date());
                            response.sendRedirect(request.getContextPath() + "/login/error");
                            return;
                        }
                        int x=(int)(Math.random()*10);//生成一个个位的随机数 ,用来随机加几天
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(d);
                        calendar.add(calendar.DATE,x);//把日期往后增加一天.整数往后推,负数往前移动
                        String yanzhengma = x+"yanmafalsegdltemr"+dateFormat.format(calendar.getTime());  //生成验证码，用以比对跳过验证码
                        CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(params[0],"111111",true,null,yanzhengma); //yanzhengma 跳过验证
                        s.login(token);
                        response.sendRedirect(request.getContextPath() + "/main");
                        //WebUtils.issueRedirect(request, response, request.getContextPath() + "/main");
                        //return;
                        //response.sendRedirect(request.getContextPath() + "/main?redirect=/modules/ltesoft/lte_IntegratedKpi_index.jsp");
                    } else {
                        logger.error(" 单点登陆：用户名或秘钥传递缺失！"+new Date());
                        response.sendRedirect(request.getContextPath() + "/login/error");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.sendRedirect(request.getContextPath() + "/login/error");
                }
        } else { //不是合法链接
            response.sendRedirect(request.getContextPath() + "/login/error");
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
