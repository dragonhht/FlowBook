package book.flow.repository;

import book.flow.enity.Apply;
import book.flow.enity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请表操作.
 * User: huang
 * Date: 17-8-5
 */
public interface ApplyRepository extends JpaRepository<Apply, Integer> {

    /**
     * 通过用户编号获取所有申请.
     * @param userId 用户编号
     * @return 用户的所有申请
     */
    @Query("select a from Apply a where a.user.userId = ?1 order by a.applyDate desc")
    List<Apply> getAllAppliesByUserId(String userId);

    /**
     * 通过用户编号获取待审批的申请.
     * @param userId 用户编号
     * @return 待审批的申请
     */
    @Query("select a from Apply a where a.user.userId = ?1 and a.status = '待审批' order by a.applyDate desc")
    List<Apply> getWaitAppliesByUserId(String userId);

    /**
     * 通过用户编号获取已审核的申请.
     * @param userId 用户编号
     * @return 已审核的申请
     */
    @Query("select a from Apply a where a.user.userId = ?1 and a.status <> '待审批' order by a.applyDate desc")
    List<Apply> getPassAppliesByUserId(String userId);

    /**
     * 通过用户编号获取用户已申请的图书.
     * @param userId 用户编号
     * @return 已申请的图书
     */
    @Query("select a.book from Apply a where a.user.userId = ?1 order by a.book.bookDate asc")
    List<Book> getBookHasApplyByUser(String userId);

    /**
     * 通过申请编号获取申请内容.
     * @param applyId 申请编号
     * @return 申请信息
     */
    @Query("select a from Apply a where a.applyId = ?1")
    Apply getApplyById(int applyId);

    /**
     * 获得所有申请.
     * @return 所有申请
     */
    @Query("select a from Apply a order by a.status asc ,a.applyDate desc ")
    List<Apply> getAllApplies();

    /**
     * 删除图书申请.
     * @param bookId 图书编号
     * @return
     */
    @Transactional
    @Modifying
    @Query("delete from Apply a where a.book.bookId = ?1")
    int delApplyByBook(int bookId);

    /**
     * 拒绝申请.
     * @param applyId 申请编号
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Apply a set a.status = '拒绝' where a.applyId = ?1")
    int refuseApply(int applyId);

    @Query("select a from Apply a where a.user.identity = 0 order by a.status asc , a.applyDate desc ")
    List<Apply> getAdminBookApply();
}
