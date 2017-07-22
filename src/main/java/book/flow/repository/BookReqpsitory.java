package book.flow.repository;

import book.flow.enity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Book表的数据库操作.
 * User: huang
 * Date: 17-7-19
 */
public interface BookReqpsitory extends JpaRepository<Book, Integer> {

    /**
     * 通过书名搜索图书.
     * @param name 图书名
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Query("select b from Book b where b.bookName like ?1")
    Page<Book> searchBookByBookName(String name, Pageable pageable);

    /**
     * 通过图书作者搜索图书.
     * @param author 作者名
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Query("select b from Book b where b.author like ?1")
    Page<Book> searchBookByBookAuthor(String author, Pageable pageable);

    /**
     * 通过图书出版社搜索图书.
     * @param publish 出版社
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Query("select b from Book b where b.publish like ?1")
    Page<Book> searchBookByBookPublish(String publish, Pageable pageable);

    /**
     * 查询热门图书（评论最多）.
     * @return 人们图书
     */
    @Query("select c.book from Comment c group by c.book order by count(c.book) desc")
    Page<Book> getHotBooks(Pageable pageable);
}
