package book.flow.controller;

import book.flow.enity.User;
import book.flow.service.TouristService;
import book.flow.service.UserHomeService;
import book.flow.service.UserService;
import book.flow.utils.SmsUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 注册和登录模块控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/tourist")
public class TouristRegisterAndLoginController {

    @Autowired
    private TouristService touristService;
    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserService userService;
    @Autowired
    private UserHomeService userHomeService;

    /**
     * 用户登录.
     *
     * @param text     用户名/手机号/编号.
     * @param password 用户密码
     * @param session  用户保存用户信息
     * @return 先关结果页面
     */
    @PostMapping("/login")
    public String login(String text, String password, int role, HttpSession session, Model model) {
        System.out.println("角色" + role);
        if (text.trim() == null || text.trim().equals("")
                || password.trim() == null || password.trim().equals("")) {
            model.addAttribute("error", "用户名或密码不能为空");
            return "login";
        }
        User u = userService.login(text.trim(), password.trim(), role);
        if (u != null) {
            // 是否有未读信息
            boolean ok = false;
            long count = userService.msgCount(u.getUserId());
            if (count != 0) {
                ok = true;
            }
            session.setAttribute("haveMsg", ok);
            session.setAttribute("user", u);
            if (role == 1) {
                session.setAttribute("role", "admin");
            }
            if (role == 0){
                session.setAttribute("role", "user");
            }
            if (role == 2) {
                session.setAttribute("role", "superAdmin");
                return "redirect:/superadmin/adminmanager";
            }
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户不存在或密码错误");
            return "login";
        }
    }

    /**
     * 用户注册.
     *
     * @param user             填写的用户信息
     * @param bindingResult    填写信息检验错误
     * @param verificationCode 手机验证码
     * @param model            用户返回数据
     * @return 相关结果页面
     */
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult,
                           String verificationCode, String smsCode, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "register";
        }

        // 校验图片验证码
        String captchaId = (String) session.getAttribute("vrifyCode");
        String sCode = (String) session.getAttribute("smsCode");

        if (!captchaId.equals(verificationCode)) {
            model.addAttribute("error", "图片验证码不正确");
            return "register";
        }

        // TODO 为了省钱
        /*if (!sCode.equals(smsCode)) {
            model.addAttribute("error", "短信验证码不正确");
            return "register";
        }*/

        User u = touristService.register(user);
        model.addAttribute("userId", u.getUserId());
        model.addAttribute("userName", u.getUserName());
        model.addAttribute("flag", "registerSeccuss");
        return "msg";
    }


    /**
     * 检测用户名是否已经注册.
     *
     * @param userName 用户名
     */
    @PostMapping("/test_username")
    @ResponseBody
    public boolean testUserName(String userName) throws IOException {
        boolean ok = false;
        ok = touristService.isUserNotExist(userName);
        if (userName == null || "".equals(userName.trim())) {
            ok = false;
        }
        return ok;
    }

    /**
     * 检测学号是否已经注册.
     *
     * @param userId 学号
     * @return 是否已经注册
     */
    @PostMapping("/test_userid")
    @ResponseBody
    public boolean testUserId(String userId) {
        boolean ok = true;
        User user = touristService.getUserById(userId.trim());
        if (user != null) {
            ok = false;
        }
        return ok;
    }

    /**
     * 检验并保存学号.
     *
     * @param userId  学号
     * @param stuName 　学生名
     * @param code    　校验码
     * @param session 　session
     * @return
     */
    @PostMapping("putuserid")
    @ResponseBody
    public boolean putUserId(String userId, String stuName, String code, HttpSession session) {
        User user = new User();
        user.setUserId(userId.trim());
        // TODO 检验学生信息
        boolean ok = true;
        // 校验图片验证码
        String captchaId = ((String) session.getAttribute("vrifyCode")).trim();
        if (!captchaId.equals(code.trim())) {
            ok = false;
        }
        if (ok) {
            session.setAttribute("registerUser", user);
        }
        return ok;
    }

    /**
     * 设置用户名及密码.
     *
     * @param userName   用户名
     * @param possword   　密码
     * @param repossword 　确认密码
     * @param session    　session
     * @return
     */
    @PostMapping("/setusername")
    @ResponseBody
    public boolean setUserName(String userName, String possword, String repossword, HttpSession session) {
        User user = (User) session.getAttribute("registerUser");
        boolean ok = false;
        if (user != null) {
            user.setUserName(userName);
            if (possword.trim().equals(repossword.trim())) {
                ok = true;
                user.setPassword(possword);
                session.setAttribute("registerUser", user);
            }
        }
        return ok;
    }

    /**
     * 设置手机.
     * @param phone   手机号
     * @param code    　验证码
     * @param session 　session
     * @return
     */
    @PostMapping("/setphone")
    @ResponseBody
    public boolean setPhone(String phone, String code, HttpSession session) {
        boolean ok = false;
        User user = (User) session.getAttribute("registerUser");
        if (user != null) {
            String sCode = (String) session.getAttribute("smsCode");
            if (sCode.equals(code.trim())) {
                ok = true;
                user.setUserPhone(phone);
                session.setAttribute("registerUser", user);
            }
        }
        return ok;
    }

    /**
     * 设置电子邮箱.
     * @param email 电子邮箱
     * @param code 检验码
     * @param session session
     * @return
     */
    @PostMapping("/setemail")
    @ResponseBody
    public boolean setEmail(String email, String code, HttpSession session) {
        boolean ok = false;
        User user = (User) session.getAttribute("registerUser");
        if (user != null) {
            String emailCode = (String) session.getAttribute("emailCode");
            if (emailCode.equals(code.trim())) {
                ok = true;
                user.setUserEmail(email);
                session.setAttribute("registerUser", user);
            }
        }
        touristService.register(user);
        return ok;
    }

    /**
     * 发送邮箱验证码.
     * @param email 电子邮箱
     * @param session session
     * @return
     */
    @PostMapping("/testemail")
    @ResponseBody
    public boolean testEmail(String email, HttpSession session) {
        boolean ok = false;
        String code = userHomeService.checkEmail(email.trim());
        session.setAttribute("emailCode", code);
        ok = true;
        return ok;
    }


    /**
     * 验证码.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/captcha_image")
    public void getKaptchaImage(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("vrifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 发送短信
     *
     * @param recipient 接收方手机号
     * @param session   session
     * @return
     */
    @PostMapping("/sendSMS")
    @ResponseBody
    public boolean sendSMS(String recipient, HttpSession session) {
        if (recipient == null && "".equals(recipient)) {
            return false;
        }
        boolean ok = false;
        SmsUtils smsUtils = SmsUtils.getIntence();
        String code = smsUtils.getCode();
        System.out.println("CODE: " + code);
        session.setAttribute("smsCode", code);
        ok = smsUtils.sendSmsCode(code, recipient);

        return ok;
    }
}
