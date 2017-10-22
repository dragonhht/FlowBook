package book.flow.controller;

import book.flow.enity.Activity;
import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    @GetMapping("/search")
    public String search(Model model, String searchText, String target, int pageNum) {
        model.addAttribute("nowPage", pageNum);
        pageNum = pageNum - 1;
        if ("user".equals(target)) {            // 搜索用户
            Page<User> users = null;
            users = touristService.searchUserByName(searchText, pageNum);
            long size = touristService.getSearchUserPageSize(searchText);
            model.addAttribute("lastPage", size);
            model.addAttribute("users", users);
            model.addAttribute("searchText", searchText);
            model.addAttribute("target", "user");
            return "search_user";
        } else {                                // 搜索图书
            Page<Book> books = null;
            if ("bookName".equals(target)) {    // 按书名查找
                books = touristService.searchBookByBookName(searchText, pageNum);
                long size = touristService.getBookCountByBookName(searchText);
                model.addAttribute("lastPage", size);
                model.addAttribute("books", books);
                model.addAttribute("target", "bookName");
            }
            if ("author".equals(target)) {    // 按作者查找
                books = touristService.searchBookByBookAuthor(searchText, pageNum);
                long size = touristService.getBookCountByBookAuthor(searchText);
                model.addAttribute("lastPage", size);
                model.addAttribute("books", books);
                model.addAttribute("target", "author");
            }
            if ("publish".equals(target)) {    // 按出版社查找
                books = touristService.searchBookByBookPublish(searchText, pageNum);
                long size = touristService.getBookCountByBookPublish(searchText);
                model.addAttribute("lastPage", size);
                model.addAttribute("books", books);
                model.addAttribute("target", "publish");
            }
            model.addAttribute("searchText", searchText);
            return "search_book";
        }
    }

    @RequestMapping("/bookType/{typeId}/{pageNum}")
    public String getBookByType(@PathVariable("typeId") int typeId,@PathVariable("pageNum") int pageNum, Model model) {
        List<Book> books = null;
        model.addAttribute("nowPage", pageNum);
        // System.out.println("pageNum" + pageNum);
        pageNum = pageNum - 1;
        books = touristService.getBookByTypeId(typeId, pageNum);
        long size = touristService.getBookByTypeIdCount(typeId);
        model.addAttribute("lastPage", size);
        model.addAttribute("books", books);
        model.addAttribute("byType", "type");
        return "search_book";
    }

    /**
     * 获取图书详细信息.
     * @param bookId 图书编号
     * @param model 用于返回数据
     * @return 详细信息页面
     */
    @RequestMapping(value = "/bookMessage/{bookId}")
    public String bookInformation(@PathVariable("bookId") int bookId, Model model) {
        Book book = null;
        User user = null;
        List<LoanRecord> records;
        int commentSize = 0;
        book = touristService.getBookById(bookId);
        user = touristService.getNowOwner(bookId);
        commentSize = book.getComments().size();
        records = touristService.getRecordByBookId(bookId);
        model.addAttribute("book", book);
        model.addAttribute("nowOwner", user);
        model.addAttribute("commentSize", commentSize);
        model.addAttribute("records", records);
        return "book_information";
    }

    /**
     * 用户信息界面.
     * @param userId 用户编号
     * @param model 用于返回信息
     * @return 用户信息界面
     */
    @RequestMapping("/user/{userId}")
    public String userMessage(@PathVariable String userId, Model model, HttpSession session) {
        User user = null;
        List<LoanRecord> records = null;
        user= touristService.getUserById(userId);
        records = touristService.getRecordsByUserId(userId);
        User u = (User) session.getAttribute("user");
        boolean ok = false;
        if (u != null) {
            String selfId = u.getUserId();
             ok = touristService.isFriendExist(selfId, userId);
        }
        model.addAttribute("isAdd", ok);
        model.addAttribute("user", user);
        model.addAttribute("records", records);
        return "user";
    }

    /**
     * 跳转是活动详情页面.
     * @param activeId
     * @param model
     * @return
     */
    @RequestMapping("/active/{activeId}")
    public String toActivite(@PathVariable("activeId") int activeId, Model model) {
        Activity activity = touristService.getActivityById(activeId);
        model.addAttribute("active", activity);
        return "activity_information";
    }

    /**
     * 跳转活动列表.
     * @param model model
     * @return
     */
    @RequestMapping("/activelist/{pageNum}")
    public String activeList(@PathVariable("pageNum") int pageNum, Model model) {
        model.addAttribute("nowPage", pageNum);
        pageNum = pageNum - 1;
        Page<Activity> activities = touristService.getActiveList(pageNum);
        model.addAttribute("activies", activities);
        long pageCount = touristService.getActivitiesCount();
        model.addAttribute("lastPage", pageCount);
        return "activity_list";
    }
}
