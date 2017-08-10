package book.flow.enity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 好友表.
 * User: huang
 * Date: 17-8-8
 */
@Entity
@Table(name = "friends")
public class Friends implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    /** 好友. */
    @ManyToOne
    @JoinColumn(name = "friendId")
    private User friend;
    /** 自身. */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User self;

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
