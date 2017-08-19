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
    private String friendImg = "";

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

    public String getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(String friendImg) {
        this.friendImg = friendImg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "selfMsg=" + selfMsg +
                ", friendMsg=" + friendMsg +
                '}';
    }
}
