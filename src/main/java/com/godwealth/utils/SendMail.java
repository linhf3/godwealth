package com.godwealth.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 例：销售合同服务API
 *
 * @author sie_linhongfei
 * @createDate 2021/11/7 11:45
 */
public class SendMail {
    /**
     * 发送qq邮件
     * @param msg
     */
    public static void sendQqMail(String msg) throws MessagingException, GeneralSecurityException {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        // =====================自行修改账号和授权码
        // 后面的字符是授权码
        ts.connect("smtp.qq.com", Constant.QQ, "pfhmamokagyxbjge");
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(Constant.QQ));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(Constant.QQ));
        // 邮件的标题
        message.setSubject("重要通知");
        // 邮件的文本内容
        message.setContent("<font style='color:red'>"+msg+"</font>", "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }




}
