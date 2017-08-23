package book.flow.utils;

import book.flow.aliapi.HttpUtils;
import org.apache.http.HttpResponse;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码发送工具类.
 * User: huang
 * Date: 17-8-23
 */
public class SmsUtils {

    private static SmsUtils smsUtils;

    /** APPCode. */
    private String appCode;
    /** 签名. */
    private String signName;
    /** 短信模板. */
    private String templateCode;

    private Random random;

    private SmsUtils() {

    }

    public static synchronized SmsUtils getIntence() {
        if (smsUtils == null) {
            synchronized (SmsUtils.class) {
                if (smsUtils == null) {
                    smsUtils = new SmsUtils();
                    Jedis jedis = new Jedis();
                    smsUtils.appCode = jedis.get("appCode");
                    smsUtils.signName = jedis.get("SignName");
                    smsUtils.templateCode = jedis.get("TemplateCode");
                    smsUtils.random = new Random();
                }
            }
        }
        return smsUtils;
    }

    /**
     * 发送验证码
     * @param code 验证码
     * @return
     */
    public boolean sendSmsCode(String code, String recipient) {
        boolean ok = false;
        String host = "http://sms.market.alicloudapi.com";
        String path = "/singleSendSms";
        String method = "GET";
        String appcode = appCode;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ParamString", "{'code': ' " + code + " '}");
        querys.put("RecNum", recipient);
        querys.put("SignName", signName);
        querys.put("TemplateCode", templateCode);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            ok = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    /**
     * 获取验证码.
     * @return 验证码
     */
    public String getCode() {
        int sensCode = random.nextInt(9999);
        String smsCode = String.valueOf(sensCode);
        int len = smsCode.length();
        if (len < 4) {
            String code = "";
            for (int i = 0; i < (4-len); i++) {
                code = code + "0";
            }
            smsCode = code + smsCode;
        }
        return smsCode;
    }
}

