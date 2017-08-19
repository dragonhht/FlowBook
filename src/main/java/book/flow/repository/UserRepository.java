package book.flow.repository;

import book.flow.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户表的数据库操作.
 * User: huang
 * Date: 17-7-19
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 通过用户名查找用户.
     * @param name 用户名
     * @param pageable 分页参数
     * @return 搜索到的结果
     */
    @Query("select u from User u where u.userName like ?1")
    Page<User> searchUserByName(String name, Pageable pageable);

    @Query("select count(u) from User u where u.userName like ?1")
    long getSearchUserCount(String name);

    /**
     * 用户登录查询(通过编号).
     * @param userId 用户编号
     * @param password 用户密码
     * @return 用户信息
     */
    @Query("select u from User u where u.userId = ?1 and u.password = ?2")
    User loginById(int userId, String password);

    /**
     * 用户登录查询(通过用户名).
     * @param userName 用户名
     * @param password 用户密码
     * @return 用户信息
     */
    @Query("select u from User u where u.userName = ?1 and u.password = ?2")
    User loginByName(String userName, String password);

    /**
     * 用户登录查询(通过用户手机号).
     * @param userPhone 用户手机号
     * @param password 用户密码
     * @return 用户信息
     */
    @Query("select u from User u where u.userPhone = ?1 and u.password = ?2")
    User loginByPhone(String userPhone, String password);

    /**
     * 通过编号查询用户.
     * @param id 用户编号
     * @return 用户信息
     */
    @Query("select u from User u where u.userId = ?1")
    User getUserById(int id);

    /**
     * 通过用户名获取用户.
     * @param userName 用户名
     * @return 用户信息
     */
    @Query("select u from User u where u.userName = ?1")
    User getUserByUserName(String userName);

    /**
     * 修改电子邮箱.
     * @param email 电子邮箱
     * @param userId 用户编号
     * @return 修改记录数
     */
    @Transactional
    @Modifying
    @Query("update User u set u.userEmail = ?1 where u.userId = ?2")
    int updateUserEmail(String email, int userId);

    /**
     * 更新贡献度.
     * @param userId 用户编号
     * @return 修改记录数
     */
    @Transactional
    @Modifying
    @Query("update User u set u.contributeNum = u.contributeNum + 5 where u.userId = ?1")
    int updataContributeNum(int userId);

    /**
     * 修改用户头像.
     * @param path 头像地址
     * @param userId 用户编号
     * @return 修改数
     */
    @Transactional
    @Modifying
    @Query("update User u set u.userImg = ?1 where u.userId = ?2")
    int updateUserImg(String path, int userId);

    /**
     * 获得用户头像.
     * @param userId 用户编号
     * @return 头像地址
     */
    @Query("select u.userImg from User u where u.userId = ?1")
    String getUserImg(int userId);
}
