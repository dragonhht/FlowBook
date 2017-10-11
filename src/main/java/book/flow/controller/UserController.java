package book.flow.controller;

import book.flow.enity.*;
import book.flow.service.FileService;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户基本操作控制器.
 * User: huang
 * Date: 17-7-21
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注销.
     * @param session session信息
     * @return 首页
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    /**
     * 评论图书.
     * @param text 评论内容
     * @param bookId 图书编号
     * @param session session
     * @return 结果页面
     */
    @PostMapping("/comment")
    public String comment(String text, int bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "index";
        }
        String userId = user.getUserId();
        userService.addComment(text, userId, bookId);
        return "redirect:/tourist/bookMessage/" + bookId;
    }

}
