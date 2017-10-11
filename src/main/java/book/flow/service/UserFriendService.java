package book.flow.service;

import book.flow.enity.ChatRecord;
import book.flow.enity.User;
import book.flow.model.MsgModel;

import java.util.List;

/**
 * 用户好友功能服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserFriendService {

    /**
     * 添加好友.
     * @param selfId 用户编号
     * @param friendId 对方编号
     * @return 是否添加成功
     */
    boolean addFriend(String selfId, String friendId);

    /**
     * 判断好友是否已经添加.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 是否已经添加， true为已添加
     */
    boolean isFriendExist(String selfId, String friendId);

    /**
     * 获取好友.
     * @param selfId 用户编号
     * @return 好友信息
     */
    List<User> getFriends(String selfId);

    /**
     * 获取好友编号.
     * @param selfId 用户编号
     * @return 好友信息
     */
    List<Integer> getFriendsId(String selfId);

    /**
     * 保存聊天信息.
     * @param record 聊天信息
     * @return 是否保存成功， true为保存成功
     */
    boolean addChatRecord(ChatRecord record);



    /**
     * 获取发送消息的用户的编号.
     * @param userId 用户编号
     * @return 发送消息的用户编号
     */
    List<Integer> getSenderId(String userId);

    /**
     * 获取指定好友的信息.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 信息
     */
    List<MsgModel> getFriendMsg(String selfId, String friendId);


    /**
     * 获取发送给制定好友的信息.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 信息
     */
    List<MsgModel> getToFriendMsg(String selfId, String friendId);

    /**
     * 获取聊天信息中非好友用户
     * @param selfId 用户编号
     * @return 非好友信息
     */
    List<User> getNotFriend(String selfId);

    /**
     * 将信息标为已读.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 是否成功， true为成功
     */
    boolean setChatReaded(String selfId, String friendId);

    /**
     * 删除好友.
     * @param selfId 用户编号
     * @param friendId 好友编号
     * @return 是否成功， true为成功
     */
    boolean delFriend(String selfId, String friendId);


    /**
     * 获得用户头像.
     * @param userId 用户编号
     * @return 头像地址
     */
    String getUserImg(String userId);

}
