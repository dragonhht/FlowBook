package book.flow.service;

import book.flow.enity.FlowApply;

import java.util.List;

/**
 * 用户图书传阅服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserFlowApplyService {

    /**
     * 保存传阅申请.
     * @param bookId 传阅申请编号
     * @param toUserId 接受者编号
     * @param wantSay 想说什么
     * @param userId 申请人
     * @return 是否保存成功, true为成功
     */
    boolean saveFlowApply(int bookId, String toUserId, String wantSay, String userId);

    /**
     * 获取接收方的所有申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    List<FlowApply> getFlowApplyByToUser(String toUserId);

    /**
     * 获取接收方的未回复申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    List<FlowApply> getNotLookApplyByToUser(String toUserId);

    /**
     * 获取接收方的已回复申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    List<FlowApply> getLookedApplyByToUser(String toUserId);

    /**
     * 获取正在处理的申请.
     * @param toUserId 接收方编号
     * @return 申请
     */
    List<FlowApply> getDealingApplyByToUser(String toUserId);

    /**
     * 获取我的传阅申请.
     * @param userId 用户编号
     * @return 申请
     */
    List<FlowApply> getMyFlowApplies(String userId);

    /**
     * 通过传阅申请编号查找申请.
     * @param flowBookId 申请编号
     * @return 申请
     */
    FlowApply getFlowApplyById(int flowBookId);

    /**
     * 将申请设为处理中.
     * @param flowApplyId 传阅申请编号
     * @return 结果
     */
    boolean dealFlowApply(int flowApplyId);

    /**
     * 图书传阅完成.
     * @param flowApplyId 图书传阅申请编号
     * @return 是否成功
     */
    boolean flowBookToNext(int flowApplyId);

    /**
     * 拒绝图书传阅申请.
     * @param refuse 拒绝理由
     * @param applyId 申请编号
     * @return 是否成功
     */
    boolean refuseFlowApply(String refuse, int applyId);
}
