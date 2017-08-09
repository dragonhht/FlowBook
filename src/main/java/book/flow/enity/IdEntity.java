package book.flow.enity;

import java.io.Serializable;

/**
 * 双属性主键id类.
 * User: huang
 * Date: 17-8-8
 */
public class IdEntity implements Serializable {
   private User self;
   private User friend;

    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
