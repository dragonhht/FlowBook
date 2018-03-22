package book.flow.utils;

import book.flow.aliapi.HttpUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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

    /** accessKeyId. */
    private String accessKey;
    /** accessKeySecret. */
    private String accessKeySecret;

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
                    smsUtils.accessKey = jedis.get("accesskey");
                    smsUtils.accessKeySecret = jedis.get("accesskeysecret");
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

    public void sendCode(String phoneNum, String code) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
//替换成你的AK
        final String accessKeyId = this.accessKey;//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = this.accessKeySecret;//你的accessKeySecret，参考本文档步骤2
//初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(this.signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(this.templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
//请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//请求成功
        }
    }

}

