package book.flow.repository;

import book.flow.enity.Book;
import book.flow.enity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 评价表的数据库相关操作.
 * User: huang
 * Date: 17-7-22
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
