package book.flow.controller;

import book.flow.enity.Book;
import book.flow.enity.Type;
import book.flow.enity.User;
import book.flow.service.FileService;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户新书漂流控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserUplodBookController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    /**
     * 跳转图书上传页面.
     * @return 图书上传页面
     */
    @RequestMapping("/uploadBook")
    public String uploadBook() {
        return "upload_book";
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
}
