package book.flow.controller;

import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.service.TouristService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    @Autowired
    DefaultKaptcha defaultKaptcha;

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
    public String register(@Valid User user, BindingResult bindingResult,
                           String verificationCode, Model model, HttpSession session){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "register";
        }

        // 校验图片验证码
        String captchaId = (String) session.getAttribute("vrifyCode");

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

    /**
     * 验证码.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/captcha_image")
    public void getKaptchaImage(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("vrifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 获取图书详细信息.
     * @param bookId 图书编号
     * @param model 用于返回数据
     * @return 详细信息页面
     */
    @RequestMapping("/bookMessage/{bookId}")
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
    public String userMessage(@PathVariable int userId, Model model) {
        User user = null;
        user= touristService.getUserById(userId);
        model.addAttribute("user", user);
        return "user";
    }

}
