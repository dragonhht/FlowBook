package book.flow.enity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户实体.
 * User: huang
 * Date: 17-7-3
 */
@Entity
@Table(name = "user")
public class User {
    /** 用户编号. */
    @Id
    @GeneratedValue
    private Integer userId;
    /** 用户名. */
    private String userName;




    /**
     * 获取 用户名.
     *
     * @return userName 用户名.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 设置 用户名.
     *
     * @param userName 用户名.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * 获取 用户编号.
     *
     * @return userId 用户编号.
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置 用户编号.
     *
     * @param userId 用户编号.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
