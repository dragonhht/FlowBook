package book.flow.controller;

import book.flow.enity.Apply;
import book.flow.enity.Book;
import book.flow.enity.User;
import book.flow.service.UserApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户申请控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserApplyController {

    @Autowired
    private UserApplyService userApplyService;


    /**
     * 跳转申请页面.
     * @param session session
     * @param model 用于返回数据
     * @return 申请页面
     */
    @RequestMapping("/apply")
    public String apply(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUserId();
            List<Apply> allApplies = userApplyService.getAllAppliesByUserId(userId);
            List<Apply> waitApplies = userApplyService.getWaitAppliesByUserId(userId);
            List<Apply> passApplies = userApplyService.getPassAppliesByUserId(userId);
            List<Book> canApplyBook = userApplyService.getBookToApply(userId);
            model.addAttribute("allApplies", allApplies);
            model.addAttribute("waitApplies", waitApplies);
            model.addAttribute("passApplies", passApplies);
            model.addAttribute("canApplyBook", canApplyBook);
        }
        return "user_apply";
    }

    /**
     * 申请图书退出.
     * @param bookId 图书编号
     * @param session session
     * @param model model
     * @return 信息页面
     */
    @PostMapping("/applyOut")
    public String applyBookOut(Integer bookId,
                               MultipartFile[] fileSelect, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUserId();
            List<Integer> imags = new ArrayList<>();
            for (int i = 0; i < fileSelect.length; i++) {
                int imgId = userApplyService.saveApplyImg(fileSelect[i], i, bookId, userId);
                imags.add(imgId);
            }
            boolean ok = userApplyService.applyBookOut(bookId, userId, imags);
            if (ok) {
                model.addAttribute("flag", "applySeccuss");
            }
        }
        return "msg";
    }


    /**
     * 申请图片缓存.
     * @param uploadImg 图片
     * @param index 索引
     * @param bookId 图书编号
     * @param session session
     * @return 结果
     */
    @PostMapping("/uploadApplyImg")
    @ResponseBody
    public String uploadApplyImg(MultipartFile uploadImg, int index, int bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUserId();
            int imgId = userApplyService.saveApplyImg(uploadImg, index, bookId, userId);
            return "" + imgId;
        }
        return "error";
    }

    /**
     * 通过编号获取申请.
     * @param applyId 申请编号
     * @return 申请内容
     */
    @PostMapping("/getApplyById")
    @ResponseBody
    public Apply getApplyById(int applyId) {
        Apply apply = null;
        apply = userApplyService.getApplyById(applyId);
        return apply;
    }
}
