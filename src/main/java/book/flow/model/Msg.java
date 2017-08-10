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

    private List<MsgModel> selfMsg;
    private List<MsgModel> friendMsg;

    public List<MsgModel> getSelfMsg() {
        return selfMsg;
    }

    public void setSelfMsg(List<MsgModel> selfMsg) {
        this.selfMsg = selfMsg;
    }

    public List<MsgModel> getFriendMsg() {
        return friendMsg;
    }

    public void setFriendMsg(List<MsgModel> friendMsg) {
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
