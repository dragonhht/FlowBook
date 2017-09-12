package book.flow.service;

import book.flow.enity.LoanRecord;
import java.util.List;

/**
 * 用户个人信息服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserHomeService {

    /**
     * 获取用户的所有借阅记录.
     * @param userId 用户编号
     * @return 借阅记录
     */
    List<LoanRecord> getAllRecode(int userId);

    /**
     * 获取用户所有的借出记录.
     * @param userId 用户编号
     * @return 借出记录
     */
    List<LoanRecord> getOutRecode(int userId);

    /**
     * 获取用户所有的当前拥有记录.
     * @param userId 用户编号
     * @return 当前拥有记录
     */
    List<LoanRecord> getHaveRecode(int userId);

    /**
     * 向邮箱发送校验码.
     * @param email 邮箱
     * @return 校验码
     */
    String checkEmail(String email);

    /**
     * 修改用户邮箱.
     * @param email 邮箱
     * @param userId 用户编号
     * @return 是否修改成功
     */
    boolean updateUserEmail(String email, int userId);

    /**
     * 修改用户头像.
     * @param path 头像地址
     * @param userId 用户编号
     * @return 是否修改成功, true为成功
     */
    boolean updateUserImg(String path, int userId);
}
