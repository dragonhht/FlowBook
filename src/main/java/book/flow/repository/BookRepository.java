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
public interface BookRepository extends JpaRepository<Book, Integer> {

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
     * 通过书名搜索图书获得的数量.
     * @param name 图书名
     * @return 搜索结果数量
     */
    @Query("select count(b) from Book b where b.bookName like ?1")
    long getBookCountByBookName(String name);

    /**
     * 通过图书作者搜索图书获得的数量.
     * @param author 作者名
     * @return 搜索结果的数量
     */
    @Query("select count(b) from Book b where b.author like ?1")
    long getBookCountByBookAuthor(String author);

    /**
     * 通过图书出版社搜索图书获得的数量.
     * @param publish 出版社
     * @return 搜索结果的数量
     */
    @Query("select count(b) from Book b where b.publish like ?1")
    long getBookCountByBookPublish(String publish);

    /**
     * 查询热门图书（评论最多）.
     * @return 人们图书
     */
    @Query("select b from Book b order by b.comments.size desc")
    Page<Book> getHotBooks(Pageable pageable);

    /**
     * 通过图书编号查询图书.
     * @param id 图书编号
     * @return 图书信息
     */
    @Query("select b from Book b where b.bookId = ?1")
    Book getBookById(int id);

    /**
     * 更新图书封面图片.
     * @param imgPath 图片路径
     * @param bookId 图书编号
     */
    @Query("update Book b set b.bookImg = ?1 where b.bookId = ?2")
    void updateBookImg(String imgPath, int bookId);

}
