package book.flow.enity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 图书路线表.
 * User: huang
 * Date: 17-7-3
 */
@Entity
@Table(name = "book_route")
public class BookRoute implements Serializable {
    /** 路线编号. */
    @Id
    @GeneratedValue
    private int routeId;
    /** 路线创建时间. */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date routeDate;
    /** 借阅图书. */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private Book book;
    /** 借阅用户. */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    /**
     * 获取 路线编号.
     *
     * @return routeId 路线编号.
     */
    public int getRouteId() {
        return this.routeId;
    }

    /**
     * 设置 路线编号.
     *
     * @param routeId 路线编号.
     */
    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    /**
     * 获取 路线创建时间.
     *
     * @return routeDate 路线创建时间.
     */
    public Date getRouteDate() {
        return this.routeDate;
    }

    /**
     * 设置 路线创建时间.
     *
     * @param routeDate 路线创建时间.
     */
    public void setRouteDate(Date routeDate) {
        this.routeDate = routeDate;
    }

    /**
     * 获取 借阅图书.
     *
     * @return book 借阅图书.
     */
    public Book getBook() {
        return this.book;
    }

    /**
     * 设置 借阅图书.
     *
     * @param book 借阅图书.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * 获取 借阅用户.
     *
     * @return user 借阅用户.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * 设置 借阅用户.
     *
     * @param user 借阅用户.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
