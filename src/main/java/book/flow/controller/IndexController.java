package book.flow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * .
 * User: huang
 * Date: 17-7-18
 */
@Controller
public class IndexController {

    /**
     * 返回首页.
     * @return 首页页面
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 返回公告页面.
     * @return 公告页面
     */
    @RequestMapping("/notice")
    public String notice() {
        return "notice";
    }

    /**
     * 跳转登录界面.
     * @return 登录界面
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转注册页面.
     * @return 注册页面
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
}
