package book.flow.controller;

import book.flow.enity.Book;
import book.flow.enity.Notice;
import book.flow.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * .
 * User: huang
 * Date: 17-7-18
 */
@Controller
public class IndexController {

    @Autowired
    private TouristService touristService;

    /**
     * 返回首页.
     * @param model 用于返回数据
     * @return 首页页面
     */
    @RequestMapping("/index")
    public String index(Model model) {
        Page<Book> hotBook = touristService.getHotBook();
        Page<Notice> notices = touristService.getIndexNotice();
        model.addAttribute("hotBook", hotBook);
        model.addAttribute("indexNotice", notices);
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
