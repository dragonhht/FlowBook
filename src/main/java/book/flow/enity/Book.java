package book.flow.enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 图书表.
 * User: huang
 * Date: 17-7-3
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {
    /** 图书编号. */
    @Id
    @GeneratedValue
    private int bookId;
    /** 图书名. */
    @Size(min = 1, message = "{book.name.null.error}")
    private String bookName;
    /** 作者. */
    @Size(min = 1, message = "{book.author.null.error}")
    private String author;
    /** 出版社. */
    @Size(min = 1, message = "{book.publish.null.error}")
    private String publish;
    /** ISBN. */
    private String ISBN;
    /** 简介. */
    private String introduction;
    /** 评价星级. */
    private int bookStart;
    /** 图书图片. */
    private String bookImg = "/img/unknow_book.png";
    /** 上传时间. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookDate;
//    /** 借阅路线. */
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "bookId")
//    @JsonBackReference
//    private Set<BookRoute> bookRoute;
    /** 评论. */
    @OneToMany
    @JoinColumn(name = "bookId")
    @OrderBy("commentDate desc")
    @JsonBackReference
    private Set<Comment> comments;
    /** 图书类型. */
    @ManyToMany(cascade = {}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_type", joinColumns = {@JoinColumn(name = "bookId")},
            inverseJoinColumns = {@JoinColumn(name = "typeId")})
    @JsonBackReference
    private Set<Type> types;
    /** 图书贡献者. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User contributor;
    /** 已申请的书籍. */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    @JsonBackReference
    private Set<Apply> applies;
    /** 记录. */
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bookId")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonBackReference
    private Set<LoanRecord> records;
    /** 0为未退出图书，1为已退出图书. */
    private int status = 0;

    /**
     * 获取 图书编号.
     *
     * @return bookId 图书编号.
     */
    public int getBookId() {
        return this.bookId;
    }

    /**
     * 设置 图书编号.
     *
     * @param bookId 图书编号.
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * 获取 图书名.
     *
     * @return bookName 图书名.
     */
    public String getBookName() {
        return this.bookName;
    }

    /**
     * 设置 图书名.
     *
     * @param bookName 图书名.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * 获取 作者.
     *
     * @return author 作者.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * 设置 作者.
     *
     * @param author 作者.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取 出版社.
     *
     * @return publish 出版社.
     */
    public String getPublish() {
        return this.publish;
    }

    /**
     * 设置 出版社.
     *
     * @param publish 出版社.
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * 获取 简介.
     *
     * @return introduction 简介.
     */
    public String getIntroduction() {
        return this.introduction;
    }

    /**
     * 设置 简介.
     *
     * @param introduction 简介.
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取 评价星级.
     *
     * @return bookStart 评价星级.
     */
    public int getBookStart() {
        return this.bookStart;
    }

    /**
     * 设置 评价星级.
     *
     * @param bookStart 评价星级.
     */
    public void setBookStart(int bookStart) {
        this.bookStart = bookStart;
    }

    /**
     * 获取 图书图片.
     *
     * @return bookImg 图书图片.
     */
    public String getBookImg() {
        return this.bookImg;
    }

    /**
     * 设置 图书图片.
     *
     * @param bookImg 图书图片.
     */
    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }


    /**
     * 获取 上传时间.
     *
     * @return bookDate 上传时间.
     */
    public Date getBookDate() {
        return this.bookDate;
    }

    /**
     * 设置 上传时间.
     *
     * @param bookDate 上传时间.
     */
    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }


//    public Set<BookRoute> getBookRoute() {
//        return bookRoute;
//    }
//
//    public void setBookRoute(Set<BookRoute> bookRoute) {
//        this.bookRoute = bookRoute;
//    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public User getContributor() {
        return contributor;
    }

    public void setContributor(User contributor) {
        this.contributor = contributor;
    }

    public Set<Apply> getApplies() {
        return applies;
    }

    public void setApplies(Set<Apply> applies) {
        this.applies = applies;
    }

    public Set<LoanRecord> getRecords() {
        return records;
    }

    public void setRecords(Set<LoanRecord> records) {
        this.records = records;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
