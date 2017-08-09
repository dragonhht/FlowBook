package book.flow.service;

import book.flow.enity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;
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

    /**
     * 通过用户查询可申请的书籍.
     * @param userId 用户编号
     * @return 可申请的书籍
     */
    List<Book> getBookToApply(int userId);

    /**
     * 保存图书退出申请.
     * @param bookId 图书编号
     * @param userId 用户编号
     * @param imgs 图片
     * @return 是否保存成功， true为成功
     */
    boolean applyBookOut(int bookId, int userId, MultipartFile[] imgs);

    /**
     * 保存图片路径.
     * @param img 图片路径信息
     * @return 图片路径信息
     */
    Img saveImg(Img img);

    /**
     * 添加好友.
     * @param selfId 用户编号
     * @param friendId 对方编号
     * @return 是否添加成功
     */
    boolean addFriend(int selfId, int friendId);

    /**
     * 判断好友是否已经添加.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 是否已经添加， true为已添加
     */
    boolean isFriendExist(int selfId, int friendId);

    /**
     * 获取好友.
     * @param selfId 用户编号
     * @return 好友信息
     */
    List<User> getFriends(int selfId);

    /**
     * 通过编号查询用户.
     * @param userId 用户编号
     * @return 用户信息
     */
    User getUserById(int userId);

    /**
     * 保存聊天信息.
     * @param record 聊天信息
     * @return 是否保存成功， true为保存成功
     */
    boolean addChatRecord(ChatRecord record);

    /**
     * 查询用户唯独信息数量.
     * @param userId 用户编号
     * @return 信息数量
     */
    long msgCount(int userId);

    /**
     * 获取发送消息的用户的编号.
     * @param userId 用户编号
     * @return 发送消息的用户编号
     */
    List<Integer> getSenderId(int userId);

    /**
     * 获取指定好友的信息.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 信息
     */
    List<String> getFriendMsg(int selfId, int friendId);
}
