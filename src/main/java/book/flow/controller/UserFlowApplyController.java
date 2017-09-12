package book.flow.controller;

import book.flow.enity.FlowApply;
import book.flow.enity.User;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户图书传阅控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserFlowApplyController {

    @Autowired
    private UserService userService;

    /**
     * 跳转传阅申请页面.
     * @param session session
     * @param model model
     * @return 页面
     */
    @RequestMapping("/flowApply")
    public String flowApply(HttpSession session, Model model) {
        List<FlowApply> allApplies= null;
        List<FlowApply> notApplies = null;
//        List<FlowApply> lookedApplies = null;
        List<FlowApply> dealingApplies = null;
        List<FlowApply> myApplies = null;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            allApplies = userService.getFlowApplyByToUser(userId);
            notApplies = userService.getNotLookApplyByToUser(userId);
//            lookedApplies = userService.getLookedApplyByToUser(userId);
            dealingApplies = userService.getDealingApplyByToUser(userId);
            myApplies = userService.getMyFlowApplies(userId);
        }
        model.addAttribute("allApplies", allApplies);
        model.addAttribute("notApplies", notApplies);
        model.addAttribute("dealingApplies", dealingApplies);
        model.addAttribute("myApplies", myApplies);
        return "flow_apply";
    }

    /**
     * 图书传阅申请.
     * @param bookId 申请的图书编号
     * @param toUserId 申请接收方
     * @param wantSay 想说的
     * @param session session
     * @return 提交结果
     */
    @PostMapping("/wantBook")
    @ResponseBody
    public String wantBook(int bookId, int toUserId, String wantSay, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            boolean ok = false;
            ok = userService.saveFlowApply(bookId, toUserId, wantSay, userId);
            if (ok) {
                return "ok";
            }
        }
        return "失败";
    }

    /**
     * 通过传阅申请编号查询申请.
     * @param flowApplyId 申请编号
     * @return 传阅申请
     */
    @PostMapping("/getFlowApplyById")
    @ResponseBody
    public FlowApply getFlowApplyById(int flowApplyId) {
        FlowApply apply = null;
        apply = userService.getFlowApplyById(flowApplyId);
        return apply;
    }

    /**
     * 同意传阅申请.
     * @param applyId 申请编号
     * @return 结果
     */
    @PostMapping("/applyOk")
    @ResponseBody
    public boolean applyOk(int applyId) {
        boolean ok = false;
        ok = userService.dealFlowApply(applyId);
        return ok;
    }

    /**
     * 图书传阅到下一人.
     * @param applyId 传阅申请编号
     * @return 结果
     */
    @PostMapping("/flowToNext")
    @ResponseBody
    public boolean flowToNext(int applyId) {
        boolean ok = false;
        ok = userService.flowBookToNext(applyId);
        return ok;
    }

    /**
     * 用户拒绝图书传阅.
     * @param refuse 拒绝理由
     * @param applyId 图书传阅申请编号
     * @return 是否成功
     */
    @PostMapping("/refuseFlowApply")
    @ResponseBody
    public boolean refuseFlowApply(String refuse, int applyId) {
        boolean ok = false;
        ok = userService.refuseFlowApply(refuse, applyId);
        return ok;
    }
}
