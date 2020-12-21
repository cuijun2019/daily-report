package com.etone.project.utils.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.List;
import java.util.Properties;

public class SimpleMailSender {
    /**
     * 发送邮件的props文件
     */
    private final transient Properties props = System.getProperties();
    /**
     * 邮件服务器登录验证
     */
    private transient MailAuthenticator authenticator;

    /**
     * 邮箱session
     */
    private transient Session session;

    /**
     * 初始化邮件发送器
     *
     * @param smtpHostName SMTP邮件服务器地址
     * @param username     发送邮件的用户名(地址)
     * @param password     发送邮件的密码
     */
    public SimpleMailSender(final String smtpHostName, final String username,
                            final String password) {
        init(username, password, smtpHostName);
    }

    /**
     * 初始化邮件发送器
     *
     * @param username 发送邮件的用户名(地址)，并以此解析SMTP服务器地址
     * @param password 发送邮件的密码
     */
    public SimpleMailSender(final String username, final String password) {
        //通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
        final String smtpHostName = "smtp." + username.split("@")[1];
        init(username, password, smtpHostName);

    }

    /**
     * 初始化
     *
     * @param username     发送邮件的用户名(地址)
     * @param password     密码
     * @param smtpHostName SMTP主机地址
     */
    private void init(String username, String password, String smtpHostName) {
        // 初始化props
        props.setProperty("mail.smtp.host", smtpHostName); // 设置邮件服务器
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        // 验证
        authenticator = new MailAuthenticator(username, password);
        // 创建session
        session = Session.getInstance(props, authenticator);
    }

    /**
     * 发送邮件
     *
     * @param recipient 收件人邮箱地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, String subject, String content, String path)
            throws AddressException, MessagingException {
        // 创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        MimeMultipart multipart = createContent(content, path);
        message.setContent(multipart, "text/html;charset=utf-8");
        // 发送
        Transport.send(message);
    }

    private MimeMultipart createContent(String data, String path) throws MessagingException {
        MimeBodyPart contentPart = new MimeBodyPart();
        MimeMultipart contentMultipart = new MimeMultipart("mixed");

//        MimeBodyPart htmlBodyPart = new MimeBodyPart();
//        htmlBodyPart.setContent(data, "text/html;charset=utf-8");
//        contentMultipart.addBodyPart(htmlBodyPart);

        FileDataSource fds = new FileDataSource(path);
        contentPart.setDataHandler(new DataHandler(fds));
        contentPart.setFileName(fds.getName());
        contentMultipart.addBodyPart(contentPart);

        return contentMultipart;
    }

    private MimeBodyPart createAttachment(String path) throws MessagingException {
        MimeBodyPart attachBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(path);
        attachBodyPart.setDataHandler(new DataHandler(fds));
        attachBodyPart.setFileName(fds.getName());
        return attachBodyPart;
    }

    /**
     * 群发邮件
     *
     * @param recipients 收件人们
     * @param subject    主题
     * @param content    内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Object content)
            throws AddressException, MessagingException {
        // 创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // 设置收件人们
        final int num = recipients.size();
        InternetAddress[] addresses = new InternetAddress[num];
        for (int i = 0; i < num; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.TO, addresses);
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content.toString(), "text/html;charset=utf-8");
        // 发送
        Transport.send(message);
    }

    /**
     * 发送邮件
     *
     * @param recipient 收件人邮箱地址
     * @param mail      邮件对象
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, SimpleMail mail)
            throws AddressException, MessagingException {
        send(recipient, mail.getSubject(), mail.getContent(), "");
    }

    /**
     * 群发邮件
     *
     * @param recipients 收件人们
     * @param mail       邮件对象
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, SimpleMail mail)
            throws AddressException, MessagingException {
        send(recipients, mail.getSubject(), mail.getContent());
    }
}
