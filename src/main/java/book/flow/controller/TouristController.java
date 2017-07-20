package book.flow.controller;

import book.flow.enity.Book;
import book.flow.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 游客操作控制器.
 * User: huang
 * Date: 17-7-20
 */
@Controller
@RequestMapping("/tourist")
public class TouristController {

    /**
     * 搜索.
     * @param model 返回数据
     * @param searchText 搜索关键字
     * @param target 目标（范围）
     * @return 查询到的结果
     */
    @PostMapping("/search")
    public String search(Model model, String searchText, String target) {
        if ("user".equals(target)) {    // 搜索用户
            Page<User> users = null;

            return "user_list";
        } else {                        // 搜索图书
            Page<Book> books = null;

            return "book_list";
        }
    }
}
