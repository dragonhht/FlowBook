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
import org.springframework.web.bind.annotation.RequestParam;
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
            // 是否有未读信息
            boolean ok = false;
            long count = userService.msgCount(u.getUserId());
            if (count != 0) {
                ok = true;
            }
            session.setAttribute("haveMsg", ok);
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
        if (user != null) {
            int userId = user.getUserId();
            List<LoanRecord> nowHaveRecode = null;
            List<LoanRecord> allRecodes = null;
            user = userService.getUserById(userId);
            nowHaveRecode = userService.getHaveRecode(userId);
            allRecodes = userService.getAllRecode(userId);
            int contributeNum = user.getContribution().size() * 5;
            session.setAttribute("user", user);
            model.addAttribute("allRecodes", allRecodes);
            model.addAttribute("nowRecodes", nowHaveRecode);
            model.addAttribute("contributeNum", contributeNum);
        }
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
        int userId = user.getUserId();
        userService.addComment(text, userId, bookId);
        return "redirect:/tourist/bookMessage/" + bookId;
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
    public String saveBook(@RequestParam(value = "type") List<Integer> types, @Valid Book book, BindingResult bindingResult, Model model, HttpSession session,
                           MultipartFile uploadImg) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "upload_book";
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            Set<Type> typeSet = new HashSet<>();
            for (int type : types) {
                Type t = new Type();
                t.setTypeId(type);
                typeSet.add(t);
            }
            book.setTypes(typeSet);
            Book b = userService.uploadBook(book, userId);
            if (uploadImg.getSize() > 0) {
                String imgPath = "bookCover/" + userId + "/" + b.getBookId() + ".png";
                imgPath = fileService.store(uploadImg, imgPath);
                imgPath = "http://localhost:8080/FlowBook/" + imgPath;
                System.out.println("封面路径" + imgPath);
                userService.updateBookImg(imgPath, b.getBookId());
            }
            user = userService.getUserById(userId);
            session.setAttribute("user", user);
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
     * @param model model
     * @param session session
     * @return 我的好友页面
     */
    @RequestMapping("/userFriend")
    public String userFriend(Model model, HttpSession session) {

        List<User> friends = null;
        List<Integer> idList = null;
        List<User> notFriend = null;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            friends = userService.getFriends(selfId);
            idList = userService.getSenderId(selfId);
            notFriend = userService.getNotFriend(selfId);
        }
        model.addAttribute("notFriend", notFriend);
        model.addAttribute("senderIds", idList);
        model.addAttribute("friends", friends);
        return "user_friend";
    }

    /**
     * 添加好友.
     * @param friend 要添加的好友编号
     * @param session session
     * @return 添加结果信息
     */
    @PostMapping("/addFriend")
    @ResponseBody
    public boolean addFriend(int friend, HttpSession session) {
        boolean ok = false;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            ok = userService.addFriend(userId, friend);
        }
        return ok;
    }

    /**
     * 删除好友.
     * @param friendId 好友编号
     * @param session session
     */
    @PostMapping("/delFriend")
    @ResponseBody
    public void delFriend(int friendId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            userService.delFriend(selfId, friendId);
            userService.setChatReaded(selfId, friendId);
        }
    }

    /**
     * 将信息标为已读.
     * @param friendId 对发编号
     * @param session session
     */
    @PostMapping("/readedChat")
    @ResponseBody
    public void readedChat(int friendId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            userService.setChatReaded(selfId, friendId);
        }
    }

    /**
     * 修改用户邮箱.
     * @param oldEmail 原邮箱
     * @param newEmail 新邮箱
     * @param code 检验码
     * @param session session
     * @param model model
     * @return 结果信息
     */
    @PostMapping("/updateEmail")
    @ResponseBody
    public String updateEmail(String oldEmail, String newEmail, String code, HttpSession session, Model model) {
        if (oldEmail == null) {
            return "原邮箱不能为空";
        }
        if (newEmail == null) {
            return "新邮箱不能为空";
        }
        if (code == null) {
            return "校验码不正确";
        }
        String c = (String) session.getAttribute("checkEmailCode");
        if (code != null && !code.equals(c)) {
            return "校验码不正确";
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String email = user.getUserEmail();
            int userId= user.getUserId();
            if (!oldEmail.equals(email)) {
                return "原邮箱不正确";
            }
            if (code.equals(c)) {
                boolean ok = userService.updateUserEmail(newEmail, userId);
                if (ok) {
                    user = userService.getUserById(userId);
                    session.setAttribute("user", user);
                    return "ok";
                }
            }
        }
        return "失败";
    }

    /**
     * 检验邮箱.
     * @param email 邮箱
     * @param session session
     * @return 发送检验码是否成功
     */
    @RequestMapping("/checkEmail")
    @ResponseBody
    public boolean chackEmail(String email, HttpSession session) {
        boolean ok = false;
        String code = null;
        code = userService.checkEmail(email);
        if (code != null && code != "0") {
            ok = true;
        }
        session.setAttribute("checkEmailCode", code);
        return ok;
    }

    /**
     * 添加邮箱.
     * @param email 邮箱
     * @param code 检验码
     * @param session session
     * @return 结果
     */
    @PostMapping("/addEmail")
    @ResponseBody
    public String addEmail(String email, String code, HttpSession session) {
        if (email == null) {
            return "邮箱不能为空";
        }
        if (code == null) {
            return "检验码不能为空";
        }
        String c = (String) session.getAttribute("checkEmailCode");
        if (!code.equals(c)) {
            return "校验码不正确";
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId= user.getUserId();
            boolean ok = userService.updateUserEmail(email, userId);
            if (ok) {
                user = userService.getUserById(userId);
                session.setAttribute("user", user);
                return "ok";
            }
        }
        return "失败";
    }

}
