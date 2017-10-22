package book.flow.service;

import book.flow.enity.Activity;
import book.flow.enity.Apply;
import book.flow.enity.Report;

import java.util.List;

/**
 * 管理员服务接口.
 * User: huang
 * Date: 17-8-17
 */
public interface AdminService {

    /**
     * 获取所有用户申请.
     * @return 申请
     */
    List<Apply> getAllApplies();

    /**
     * 通过图书退出申请后删除图书.
     * @param applyId 图书申请编号
     */
    void delBook(int applyId);

    /**
     * 拒绝申请.
     * @param applyId 申请编号
     * @return
     */
    boolean refuseApply(int applyId);

    List<Report> getAllReport();

    /**
     * 获得所有待处理的举报.
     * @return 举报信息
     */
    List<Report> getAllWaitReport();

    /**
     * 通过编号获取举报信息.
     * @param reportId 举报编号
     * @return 举报信息
     */
    Report getReportById(int reportId);

    /**
     * 举报通过.
     * @param reportId 举报编号
     * @return
     */
    boolean passReport(int reportId);

    /**
     * 举报未通过.
     * @param reportId 举报编号
     * @return
     */
    boolean notPassReport(int reportId);

    List<Activity> getAllActivity();

    Activity getActivityById(int id);

    boolean refuseActive(int activeId);

    boolean okActive(int activeId);
}
