package book.flow.controller;

import book.flow.enity.User;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户操作控制器.
 * User: huang
 * Date: 17-7-21
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录.
     * @param text 用户名/手机号/编号.
     * @param password 用户密码
     * @param session 用户保存用户信息
     * @return 先关结果页面
     */
    @PostMapping("/login")
    public String login(String text, String password, HttpSession session, Model model) {
        System.out.println(text + ":" + password);
        if (text.trim() == null || text.trim().equals("")
                || password.trim() == null || password.trim().equals("")) {
            model.addAttribute("error", "用户名或密码不能为空");
            return "login";
        }
        User u = userService.login(text.trim(), password.trim());
        if (u != null) {
            session.setAttribute("user", u);
            return "index";
        } else {
            model.addAttribute("error", "用户不存在或密码错误");
            return "login";
        }
    }

    /**
     * 跳转到用户主页.
     * @return 用户主页
     */
    @RequestMapping("/userHome")
    public String userHome() {

        return "user_home";
    }

    /**
     * 用户注销.
     * @param session session信息
     * @return 首页
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

}
