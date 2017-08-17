package book.flow.repository;

import book.flow.enity.FlowApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图书传阅申请表操作.
 * User: huang
 * Date: 17-8-15
 */
public interface FlowApplyRepository extends JpaRepository<FlowApply, Integer> {

    /**
     * 同意传阅请求.
     * @param flowApplyId 传阅请求编号
     * @return 修改数
     */
    @Transactional
    @Modifying
    @Query("update FlowApply f set f.status = 1 where f.id = ?1")
    int agreedApply(int flowApplyId);

    /**
     * 不同意传阅请求.
     * @param flowApplyId 传阅请求编号
     * @return 修改数
     */
    @Transactional
    @Modifying
    @Query("update FlowApply f set f.status = 2 where f.id = ?1")
    int notAgreedApply(int flowApplyId);

    /**
     * 将申请设为处理中.
     * @param flowApplyId 传阅申请编号
     * @return 修改数
     */
    @Transactional
    @Modifying
    @Query("update FlowApply f set f.status = 3 where f.id = ?1")
    int dealFlowApply(int flowApplyId);

    /**
     * 通过编号获取传阅请求.
     * @param flowApplyId 传阅申请编号
     * @return 传阅申请
     */
    @Query("select f from FlowApply f where f.id = ?1")
    FlowApply getFlowApplyById(int flowApplyId);

    /**
     * 获取接收方的所有申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    @Query("select f from FlowApply f where f.okUser.userId = ?1 order by f.applyDate desc ")
    List<FlowApply> getApplyByToUser(int toUserId);

    /**
     * 获取接收方的未回复申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    @Query("select f from FlowApply f where f.okUser.userId = ?1 and f.status = 0 order by f.applyDate desc ")
    List<FlowApply> getNotLookApplyByToUser(int toUserId);

    /**
     * 获取接收方的已回复申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    @Query("select f from FlowApply f where f.okUser.userId = ?1 and f.status <> 0 order by f.applyDate desc ")
    List<FlowApply> getLookedApplyByToUser(int toUserId);

    /**
     * 获取正在处理的申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    @Query("select f from FlowApply f where f.okUser.userId = ?1 and f.status = 3 order by f.applyDate desc")
    List<FlowApply> getDealingApplyByToUser(int toUserId);

    /**
     * 获取我的传阅申请.
     * @param userId 用户编号
     * @return 申请
     */
    @Query("select f from FlowApply f where f.applyUser.userId = ?1 order by f.applyDate desc ")
    List<FlowApply> getMyFlowApplies(int userId);

    /**
     * 删除图书漂流申请
     * @param bookId 图书编号
     * @return
     */
    @Transactional
    @Modifying
    @Query("delete from FlowApply f where f.book.bookId = ?1")
    int delFlowApplyByBook(int bookId);
}
