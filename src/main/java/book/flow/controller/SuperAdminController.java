package book.flow.controller;

import book.flow.enity.User;
import book.flow.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
