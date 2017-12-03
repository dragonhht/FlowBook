package book.flow.service;

import book.flow.enity.*;
import org.springframework.data.domain.Page;
import java.util.List;

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
     * 通过书名搜索图书获得的数量.
     * @param name 图书名
     * @return 搜索结果数量
     */
    long getBookCountByBookName(String name);

    /**
     * 通过图书作者搜索图书获得的数量.
     * @param author 作者名
     * @return 搜索结果的数量
     */
    long getBookCountByBookAuthor(String author);

    /**
     * 通过图书出版社搜索图书获得的数量.
     * @param publish 出版社
     * @return 搜索结果的数量
     */
    long getBookCountByBookPublish(String publish);

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
     * 获取首页需显示的公告信息.
     * @return 公告信息
     */
    Page<Notice> getIndexNotice();

    /**
     * 获取热门图书.
     * @return 热门图书
     */
    Page<Book> getHotBook(int size);

    /**
     * 通过图书编号查询图书.
     * @param id 图书编号
     * @return 图书信息
     */
    Book getBookById(int id);

    /**
     * 通过编号查询用户.
     * @param id 用户编号
     * @return 用户信息
     */
    User getUserById(String id);

    /**
     * 通过用户名判断用户是否存在.
     * @param userName 用户名
     * @return 用户是否存在，true为存在
     */
    boolean isUserNotExist(String userName);

    /**
     * 通过图书编号获取图书当前的拥有者.
     * @param bookId 图书编号
     * @return 用户信息
     */
    User getNowOwner(int bookId);

    /**
     * 通过图书查询图书记录.
     * @param bookId 图书编号
     * @return 图书记录
     */
    List<LoanRecord> getRecordByBookId(int bookId);

    /**
     * 获取公告总数.
     * @return 公告数量
     */
    long getNoticesPageCount();

    /**
     * 通过用户编号查询所有记录.
     * @param userId 用户编号
     * @return 用户的借阅记录
     */
    List<LoanRecord> getRecordsByUserId(String userId);

    /**
     * 获取通过用户名查询到的分页页数.
     * @param name 用户名
     * @return 页数
     */
    long getSearchUserPageSize(String name);

    /**
     * 判断好友是否已经添加.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 是否已经添加， true为已添加
     */
    boolean isFriendExist(String selfId, String friendId);

    /**
     * 通过类型查找图书.
     * @param typeId 类型编号
     * @param pageNum 分页所看页数
     * @return 图书信息
     */
    List<Book> getBookByTypeId(int typeId, int pageNum);

    /**
     * 通过类型查找图书总数量.
     * @param typeId 类型编号
     * @return 图书信息总数量
     */
    long getBookByTypeIdCount(int typeId);

    Page<Activity> getActivity();

    Activity getActivityById(int activeId);

    Page<Activity> getActiveList(int pageNum);

    long getActivitiesCount();

    Page<Book> filterSearchBookByName(String name, int types, int pageNum);

    Page<Book> filterSearchBookByAuthor(String name, int types, int pageNum);

    Page<Book> filterSearchBookByPublish(String name, int types, int pageNum);

    Page<User> orderUserSearch(String name, String types, String order, int pageNum);

    Page<Book> getGoodBook(int size);

    boolean isExistPhone(String phone);

    boolean isExistEmail(String email);
}
