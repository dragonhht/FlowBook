package book.flow.service;

import book.flow.enity.Apply;

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
}
