package book.flow.service.imp;

import book.flow.enity.*;
import book.flow.repository.*;
import book.flow.service.SuperAdminService;
import book.flow.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 超级管理员service实现类.
 * User: huang
 * Date: 17-10-21
 */
@Service
public class SuperAdminServiceImp implements SuperAdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ApplyAdminRepository applyAdminRepository;


    @Override
    public User searchUserById(String text) {
        return userRepository.getNotAdminUserById(text);
    }

    @Override
    public User searchUserByName(String text) {
        return userRepository.getNotAdminUserByUserName(text);
    }

    @Override
    public User searchUserByPhone(String text) {
        return userRepository.getNotAdminUserByPhone(text);
    }

    @Override
    public User searchUserByEmail(String text) {
        return userRepository.getNotAdminUserByEmail(text);
    }

    @Override
    public boolean setAdmin(String userId) {
        int num = 0;
        num = userRepository.setAdmin(userId);
        User user = userRepository.getUserById(userId);
        boolean ok = false;
        if (num != 0) {
            ok = true;
            MailUtils mailUtils = MailUtils.getMailUtils();
            String text = "<p>尊敬的 " + user.getUserName() + "，您好，您已成功成为图书漂流平台的管理员！</p>";
            mailUtils.sendMailByQQ(user.getUserEmail(), "九江学院图书漂流平台管理员提醒", text);
        }
        return ok;
    }

    @Override
    public List<User> getAdmin() {
        return userRepository.getAdmin();
    }

    @Override
    public boolean delAdmin(String userId) {
        boolean ok = false;
        int num = userRepository.delAdmin(userId);
        User user = userRepository.getUserById(userId);
        if (num > 0) {
            ok = true;
            MailUtils mailUtils = MailUtils.getMailUtils();
            String text = "<p>尊敬的 " + user.getUserName() + "，您好，因某些原因您已被撤销图书漂流平台的管理员身份！</p>";
            mailUtils.sendMailByQQ(user.getUserEmail(), "九江学院图书漂流平台管理员提醒", text);
        }
        return ok;
    }

    @Override
    public List<Apply> getAdminBookApply() {
        return applyRepository.getAdminBookApply();
    }

    @Override
    public List<Report> getAdminAllReport() {
        return reportRepository.getAdminReport();
    }

    @Override
    public List<Report> getAdminNotDealReport() {
        return reportRepository.getAdminNotDealReport();
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.getAdminAllActivies();
    }

    @Override
    public List<ApplyAdmin> getAllApplyAdmin() {
        return applyAdminRepository.getAllApplies();
    }

    @Override
    public boolean okApply(String userId, int applyId) {
        boolean ok = false;
        int num = applyAdminRepository.okApply(applyId);
        if (num > 0) {
            ok = setAdmin(userId);
        }
        return ok;
    }

    @Override
    public boolean delApply(int applyId) {
        boolean ok = false;
        int num = applyAdminRepository.delApply(applyId);
        if (num > 0) {
            ok = true;
        }
        return ok;
    }
}
