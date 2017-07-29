package book.flow.controller;

import book.flow.enity.Book;
import book.flow.enity.User;
import book.flow.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 游客操作控制器.
 * User: huang
 * Date: 17-7-20
 */
@Controller
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private TouristService touristService;

    /**
     * 搜索.
     * @param model 返回数据
     * @param searchText 搜索关键字
     * @param target 目标（范围）
     * @param pageNum 页数
     * @return 查询到的结果
     */
    @PostMapping("/search")
    public String search(Model model, String searchText, String target, int pageNum) {
        if ("user".equals(target)) {            // 搜索用户
            Page<User> users = null;
            users = touristService.searchUserByName(searchText, pageNum);
            model.addAttribute("users", users);
            return "user_list";
        } else {                                // 搜索图书
            Page<Book> books = null;
            if ("bookName".equals(target)) {    // 按书名查找
                books = touristService.searchBookByBookName(searchText, pageNum);
                model.addAttribute("books", books);
            }
            if ("author".equals(target)) {    // 按作者查找
                books = touristService.searchBookByBookAuthor(searchText, pageNum);
                model.addAttribute("books", books);
            }
            if ("publish".equals(target)) {    // 按出版社查找
                books = touristService.searchBookByBookPublish(searchText, pageNum);
                model.addAttribute("books", books);
            }
            return "book_list";
        }
    }

    /**
     * 用户注册.
     * @param user 填写的用户信息
     * @param bindingResult 填写信息检验错误
     * @param verificationCode 手机验证码
     * @param model 用户返回数据
     * @return 相关结果页面
     */
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, String verificationCode, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "register";
        }
        User u = touristService.register(user);
        model.addAttribute("userId", u.getUserId());
        model.addAttribute("userName", u.getUserName());
        model.addAttribute("flag", "registerSeccuss");
        return "msg";
    }

    /**
     * 检测用户名是否已经注册.
     * @param userName 用户名
     * @param response 用于返回信息
     */
    @PostMapping("/test_username")
    @ResponseBody
    public boolean testUserName(String userName, HttpServletResponse response) throws IOException {
        /*response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();*/
        boolean ok;
        ok = touristService.isUserExist(userName);
        if (userName == null || "".equals(userName.trim())) {
            ok = true;
        }
        //out.print(ok);
        return ok;
    }

}
