package book.flow.repository;

import book.flow.enity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户表的数据库操作.
 * User: huang
 * Date: 17-7-19
 */
public interface UserRepository extends JpaRepository<User, String> {

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
    @Query("select u from User u where u.userId = ?1 and u.password = ?2 and u.identity = ?3")
    User loginById(String userId, String password, int role);

    /**
     * 用户登录查询(通过用户名).
     * @param userName 用户名
     * @param password 用户密码
     * @return 用户信息
     */
    @Query("select u from User u where u.userName = ?1 and u.password = ?2 and u.identity = ?3")
    User loginByName(String userName, String password, int role);

    /**
     * 用户登录查询(通过用户手机号).
     * @param userPhone 用户手机号
     * @param password 用户密码
     * @return 用户信息
     */
    @Query("select u from User u where u.userPhone = ?1 and u.password = ?2 and u.identity = ?3")
    User loginByPhone(String userPhone, String password, int role);

    @Query("select u from User u where u.userEmail = ?1 and u.password = ?2 and u.identity = ?3")
    User loginByEmail(String userEmail, String password, int role);

    /**
     * 通过编号查询用户.
     * @param id 用户编号
     * @return 用户信息
     */
    @Query("select u from User u where u.userId = ?1")
    User getUserById(String id);

    /**
     * 通过用户名获取用户.
     * @param userName 用户名
     * @return 用户信息
     */
    @Query("select u from User u where u.userName = ?1")
    User getUserByUserName(String userName);

    @Query("select u from User u where u.userId = ?1 and u.identity = 0")
    User getNotAdminUserById(String id);

    @Query("select u from User u where u.userName = ?1 and u.identity = 0")
    User getNotAdminUserByUserName(String userName);

    @Query("select u from User u where u.userEmail = ?1 and u.identity = 0")
    User getNotAdminUserByEmail(String email);

    @Query("select u from User u where u.userPhone = ?1 and u.identity = 0")
    User getNotAdminUserByPhone(String phone);

    /**
     * 设置管理员
     * @param userId
     * @return
     */
    @Transactional
    @Modifying
    @Query("update User u set u.identity = 1 where u.userId = ?1")
    int setAdmin(String userId);

    @Query("select u from User u where u.identity = 1 order by u.credit desc , u.contributeNum desc ")
    List<User> getAdmin();

    @Transactional
    @Modifying
    @Query("update User u set u.identity = 0 where  u.userId = ?1")
    int delAdmin(String userId);

    /**
     * 修改电子邮箱.
     * @param email 电子邮箱
     * @param userId 用户编号
     * @return 修改记录数
     */
    @Transactional
    @Modifying
    @Query("update User u set u.userEmail = ?1 where u.userId = ?2")
    int updateUserEmail(String email, String userId);

    /**
     * 更新贡献度.
     * @param userId 用户编号
     * @return 修改记录数
     */
    @Transactional
    @Modifying
    @Query("update User u set u.contributeNum = u.contributeNum + 5 where u.userId = ?1")
    int updataContributeNum(String userId);

    /**
     * 修改用户头像.
     * @param path 头像地址
     * @param userId 用户编号
     * @return 修改数
     */
    @Transactional
    @Modifying
    @Query("update User u set u.userImg = ?1 where u.userId = ?2")
    int updateUserImg(String path, String userId);

    /**
     * 获得用户头像.
     * @param userId 用户编号
     * @return 头像地址
     */
    @Query("select u.userImg from User u where u.userId = ?1")
    String getUserImg(String userId);

    @Query("select count(u) from User u where u.userPhone = ?1")
    int findUserByPhone(String phone);

    @Query("select count(u) from User u where u.userEmail = ?1")
    int findUserCountByEmail(String email);

    @Query("select count(u) from User u where u.userId = ?1 and u.userEmail = ?2")
    int isUserExistByEmail(String userId, String email);

    @Modifying
    @Transactional
    @Query("update User u set u.password = ?1 where u.userId = ?2")
    public int updatePassword(String password, String userId);
}
