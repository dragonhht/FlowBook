package book.flow.repository;

import book.flow.enity.Friends;
import book.flow.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description.
 * User: huang
 * Date: 17-8-8
 */
public interface FriendsRepository extends JpaRepository<Friends, Integer> {

    /**
     * 通过用户编号获取用户好友.
     * @param userId 用户编号
     * @return 好友信息
     */
    @Query("select f.friend from Friends f where f.self.userId = ?1")
    List<User> getUserFriends(int userId);

    /**
     * 通过用户编号获取用户好友编号.
     * @param userId 用户编号
     * @return 好友信息
     */
    @Query("select f.friend.userId from Friends f where f.self.userId = ?1")
    List<Integer> getUserFriendsId(int userId);

    /**
     * 判断好友是否已经添加.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 记录信息
     */
    @Query("select f from Friends f where f.self.userId = ?1 and f.friend.userId = ?2")
    Friends isFriendExist(int selfId, int friendId);

    /**
     * 删除好友.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 删除数
     */
    @Transactional
    @Modifying
    @Query("delete from Friends f where f.self.userId= ?1 and f.friend.userId = ?2")
    int delFriend(int selfId, int friendId);

}
