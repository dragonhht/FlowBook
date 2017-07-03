package book.flow.enity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private String bookName;
    /** 作者. */
    private String author;
    /** 出版社. */
    private String publish;
    /** 简介. */
    private String introduction;
    /** 评价星级. */
    private int bookStart;
    /** 图书图片. */
    private String bookImg;
    /** 上传时间. */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bookDate;
    /** 借阅路线. */
    @OneToOne(mappedBy = "book")
    @JoinColumn(name = "bookId")
    private BookRoute bookRoute;


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


    /**
     * 获取 借阅路线.
     *
     * @return bookRoute 借阅路线.
     */
    public BookRoute getBookRoute() {
        return this.bookRoute;
    }

    /**
     * 设置 借阅路线.
     *
     * @param bookRoute 借阅路线.
     */
    public void setBookRoute(BookRoute bookRoute) {
        this.bookRoute = bookRoute;
    }
}
