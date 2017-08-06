package book.flow.repository;

import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 借阅记录操作.
 * User: huang
 * Date: 17-7-22
 */
public interface RecordRepository extends JpaRepository<LoanRecord, Integer> {

    /**
     * 通过用户编号获取用户的借阅记录.
     * @param userId 用户编号
     * @return 借阅信息
     */
    @Query("select r from LoanRecord r where r.user.userId = ?1 order by r.recordDate desc ")
    List<LoanRecord> getRecodeByUserId(int userId);

    /**
     * 通过用户编号获取用户现在拥有的书籍.
     * @param userId 用户编号
     * @return 现有书籍记录
     */
    @Query("select r from LoanRecord r where r.user.userId = ?1 and r.isOut = false order by r.recordDate desc ")
    List<LoanRecord> getHaveRecodeByUserId(int userId);

    /**
     * 通过用户编号获取用户借出的书籍.
     * @param userId 用户编号
     * @return 借出的书籍记录
     */
    @Query("select r from LoanRecord r where r.user.userId = ?1 and r.isOut = true order by r.recordDate desc ")
    List<LoanRecord> getOutRecodeByUserId(int userId);

    /**
     * 通过图书查询记录.
     * @param bookId 图书编号
     * @return 图书记录
     */
    @Query("select r from LoanRecord r where r.book.bookId = ?1 order by r.recordDate desc ")
    List<LoanRecord> getRecordByBookId(int bookId);

    /**
     * 查询图书的当前拥有者.
     * @param bookId 图书编号
     * @return 用户信息
     */
    @Query("select r.user from LoanRecord r where r.book.bookId = ?1 and r.isOut = false ")
    User getNowOwnerByBookId(int bookId);

    /**
     * 通过用户编号获取用户可申请的图书.
     * @param userId 用户编号
     * @return 可申请的图书
     */
    @Query("select r.book from LoanRecord r where r.user.userId = ?1 and " +
            "r.book not in(select a.book from Apply a where r.user.userId = a.user.userId and a.status <> '待审批' and r.isOut = false)")
    List<Book> getBookToApplyByUser(int userId);

}
