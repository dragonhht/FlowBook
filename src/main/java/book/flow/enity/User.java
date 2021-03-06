package book.flow.enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
    private String userId;
    /** 用户名称. */
    @Size(min = 1, max = 30, message = "{user.name.length.error}")
    private String userName;
//    /** 用户年龄. */
//    private int userAge;
//    /** 用户性别. */
//    private String userSex;
//    /** 住址. */
//    private String userAddress;
    /** 电子邮箱. */
    @Email(message = "{user.email.format.error}")
    private String userEmail;
    /** 电话号码. */
    //@Pattern(regexp = "/^1[3|4|5|8][0-9]\\d{4,8}$/", message = "{user.phone.format.error}")
    private String userPhone;
    /** 用户头像. */
    private String userImg = "http://localhost:8080/FlowBook/img/user.png";
//    /** 省份. */
//    private String provice;
//    /** 城市. */
//    private String city;
    /** 身份. */
    private int identity = 0;
    /** 注册时间. */
    @Temporal(TemporalType.DATE)
    private Date userDate;
    /** 密码. */
    @Size(min = 1, message = "{user.password.length.error}")
    @JsonIgnore
    private String password;
    /** 评论. */
    @OneToMany
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Set<Comment> comments;
    /** 管理员申请. */
    @OneToMany
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Set<ApplyAdmin> applyAdmins;
//    /** 图书路线. */
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userId")
//    @JsonBackReference
//    private Set<BookRoute> bookRoute;
    /** 发布的公告. */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @OrderBy("noticeDate desc")
    @JsonBackReference
    private Set<Notice> notices;
    /** 贡献的图书. */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Set<Book> contribution;
    /** 贡献度. */
    private Integer contributeNum = 0;
    /** 信用度. */
    private Integer credit = 5;
    /** 申请. */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Set<Apply> applies;


    /**
     * 获取 用户编号.
     *
     * @return userId 用户编号.
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 设置 用户编号.
     *
     * @param userId 用户编号.
     */
    public void setUserId(String userId) {
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

//    /**
//     * 获取 用户年龄.
//     *
//     * @return userAge 用户年龄.
//     */
//    public int getUserAge() {
//        return this.userAge;
//    }
//
//    /**
//     * 设置 用户年龄.
//     *
//     * @param userAge 用户年龄.
//     */
//    public void setUserAge(int userAge) {
//        this.userAge = userAge;
//    }
//
//    /**
//     * 获取 用户性别.
//     *
//     * @return userSex 用户性别.
//     */
//    public String getUserSex() {
//        return this.userSex;
//    }
//
//    /**
//     * 设置 用户性别.
//     *
//     * @param userSex 用户性别.
//     */
//    public void setUserSex(String userSex) {
//        this.userSex = userSex;
//    }
//
//    /**
//     * 获取 住址.
//     *
//     * @return userAddress 住址.
//     */
//    public String getUserAddress() {
//        return this.userAddress;
//    }
//
//    /**
//     * 设置 住址.
//     *
//     * @param userAddress 住址.
//     */
//    public void setUserAddress(String userAddress) {
//        this.userAddress = userAddress;
//    }

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

//    public Set<BookRoute> getBookRoute() {
//        return bookRoute;
//    }
//
//    public void setBookRoute(Set<BookRoute> bookRoute) {
//        this.bookRoute = bookRoute;
//    }

    public Set<Notice> getNotices() {
        return notices;
    }

    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }

    public Set<Book> getContribution() {
        return contribution;
    }

    public void setContribution(Set<Book> contribution) {
        this.contribution = contribution;
    }

    public Integer getContributeNum() {
        return contributeNum;
    }

    public void setContributeNum(Integer contributeNum) {
        this.contributeNum = contributeNum;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Set<Apply> getApplies() {
        return applies;
    }

    public void setApplies(Set<Apply> applies) {
        this.applies = applies;
    }

//    public String getProvice() {
//        return provice;
//    }
//
//    public void setProvice(String provice) {
//        this.provice = provice;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public Set<ApplyAdmin> getApplyAdmins() {
        return applyAdmins;
    }

    public void setApplyAdmins(Set<ApplyAdmin> applyAdmins) {
        this.applyAdmins = applyAdmins;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userImg='" + userImg + '\'' +
                ", identity=" + identity +
                ", userDate=" + userDate +
                ", password='" + password + '\'' +
                ", comments=" + comments +
                ", notices=" + notices +
                ", contribution=" + contribution +
                ", contributeNum=" + contributeNum +
                ", credit=" + credit +
                ", applies=" + applies +
                '}';
    }
}
