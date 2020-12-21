/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.support.security;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 验证码Servlet
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14064 $$
 */
public class JcaptchaServlet extends HttpServlet {
    // members
    public static final String IMAGE_FORMAT = "jpeg";

    private ImageCaptchaService captchaService;

    // static block

    // constructors

    // properties

    // public methods

    /**
     * 初始化，通过Spring实例化ImageCaptchaService
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        captchaService = BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, ImageCaptchaService.class);
    }

    /**
     * 具体生成验证码实现
     *
     * @param request  请求
     * @param response 发送
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] captchaChallenge = null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            String captchaId = request.getSession().getId();
            BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
            ImageIO.write(challenge, IMAGE_FORMAT, stream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        captchaChallenge = stream.toByteArray();

        // 发送
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/" + IMAGE_FORMAT);

        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallenge);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
