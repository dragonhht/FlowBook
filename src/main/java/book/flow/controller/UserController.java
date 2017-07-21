package book.flow.controller;

import book.flow.enity.User;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户操作控制器.
 * User: huang
 * Date: 17-7-21
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录.
     * @param user 用户信息
     * @param bindingResult 检验后的错误信息
     * @param session 用户保存用户信息
     * @return 先关结果页面
     */
    @PostMapping("/login")
    public String login(@Valid User user, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            // TODO 如果检验出错
        }
        User u = userService.login(user);
        if (u != null) {
            session.setAttribute("user", u);
            return "index";
        } else {
            // TODO 用户不存在
            return "login";
        }
    }

}
