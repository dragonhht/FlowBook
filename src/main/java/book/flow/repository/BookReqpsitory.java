package book.flow.repository;

import book.flow.enity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Book表的数据库操作.
 * User: huang
 * Date: 17-7-19
 */
public interface BookReqpsitory extends JpaRepository<Book, Integer> {
}
