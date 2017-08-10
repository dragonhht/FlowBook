package book.flow.repository;

import book.flow.enity.ChatRecord;
import book.flow.enity.User;
import book.flow.model.Message;
import book.flow.model.MsgModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description.
 * User: huang
 * Date: 17-8-9
 */
public interface ChatRecordRepository extends JpaRepository<ChatRecord, Long> {

    /**
     * 查询用户有几条信息信息.
     * @param userId 用户信息
     * @return 信息条数
     */
    @Query("select count(c) from ChatRecord c where c.receiver.userId = ?1 and c.looked = false ")
    long haveMsg(int userId);

    /**
     * 获取指定好友的用户信息.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 信息内容
     */
    @Query("select new book.flow.model.MsgModel(c.message, c.sendDate) from ChatRecord c where c.receiver.userId = ?1 and c.sender.userId = ?2" +
            " and c.looked = false order by c.sendDate asc ")
    List<MsgModel> getFriendMsg(int selfId, int friendId);

    /**
     * 获取发送给指定好友信息.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 信息内容
     */
    @Query("select new book.flow.model.MsgModel(c.message, c.sendDate) from ChatRecord c where c.sender.userId = ?1 and c.receiver.userId = ?2" +
            " and c.looked = false order by c.sendDate asc ")
    List<MsgModel> getToFriendMsg(int selfId, int friendId);

    /**
     * 获取有消息发送的好友编号.
     * @param userId 用户编号
     * @return 好友编号
     */
    @Query("select distinct c.sender.userId from ChatRecord c where c.receiver.userId = ?1 and c.looked = false ")
    List<Integer> getSenderId(int userId);


    /**
     * 获取聊天信息中不是好友的用户信息
     * @param selfId 用户编号
     * @return 非好友信息
     */
    @Query("select distinct c.sender from ChatRecord c where c.receiver.userId = ?1 or c.sender.userId = ?1 and c.looked = false " +
            "and c.sender.userId not in(?2) ")
    List<User> getNotFriend(int selfId, List<Integer> ids);

    /**
     * 将信息标为已读.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 修改记录数
     */
    @Transactional
    @Modifying
    @Query("update ChatRecord c set c.looked = true where c.receiver.userId = ?1 and c.sender.userId = ?2")
    int setChatReaded(int selfId, int friendId);

}
