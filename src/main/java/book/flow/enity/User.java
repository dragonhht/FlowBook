package book.flow.enity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 用户表.
 * User: huang
 * Date: 17-7-3
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    /** 用户编号. */
    @Id
    @GeneratedValue
    private int userId;
    /** 用户名称. */
    private String userName;
    /** 用户年龄. */
    private int userAge;
    /** 用户性别. */
    private String userSex;
    /** 住址. */
    private String userAddress;
    /** 电子邮箱. */
    private String userEmail;
    /** 电话号码. */
    private String userPhone;
    /** 用户头像. */
    private String userImg;
    /** 注册时间. */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userDate;
    /** 密码. */
    private String password;
    /** 评论. */
    @OneToMany
    @JoinColumn(name = "userId")
    private Set<Comment> comments;
    /** 图书路线. */
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "userId")
    private BookRoute bookRoute;


    /**
     * 获取 用户编号.
     *
     * @return userId 用户编号.
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * 设置 用户编号.
     *
     * @param userId 用户编号.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取 用户名称.
     *
     * @return userName 用户名称.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 设置 用户名称.
     *
     * @param userName 用户名称.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取 用户年龄.
     *
     * @return userAge 用户年龄.
     */
    public int getUserAge() {
        return this.userAge;
    }

    /**
     * 设置 用户年龄.
     *
     * @param userAge 用户年龄.
     */
    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    /**
     * 获取 用户性别.
     *
     * @return userSex 用户性别.
     */
    public String getUserSex() {
        return this.userSex;
    }

    /**
     * 设置 用户性别.
     *
     * @param userSex 用户性别.
     */
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    /**
     * 获取 住址.
     *
     * @return userAddress 住址.
     */
    public String getUserAddress() {
        return this.userAddress;
    }

    /**
     * 设置 住址.
     *
     * @param userAddress 住址.
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * 获取 电子邮箱.
     *
     * @return userEmail 电子邮箱.
     */
    public String getUserEmail() {
        return this.userEmail;
    }

    /**
     * 设置 电子邮箱.
     *
     * @param userEmail 电子邮箱.
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 获取 电话号码.
     *
     * @return userPhone 电话号码.
     */
    public String getUserPhone() {
        return this.userPhone;
    }

    /**
     * 设置 电话号码.
     *
     * @param userPhone 电话号码.
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * 获取 用户头像.
     *
     * @return userImg 用户头像.
     */
    public String getUserImg() {
        return this.userImg;
    }

    /**
     * 设置 用户头像.
     *
     * @param userImg 用户头像.
     */
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    /**
     * 获取 密码.
     *
     * @return password 密码.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置 密码.
     *
     * @param password 密码.
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 获取 注册时间.
     *
     * @return userDate 注册时间.
     */
    public Date getUserDate() {
        return this.userDate;
    }

    /**
     * 设置 注册时间.
     *
     * @param userDate 注册时间.
     */
    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    /**
     * 获取 评论.
     *
     * @return comments 评论.
     */
    public Set<Comment> getComments() {
        return this.comments;
    }

    /**
     * 设置 评论.
     *
     * @param comments 评论.
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    /**
     * 获取 图书路线.
     *
     * @return bookRoute 图书路线.
     */
    public BookRoute getBookRoute() {
        return this.bookRoute;
    }

    /**
     * 设置 图书路线.
     *
     * @param bookRoute 图书路线.
     */
    public void setBookRoute(BookRoute bookRoute) {
        this.bookRoute = bookRoute;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userSex='" + userSex + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userDate=" + userDate +
                ", password='" + password + '\'' +
                '}';
    }
}
