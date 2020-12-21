package com.etone.project.utils.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // 发送邮件
        SimpleMailSender sms = MailSenderFactory.getSender(MailSenderType.SERVICE);
        List<String> recipients = new ArrayList<>();
        recipients.add("18319670614@139.com");
        try {
            for (String recipient : recipients) {
                sms.send(recipient, "价格变动", "您关注的物品赶快购物吧。", "d:/tools/JetbrainsCrack-3.1-release-enc.jar");
            }
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
