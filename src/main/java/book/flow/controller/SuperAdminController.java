package book.flow.controller;

import book.flow.enity.Apply;
import book.flow.enity.Report;
import book.flow.enity.User;
import book.flow.service.AdminService;
import book.flow.service.SuperAdminService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 超级管理员Controller.
 * User: huang
 * Date: 17-10-21
 */
@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminmanager")
    public String superAdmin() {
        return "super_admin_manager";
    }

    /**
     * 搜索用户.
     * @param target
     * @param searchText
     * @return
     */
    @PostMapping("/getuser")
    @ResponseBody
    public User getUserBy(String target, String searchText) {
        System.out.println(target+":" + searchText);
        User user = null;
        if ("userName".equals(target)) {
            user = superAdminService.searchUserByName(searchText);
        }
        if ("userId".equals(target)) {
            user = superAdminService.searchUserById(searchText);
        }
        if ("userPhone".equals(target)) {
            user = superAdminService.searchUserByPhone(searchText);
        }
        if ("userEmail".equals(target)) {
            user = superAdminService.searchUserByEmail(searchText);
        }
        return user;
    }

    @PostMapping("/setAdmin")
    @ResponseBody
    public boolean setAdmin(String userId) {
        return superAdminService.setAdmin(userId);
    }

    @PostMapping("/getAdmin")
    @ResponseBody
    public List<User> getAdmin() {
        return superAdminService.getAdmin();
    }

    @PostMapping("/delAdmin")
    @ResponseBody
    public boolean delAdmin(String userId) {
        return superAdminService.delAdmin(userId);
    }

    /**
     * 申请处理页面.
     * @return
     */
    @RequestMapping("/adminApply")
    public String adminApply(Model model) {
        List<Apply> applies = superAdminService.getAdminBookApply();
        model.addAttribute("applies", applies);
        return "super_apply";
    }

    @PostMapping("/refuseApply")
    @ResponseBody
    public boolean refuseApply(int applyId) {
        boolean ok = false;
        ok = adminService.refuseApply(applyId);
        return ok;
    }

    /**
     * 同意图书退出申请后删除图书.
     * @param applyId 图书退出申请
     * @return 结果
     */
    @PostMapping("/delBook")
    @ResponseBody
    public boolean delBook(int applyId) {
        // TODO 退出有问题
        boolean ok = false;
        adminService.delBook(applyId);
        ok = true;
        return ok;
    }

    /**
     * 跳转举报管理.
     * @return
     */
    @RequestMapping("/adminReport")
    public String adminReport(Model model) {
        List<Report> reports = adminService.getAllReport();
        List<Report> notReport = superAdminService.getAdminNotDealReport();
        model.addAttribute("reports", reports);
        model.addAttribute("notReport", notReport);
        return "super_report";
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
