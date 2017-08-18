package book.flow.controller;

import book.flow.enity.Apply;
import book.flow.enity.Report;
import book.flow.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 管理员操作控制器.
 * User: huang
 * Date: 17-8-17
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 跳转管理员审核界面.
     * @param model model
     * @return 页面
     */
    @RequestMapping("/adminAgree")
    public String adminAgree(Model model) {
        List<Apply> allBookApplies = null;
        List<Report> reports = null;
        allBookApplies = adminService.getAllApplies();
        reports = adminService.getAllReport();

        model.addAttribute("allBookApplies", allBookApplies);
        model.addAttribute("reports", reports);
        return "admin_agree";
    }

    /**
     * 同意图书退出申请后删除图书.
     * @param applyId 图书退出申请
     * @return 结果
     */
    @PostMapping("/delBook")
    @ResponseBody
    public boolean delBook(int applyId) {
        boolean ok = false;
        adminService.delBook(applyId);
        ok = true;
        return ok;
    }

    /**
     * 拒绝申请.
     * @param applyId
     * @return
     */
    @PostMapping("/refuseApply")
    @ResponseBody
    public boolean refuseApply(int applyId) {
        boolean ok = false;
        ok = adminService.refuseApply(applyId);
        return ok;
    }

    /**
     * 通过编号获取举报.
     * @param reportId 编号
     * @return 举报信息
     */
    @PostMapping("/getReportById")
    @ResponseBody
    public Report getReportById(int reportId) {
        Report report = null;
        report = adminService.getReportById(reportId);
        return report;
    }

    @PostMapping("/punishReport")
    @ResponseBody
    public boolean punishReport(int reportId) {
        boolean ok = false;
        // TODO 惩罚用户
        ok = adminService.passReport(reportId);
        System.out.println("惩罚用户");
        return ok;
    }

    @PostMapping("/notPunishReport")
    @ResponseBody
    public boolean notPunishReport(int reportId) {
        boolean ok = false;
        ok = adminService.notPassReport(reportId);
        return ok;
    }
}
