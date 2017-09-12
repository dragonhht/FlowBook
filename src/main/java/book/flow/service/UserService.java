package book.flow.service;

import book.flow.enity.*;
import book.flow.model.MsgModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.server.LoaderHandler;

import java.util.List;

/**
 * 用户基本服务层接口.
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
    User login(String text, String password, int role);

    /**
     * 评论图书.
     * @param text 评论内容
     * @param userId 评论用户编号
     * @param bookId 评论的图书
     * @return 是否成功， true为成功
     */
    boolean addComment(String text, int userId, int bookId);

    /**
     * 保存图片路径.
     * @param img 图片路径信息
     * @return 图片路径信息
     */
    ReportImg saveImg(ReportImg img);

    /**
     * 通过编号查询用户.
     * @param userId 用户编号
     * @return 用户信息
     */
    User getUserById(int userId);

    /**
     * 查询用户唯独信息数量.
     * @param userId 用户编号
     * @return 信息数量
     */
    long msgCount(int userId);

    /**
     * 图书借阅是否满30天.
     * @param bookId 图书编号
     * @return 结果， true为满30天， 可借阅
     */
    boolean isOkToFlow(int bookId);

}
