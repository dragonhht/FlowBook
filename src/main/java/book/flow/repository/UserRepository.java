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
}
