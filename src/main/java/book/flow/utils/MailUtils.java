package book.flow.utils;

import org.apache.log4j.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件工具类.
 * User: huang
 * Date: 17-7-22
 */
public final class MailUtils {
    /** log4j日志. */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 邮件工具类实例. */
    private static MailUtils mailUtils;

    /** 发送邮件的服务器. */
    private String sendServer;

    /** 端口. */
    private String port = "25";

    /** 发送者的邮箱. */
    private String userName;

    /** 发送者的邮箱的第三方授权码. */
    private String password;

    /** 邮件发送的基本属性. */
    private Properties properties = new Properties();

    /** session的session. */
    private Session session;


    /**
     * 单例模式隐藏构造器.
     */
    private MailUtils() {

    }

    /**
     * 获取邮件工具类实例.
     * @return 邮件工具类实例
     */
    public static synchronized MailUtils getMailUtils() {
        if (mailUtils == null) {
            synchronized (MailUtils.class) {
                if (mailUtils == null) {
                    mailUtils = new MailUtils();
                }
            }
        }
        return mailUtils;
    }

    /**
     * 设置发送邮件的服务器和端口号.
     *
     * @param sendServer 发送有邮件的服务器
     * @param port 端口号
     * <p>注意:</p>
     *	服务器:<br/>
     *			163邮箱服务器: smtp.163.com<br/>
     *			126邮箱服务器: smtp.126.com<br/>
     *			qq邮箱服务器: smtp.qq.com<br/>
     * 端口号:<br/>
     * 			126 , 163 邮箱端口号为: 25<br/>
     * 			qq 端口号为: 587
     */
    public void setProperty(String sendServer, String port) {
        this.sendServer = sendServer;
        this.port = port;
        // 设置发送邮件的服务器
        this.properties.setProperty("mail.host", sendServer);
        //发送服务需要身份验证
        this.properties.setProperty("mail.smtp.auth", "true");
        //发送邮件协议名称
        this.properties.setProperty("mail.transport.protocol", "smtp");
        //设置端口
        this.properties.setProperty("mail.smtp.port", port);
    }


    /**
     * 设置发送者的邮箱和邮箱的第三方授权码.
     * @param userName 发送者邮箱
     * @param password 第三方授权码
     */
    public void setSession(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.session = Session.getInstance(this.properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //邮箱和第三方登录授权码
                return new PasswordAuthentication(userName, password);
            }
        });
        this.session.setDebug(true);
    }

    /**
     * 发送邮件.
     * @param receiver 收件人邮箱
     * @param title 邮件主题
     * @param context 邮件内容
     * @return 是否成功
     */
    public boolean sendMail(String receiver, String title, String context) {
        boolean ok = false;
        //创建发送邮件的对象Message
        Message message = new MimeMessage(this.session);
        try {
            //设置发件人
            message.setFrom(new InternetAddress(this.userName));
            //设置收件人
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));
            //设置标题
            message.setSubject(title);
            //设置发送时间
            message.setSentDate(new Date());
            //设置正文(有链接选择text/html;charset=utf-8)
            message.setContent(context, "text/html;charset=utf-8");

            //发送郵件Transport
            Transport.send(message);
            ok = true;
            System.out.println("发送成功");
        } catch (Exception e) {
            logger.info(e);
        }

        return ok;
    }

    /**
     * 发送邮件的总方法.
     * @param sendServer 发送邮件的服务器
     * @param port 端口
     * @param receiver 收件人邮箱
     * @param title 邮件主题
     * @param context 邮件内容
     * @return 发送是否成功
     */
    private boolean sendMailByOther(String sendServer, String port, String receiver, String title, String context) {
        boolean ok = false;
        this.setProperty(sendServer, port);
        this.setSession(this.userName, this.password);
        ok = sendMail(receiver, title, context);
        return ok;
    }

    /**
     * 通过qq邮箱发送邮件.
     * @param receiver 收件人邮箱
     * @param title 邮件主题
     * @param context 邮件内容
     * @return 是否成功
     */
    public boolean sendMailByQQ(String receiver, String title, String context) {
        boolean ok = false;
        ok = sendMailByOther("smtp.qq.com", "587", receiver, title, context);
        return ok;
    }

    /**
     * 通过163邮箱发送邮件.
     * @param receiver 收件人邮箱
     * @param title 邮件主题
     * @param context 邮件内容
     * @return 是否成功
     */
    public boolean sendMailBy163(String receiver, String title, String context) {
        boolean ok = false;
        ok = sendMailByOther("smtp.163.com", "25", receiver, title, context);
        return ok;
    }

    /**
     * 通过126邮箱发送邮件.
     * @param receiver 收件人邮箱
     * @param title 邮件主题
     * @param context 邮件内容
     * @return 是否成功
     */
    public boolean sendMailBy126(String receiver, String title, String context) {
        boolean ok = false;
        ok = sendMailByOther("smtp.126.com", "25", receiver, title, context);
        return ok;
    }

    /**
     * 获取当前使用的服务器.
     *
     * @return the send server
     */
    public String getSendServer() {
        return sendServer;
    }

    /**
     * 设置发送邮件使用的服务器.
     *
     * @param sendServer the send server
     */
    public void setSendServer(String sendServer) {
        this.sendServer = sendServer;
        this.properties.setProperty("mail.host", sendServer);
    }

    /**
     * 获取当前设置的端口号.
     *
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * 设置端口号.
     *
     * @param port the port
     */
    public void setPort(String port) {
        this.port = port;
        this.properties.setProperty("mail.smtp.port", port);
    }
}