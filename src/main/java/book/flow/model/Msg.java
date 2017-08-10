package book.flow.model;

import book.flow.enity.ChatRecord;
import redis.clients.jedis.BinaryClient;

import java.io.Serializable;
import java.util.List;

/**
 * Description.
 * User: huang
 * Date: 17-8-10
 */
public class Msg implements Serializable {

    private List<ChatRecord> selfMsg;
    private List<ChatRecord> friendMsg;

    public List<ChatRecord> getSelfMsg() {
        return selfMsg;
    }

    public void setSelfMsg(List<ChatRecord> selfMsg) {
        this.selfMsg = selfMsg;
    }

    public List<ChatRecord> getFriendMsg() {
        return friendMsg;
    }

    public void setFriendMsg(List<ChatRecord> friendMsg) {
        this.friendMsg = friendMsg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "selfMsg=" + selfMsg +
                ", friendMsg=" + friendMsg +
                '}';
    }
}
