package book.flow.controller;

import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.service.FileService;
import book.flow.service.UserHomeService;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户主页控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserHomeController {

    @Autowired
    private UserHomeService userHomeService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

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
            String userId = user.getUserId();
            List<LoanRecord> nowHaveRecode = null;
            List<LoanRecord> allRecodes = null;
            user = userService.getUserById(userId);
            nowHaveRecode = userHomeService.getHaveRecode(userId);
            allRecodes = userHomeService.getAllRecode(userId);
            int contributeNum = user.getContribution().size() * 5;
            session.setAttribute("user", user);
            model.addAttribute("allRecodes", allRecodes);
            model.addAttribute("nowRecodes", nowHaveRecode);
            model.addAttribute("contributeNum", contributeNum);
        }
        return "user_home";
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
            String userId= user.getUserId();
            if (!oldEmail.equals(email)) {
                return "原邮箱不正确";
            }
            if (code.equals(c)) {
                boolean ok = userHomeService.updateUserEmail(newEmail, userId);
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
        code = userHomeService.checkEmail(email);
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
            String userId= user.getUserId();
            boolean ok = userHomeService.updateUserEmail(email, userId);
            if (ok) {
                user = userService.getUserById(userId);
                session.setAttribute("user", user);
                return "ok";
            }
        }
        return "失败";
    }

    /**
     * 修改用户头像.
     * @param uploadImg 头像文件
     * @param session session
     * @return 结果
     */
    @PostMapping("/updateUserImg")
    @ResponseBody
    public String updateUserImg(MultipartFile uploadImg, HttpSession session) {
        if (uploadImg.getSize() == 0) {
            return "文件不能为空";
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUserId();
            boolean ok = false;
            String imgPath = "head_img/" + userId + "/" + userId + ".png";
            imgPath = fileService.store(uploadImg, imgPath);
            imgPath = "http://localhost:8080/FlowBook/" + imgPath;
            System.out.println("头像路径" + imgPath);
            ok = userHomeService.updateUserImg(imgPath, userId);
            if (ok) {
                return "ok";
            }
        }
        return "失败";
    }
}
