package book.flow.controller;

import book.flow.enity.User;
import book.flow.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;

/**
 * 用户举报控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    /**
     * 上传举报图片.
     * @param index 图片序号
     * @param reportedId 被举报人编号
     * @param img 图片
     * @param session session
     * @return 结果信息
     */
    @PostMapping("/uploadReportImg")
    @ResponseBody
    public String uploadReportImg(int index, String reportedId, MultipartFile img , HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUserId();
            int imgId = userReportService.saveReportImg(index, reportedId, userId, img);
            return String.valueOf(imgId);
        }
        return "error";
    }

    /**
     * 保存举报信息.
     * @param informants 被举报者编号
     * @param reportText 举报内容
     * @param imgs 图片
     * @param session session
     * @return 是否成功
     */
    @PostMapping("/saveReport")
    @ResponseBody
    public boolean saveReport(String informants, String reportText, String[] imgs, HttpSession session) {
        boolean ok = false;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String userId = user.getUserId();
            ok = userReportService.saveReport(userId, informants, reportText, imgs);
        }
        return ok;
    }
}
