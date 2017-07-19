package book.flow.repository;

import book.flow.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户表的数据库操作.
 * User: huang
 * Date: 17-7-19
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
