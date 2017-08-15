package book.flow.repository;

import book.flow.enity.FlowApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
     * 通过编号获取传阅请求.
     * @param flowApplyId 传阅申请编号
     * @return 传阅申请
     */
    @Query("select f from FlowApply f where f.id = ?1")
    FlowApply getFlowApplyById(int flowApplyId);


}
