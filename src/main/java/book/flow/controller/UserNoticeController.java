package book.flow.controller;

import book.flow.enity.*;
import book.flow.service.UserNoticeService;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户寻书.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserNoticeController {

    @Autowired
    private UserNoticeService userNoticeService;


    /**
     * 跳转上传期望页面.
     * @return 上传页面
     */
    @RequestMapping("/uploadNotice")
    public String uploadNotice() {
        return "user_find_book";
    }

    /**
     * 上传期望.
     * @param text 期望内容
     * @param session session
     * @return 结果
     */
    @PostMapping("/saveNotice")
    @ResponseBody
    public String saveNotice(String text, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String userId = null;
        if (user != null) {
            userId = user.getUserId();
        }
        boolean ok = false;
        ok = userNoticeService.addNotice(text, userId);
        if (ok) {
            return "上传成功";
        } else {
            return "上传失败";
        }
    }

}
