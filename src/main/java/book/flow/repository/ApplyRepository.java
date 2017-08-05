package book.flow.repository;

import book.flow.enity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    List<Apply> getAllAppliesByUserId(int userId);

    /**
     * 通过用户编号获取待审批的申请.
     * @param userId 用户编号
     * @return 待审批的申请
     */
    @Query("select a from Apply a where a.user.userId = ?1 and a.status = '待审批' order by a.applyDate desc")
    List<Apply> getWaitAppliesByUserId(int userId);

    /**
     * 通过用户编号获取已审核的申请.
     * @param userId 用户编号
     * @return 已审核的申请
     */
    @Query("select a from Apply a where a.user.userId = ?1 and a.status <> '待审批' order by a.applyDate desc")
    List<Apply> getPassAppliesByUserId(int userId);
}
