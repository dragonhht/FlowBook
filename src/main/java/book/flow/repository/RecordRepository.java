package book.flow.repository;

import book.flow.enity.LoanRecord;
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

}
