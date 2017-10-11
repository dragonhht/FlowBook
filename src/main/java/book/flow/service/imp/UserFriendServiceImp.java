package book.flow.service.imp;

import book.flow.enity.ChatRecord;
import book.flow.enity.Friends;
import book.flow.enity.User;
import book.flow.model.MsgModel;
import book.flow.repository.ChatRecordRepository;
import book.flow.repository.FriendsRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户好友功能服务实现.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserFriendServiceImp implements UserFriendService {

    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRecordRepository chatRecordRepository;

    @Override
    public boolean addFriend(String selfId, String friendId) {
        boolean ok = false;
        User self = userRepository.getUserById(selfId);
        User friend = userRepository.getUserById(friendId);
        Friends friends = new Friends();
        friends.setFriend(friend);
        friends.setSelf(self);
        Friends f = friendsRepository.save(friends);
        if (f != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean isFriendExist(String selfId, String friendId) {
        boolean ok = false;
        Friends friend = friendsRepository.isFriendExist(selfId, friendId);
        if (friend != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public List<User> getFriends(String selfId) {
        List<User> friends = null;
        friends = friendsRepository.getUserFriends(selfId);
        return friends;
    }

    @Override
    public List<Integer> getFriendsId(String selfId) {
        List<Integer> ids = chatRecordRepository.getSenderId(selfId);
        if (ids == null) {
            ids = new ArrayList<>();
        }
        return ids;
    }

    @Override
    public boolean addChatRecord(ChatRecord record) {
        boolean ok = false;
        ChatRecord r = chatRecordRepository.save(record);
        if (r != null) {
            ok = true;
        }
        return ok;
    }



    @Override
    public List<Integer> getSenderId(String userId) {
        List<Integer> idList = null;
        idList = chatRecordRepository.getSenderId(userId);
        return idList;
    }

    @Override
    public List<MsgModel> getFriendMsg(String selfId, String friendId) {
        List<MsgModel> messages = null;
        messages = chatRecordRepository.getFriendMsg(selfId, friendId);
        return messages;
    }


    @Override
    public List<MsgModel> getToFriendMsg(String selfId, String friendId) {
        List<MsgModel> messages = null;
        messages = chatRecordRepository.getToFriendMsg(selfId, friendId);
        return messages;
    }

    @Override
    public List<User> getNotFriend(String selfId) {
        List<User> users = null;
        List<String> ids = friendsRepository.getUserFriendsId(selfId);
        users = chatRecordRepository.getNotFriend(selfId, ids);
        return users;
    }

    @Override
    public boolean setChatReaded(String selfId, String friendId) {
        boolean ok = false;
        int c = 0;
        c = chatRecordRepository.setChatReaded(selfId, friendId);
        if (c != 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean delFriend(String selfId, String friendId) {
        boolean ok = false;
        int l = 0;
        l = friendsRepository.delFriend(selfId, friendId);
        if (l != 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public String getUserImg(String userId) {
        String s = "";
        s= userRepository.getUserImg(userId);
        return s;
    }
}
