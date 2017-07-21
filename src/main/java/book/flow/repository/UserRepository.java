package book.flow.repository;

import book.flow.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    public Page<User> searchUserByName(String name, Pageable pageable);

    /**
     * 用户登录查询.
     * @param userId 用户编号
     * @param password 用户密码
     * @return 用户信息
     */
    @Query("select u from User u where u.userId = ?1 and u.password = ?2")
    public User login(int userId, String password);
}
