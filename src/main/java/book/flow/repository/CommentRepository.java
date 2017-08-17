package book.flow.repository;

import book.flow.enity.Book;
import book.flow.enity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评价表的数据库相关操作.
 * User: huang
 * Date: 17-7-22
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * 删除图书评论.
     * @param bookId 图书编号
     * @return
     */
    @Transactional
    @Modifying
    @Query("delete from Comment c where c.book.bookId = ?1")
    int delCommentByBook(int bookId);

}
