package book.flow.controller;

import book.flow.enity.*;
import book.flow.service.FileService;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private FileService fileService;

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
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户不存在或密码错误");
            return "login";
        }
    }

    /**
     * 跳转到用户主页.
     * @param model 用于返回数据
     * @param session session
     * @return 用户主页
     */
    @RequestMapping("/userHome")
    public String userHome(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();
        List<LoanRecord> nowHaveRecode = null;
        List<LoanRecord> allRecodes = null;
        nowHaveRecode = userService.getHaveRecode(userId);
        allRecodes = userService.getAllRecode(userId);
        model.addAttribute("allRecodes", allRecodes);
        model.addAttribute("nowRecodes", nowHaveRecode);
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
        int userId = user.getUserId();
        userService.addComment(text, userId, bookId);
        return "redirect:/tourist//bookMessage/" + bookId;
    }

    /**
     * 跳转图书上传页面.
     * @return 图书上传页面
     */
    @RequestMapping("/uploadBook")
    public String uploadBook() {
        return "upload_book";
    }

    /**
     * 跳转上传期望页面.
     * @return 上传页面
     */
    @RequestMapping("/uploadNotice")
    public String uploadNotice() {
        return "upload_notice";
    }

    /**
     * 跳转申请.
     * @param session session
     * @param model 用于返回数据
     * @return 申请页面
     */
    @RequestMapping("/apply")
    public String apply(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            List<Apply> allApplies = userService.getAllAppliesByUserId(userId);
            List<Apply> waitApplies = userService.getWaitAppliesByUserId(userId);
            List<Apply> passApplies = userService.getPassAppliesByUserId(userId);
            List<Book> canApplyBook = userService.getBookToApply(userId);
            model.addAttribute("allApplies", allApplies);
            model.addAttribute("waitApplies", waitApplies);
            model.addAttribute("passApplies", passApplies);
            model.addAttribute("canApplyBook", canApplyBook);
        }
        return "book_apply";
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
        int userId = 0;
        if (user != null) {
            userId = user.getUserId();
        }
        boolean ok = false;
        ok = userService.addNotice(text, userId);
        if (ok) {
            return "上传成功";
        } else {
            return "上传失败";
        }
    }

    /**
     * 上传图书.
     * @param book 图书信息
     * @param bindingResult 校验信息
     * @param model model
     * @param session session
     * @param uploadImg 上传的图书封面
     * @return 相应的结果页面
     */
    @PostMapping("/saveBook")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model, HttpSession session,
                           MultipartFile uploadImg) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "upload_book";
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            Book b = userService.uploadBook(book, userId);
            String imgPath = "bookCover/" + userId + "/" + b.getBookId() + ".png";
            imgPath = fileService.store(uploadImg, imgPath);
            imgPath = "http://localhost:8080/FlowBook/files/" + imgPath;
            System.out.println("封面路径" + imgPath);
            userService.updateBookImg(imgPath, b.getBookId());
            session.setAttribute("user", b);
            model.addAttribute("bookId", b.getBookId());
            model.addAttribute("flag", "uploadBookSuccess");
            return "msg";
        }
        return "redirect:/500";
    }

    @PostMapping("/applyOut")
    public String applyBookOut(Integer bookId, MultipartFile[] imgs, HttpSession session, Model model) {
        System.out.println(bookId);
        User user = (User) session.getAttribute("user");
        StringBuilder imgPath = new StringBuilder("bookCover/apply/");
        if (user != null) {
            int userId = user.getUserId();
            boolean ok = userService.applyBookOut(bookId, userId, imgs);
            if (ok) {
                model.addAttribute("flag", "applySeccuss");
            }
        }
        return "msg";
    }

    /**
     * 跳转到我的好友页面.
     * @return 我的好友页面
     */
    @RequestMapping("/userFriend")
    public String userFriend(Model model) {
        model.addAttribute("searchText", "test");
        return "user_friend";
    }


}
