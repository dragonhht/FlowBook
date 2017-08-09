package book.flow.repository;

import book.flow.enity.ChatRecord;
import book.flow.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    @Query("select c.message from ChatRecord c where c.receiver.userId = ?1 and c.sender.userId = ?2" +
            " and c.looked = false order by c.sendDate desc")
    List<String> getFriendMsg(int selfId, int friendId);

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
    @Query("select distinct c.sender from ChatRecord c where c.receiver.userId = ?1 and c.looked = false " +
            "and c.sender.userId not in(select f.friend.userId from Friends f where f.self.userId = c.receiver.userId)")
    List<User> getNotFriend(int selfId);

}
