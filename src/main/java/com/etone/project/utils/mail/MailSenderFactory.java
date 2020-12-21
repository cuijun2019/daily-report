package com.etone.project.utils.mail;

import com.etone.commons.util.PropertiesUtil;

/**
 * 发件箱工厂
 *
 * @author MZULE
 *
 */
public class MailSenderFactory {

    /**
     * 服务邮箱
     */
    private static SimpleMailSender serviceSms = null;

    /**
     * 获取邮箱
     *
     * @param type 邮箱类型
     * @return 符合类型的邮箱
     */
    public static SimpleMailSender getSender(MailSenderType type) {
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("config.properties");
        String username = propertiesUtil.getValue("username");
        String password = propertiesUtil.getValue("password");
        if (type == MailSenderType.SERVICE) {
            if (serviceSms == null) {
                serviceSms = new SimpleMailSender(username, password);
            }
            return serviceSms;
        }
        return null;
    }

}
