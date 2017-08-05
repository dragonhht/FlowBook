package book.flow.service;

import book.flow.enity.Apply;
import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * 评论图书.
     * @param text 评论内容
     * @param userId 评论用户编号
     * @param bookId 评论的图书
     * @return 是否成功， true为成功
     */
    boolean addComment(String text, int userId, int bookId);

    /**
     * 上传期望.
     * @param text 内容
     * @param userId 用户编号
     * @return 是否成功， true为成功
     */
    boolean addNotice(String text, int userId);

    /**
     * 上传图书.
     * @param book 图书信息
     * @param userId 用户编号
     * @return 保存后的图书信息
     */
    Book uploadBook(Book book, int userId);

    /**
     * 更新图书封面.
     * @param imgPath 封面路径
     * @param bookId 图书编号
     */
    void updateBookImg(String imgPath, int bookId);

    /**
     * 通过用户编号获取所有申请.
     * @param userId 用户编号
     * @return 用户的所有申请
     */
    List<Apply> getAllAppliesByUserId(int userId);

    /**
     * 通过用户编号获取待审批的申请.
     * @param userId 用户编号
     * @return 待审批的申请
     */
    List<Apply> getWaitAppliesByUserId(int userId);

    /**
     * 通过用户编号获取已审核的申请.
     * @param userId 用户编号
     * @return 已审核的申请
     */
    List<Apply> getPassAppliesByUserId(int userId);

    /**
     * 保存申请.
     * @param apply 申请信息
     * @return 是否保存成功， true为保存成功
     */
    boolean addApply(Apply apply);
}
