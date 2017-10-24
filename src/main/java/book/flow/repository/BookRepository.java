package book.flow.repository;

import book.flow.enity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

//    @Query("select b from Book b ")
//    Page<Book> searchBookByBookName(String name, int type, Pageable pageable);

    @Query("select b from Book b left outer join fetch b.types t where b.bookName like ?1 and t.typeId = ?2")
    List<Book> searchBookByBookName(String name, int type);

    @Query("select b from Book b left outer join fetch b.types t where b.bookName like ?1 and t.typeId = ?2 and t.typeId = ?3")
    List<Book> searchBookByBookName(String name, int type_0, int type_1);

    @Query("select b from Book b left outer join fetch b.types t where b.bookName like ?1 and t.typeId = ?2 and t.typeId = ?3 and t.typeId = ?4")
    List<Book> searchBookByBookName(String name, int type_0, int type_1, int type_2);

    /**
     * 通过图书作者搜索图书.
     * @param author 作者名
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Query("select b from Book b where b.author like ?1")
    Page<Book> searchBookByBookAuthor(String author, Pageable pageable);

    @Query("select b from Book b left outer join fetch b.types t where b.author like ?1 and t.typeId = ?2")
    List<Book> searchBookByBookAuthor(String author, int type);

    @Query("select b from Book b left outer join fetch b.types t where b.author like ?1 and t.typeId = ?2 and t.typeId = ?3")
    List<Book> searchBookByBookAuthor(String author, int type_0, int type_1);

    @Query("select b from Book b left outer join fetch b.types t where b.author like ?1 and t.typeId = ?2 and t.typeId = ?3 and t.typeId = ?4")
    List<Book> searchBookByBookAuthor(String author, int type_0, int type_1, int type_2);

    /**
     * 通过图书出版社搜索图书.
     * @param publish 出版社
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @Query("select b from Book b where b.publish like ?1")
    Page<Book> searchBookByBookPublish(String publish, Pageable pageable);

    @Query("select b from Book b left outer join fetch b.types t where b.publish like ?1 and t.typeId = ?2")
    List<Book> searchBookByBookPublish(String publish, int type);

    @Query("select b from Book b left outer join fetch b.types t where b.publish like ?1 and t.typeId = ?2 and t.typeId = ?3")
    List<Book> searchBookByBookPublish(String publish, int type_0, int type_1);

    @Query("select b from Book b left outer join fetch b.types t where b.publish like ?1 and t.typeId = ?2 and t.typeId = ?3 and t.typeId = ?3")
    List<Book> searchBookByBookPublish(String publish, int type_0, int type_1, int type_2);


    /**
     * 通过书名搜索图书获得的数量.
     * @param name 图书名
     * @return 搜索结果数量
     */
    @Query("select count(b) from Book b where b.bookName like ?1")
    long getBookCountByBookName(String name);

    @Query("select count(b) from Book b where b.bookName like ?1 and b.types = ?2")
    long getBookCountByBookName(String name, int type);

    @Query("select count(b) from Book b where b.bookName like ?1 and b.types = ?2 and b.types = ?3")
    long getBookCountByBookName(String name, int type_0, int type_1);

    @Query("select count(b) from Book b where b.bookName like ?1 and b.types = ?2 and b.types = ?3 and b.types = ?4")
    long getBookCountByBookName(String name, int type_0, int type_1, int type_2);


    /**
     * 通过图书作者搜索图书获得的数量.
     * @param author 作者名
     * @return 搜索结果的数量
     */
    @Query("select count(b) from Book b where b.author like ?1")
    long getBookCountByBookAuthor(String author);

    @Query("select count(b) from Book b where b.author like ?1 and b.types = ?2")
    long getBookCountByBookAuthor(String author, int type);

    @Query("select count(b) from Book b where b.author like ?1 and b.types = ?2 and b.types = ?3")
    long getBookCountByBookAuthor(String author, int type_0, int type_1);

    @Query("select count(b) from Book b where b.author like ?1 and b.types = ?2 and b.types = ?3 and b.types = ?4")
    long getBookCountByBookAuthor(String author, int type_0, int type_1, int type_2);

    /**
     * 通过图书出版社搜索图书获得的数量.
     * @param publish 出版社
     * @return 搜索结果的数量
     */
    @Query("select count(b) from Book b where b.publish like ?1")
    long getBookCountByBookPublish(String publish);

    @Query("select count(b) from Book b where b.publish like ?1 and b.types = ?2")
    long getBookCountByBookPublish(String publish, int type);

    @Query("select count(b) from Book b where b.publish like ?1 and b.types = ?2 and b.types = ?3")
    long getBookCountByBookPublish(String publish, int type_0, int type_1);

    @Query("select count(b) from Book b where b.publish like ?1 and b.types = ?2 and b.types = ?3 and b.types = ?4")
    long getBookCountByBookPublish(String publish, int type_0, int type_1, int type_2);

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
    @Transactional
    @Modifying
    @Query("update Book b set b.bookImg = ?1 where b.bookId = ?2")
    void updateBookImg(String imgPath, int bookId);

    /**
     * 通过类型查询图书.
     * @param typeId 图书类型
     * @param start 结果开始
     * @param size 结果个数
     * @return 图书
     */
    @Query(nativeQuery = true, value = "SELECT b.* FROM book b, book_type bt" +
            " where b.book_id = bt.book_id and bt.type_id = ?1 limit ?2, ?3")
    List<Book> getBookByTypeId1(int typeId, int start, int size);

    /**
     * 计算通过类型获取道德图书总数.
     * @param typeId 图书类型
     * @return 总数
     */
    @Query(nativeQuery = true, value = "SELECT COUNT(b.book_id) FROM book b, book_type bt" +
            " where b.book_id = bt.book_id and bt.type_id = ?1")
    long getBookByTypeCount(int typeId);

    /**
     * 删除图书.
     * @param bookId 图书编号
     * @return 结果数
     */
    @Transactional
    @Modifying
    @Query("delete from Book b where b.bookId = ?1")
    int delBookById(int bookId);

    @Query("select b from Book b order by b.records.size desc ")
    Page<Book> getGoodBook(Pageable pageable);
}
