package book.flow.service;

import book.flow.enity.Book;
import book.flow.enity.Notice;
import book.flow.enity.User;
import org.springframework.data.domain.Page;

/**
 * 游客操作服务层接口.
 * User: huang
 * Date: 17-7-20
 */
public interface TouristService {

    /**
     * 通过图书名查找图书.
     * @param name 图书名
     * @param pageNum 现实的当前页数
     * @return 查找到的结果
     */
    Page<Book> searchBookByBookName(String name, int pageNum);

    /**
     * 通过作者查找图书.
     * @param author 图书作者
     * @param pageNum 现实的当前页数
     * @return 查找到的结果
     */
    Page<Book> searchBookByBookAuthor(String author, int pageNum);

    /**
     * 通过出版社查找图书.
     * @param publish 出版社
     * @param pageNum 现实的当前页数
     * @return 查找到的结果
     */
    Page<Book> searchBookByBookPublish(String publish, int pageNum);

    /**
     * 通过用户名查找用户.
     * @param name 用户名
     * @param pageNum 显示的当前页数
     * @return 查找到的结果
     */
    Page<User> searchUserByName(String name, int pageNum);

    /**
     * 用户注册.
     * @param user 填写的用户信息
     * @return 保存后的用户信息
     */
    User register(User user);

    /**
     * 获取所有的公告信息.
     * @param pageNum 显示的当前页数
     * @return 公告信息
     */
    Page<Notice> getNotice(int pageNum);

    /**
     * 获取热门图书.
     * @return 热门图书
     */
    Page<Book> getHotBook();
}
