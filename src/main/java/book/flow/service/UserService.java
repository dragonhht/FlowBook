package book.flow.service;

import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import sun.rmi.server.LoaderHandler;

import java.util.List;

/**
 * 用户服务层接口.
 * User: huang
 * Date: 17-7-21
 */
public interface UserService {

    /**
     * 用户登录.
     * @param text 用户名/手机号/编号
     * @param password 用户密码
     * @return 数据库中的用户信息
     */
    User login(String text, String password);

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
}
