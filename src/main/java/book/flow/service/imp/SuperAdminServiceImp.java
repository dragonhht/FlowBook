package book.flow.service.imp;

import book.flow.enity.Apply;
import book.flow.enity.Report;
import book.flow.enity.User;
import book.flow.repository.ApplyRepository;
import book.flow.repository.ReportRepository;
import book.flow.repository.UserRepository;
import book.flow.service.SuperAdminService;
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
        boolean ok = false;
        if (num != 0) {
            ok = true;
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
        if (num > 0) {
            ok = true;
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
}
