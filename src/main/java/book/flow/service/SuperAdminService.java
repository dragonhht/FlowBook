package book.flow.service;

import book.flow.enity.*;

import java.util.List;

/**
 * 超级管理员service接口.
 * User: huang
 * Date: 17-10-21
 */
public interface SuperAdminService {

    /**
     * 搜索用户.
     * @param text
     * @return
     */
    User searchUserById(String text);

    User searchUserByName(String text);

    User searchUserByPhone(String text);

    User searchUserByEmail(String text);

    boolean setAdmin(String userId);

    List<User> getAdmin();

    boolean delAdmin(String userId);

    List<Apply> getAdminBookApply();

    List<Report> getAdminAllReport();

    List<Report> getAdminNotDealReport();

    List<Activity> getAllActivities();

    List<ApplyAdmin> getAllApplyAdmin();

    boolean okApply(String userId, int applyId);

    boolean delApply(int applyId);

}
