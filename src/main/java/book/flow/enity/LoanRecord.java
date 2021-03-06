package book.flow.enity;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 借阅记录表.
 * User: huang
 * Date: 17-7-22
 */
@Entity
@Table(name = "loan_record")
public class LoanRecord implements Serializable {

    /** 编号. */
    @Id
    @GeneratedValue
    private Integer recordId;
    /** 时间. */
    @Temporal(TemporalType.DATE)
    private Date recordDate;
    /** 借阅标志， 是否已借出. */
    private boolean isOut;
    /** 借阅人. */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    /** 借阅的书籍. */
    @ManyToOne(cascade = {CascadeType.ALL})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(name = "bookId")
    private Book book;
    /** 评价. */
    private String recordText;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }

    @Override
    public String toString() {
        return "LoanRecord{" +
                "recordId=" + recordId +
                ", recordDate=" + recordDate +
                ", isOut=" + isOut +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
