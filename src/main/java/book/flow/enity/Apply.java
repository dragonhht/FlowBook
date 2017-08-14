package book.flow.enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 申请表.
 * User: huang
 * Date: 17-8-4
 */
@Entity
@Table(name = "user_apply")
public class Apply implements Serializable {

    /** 申请编号. */
    @Id
    @GeneratedValue
    private int applyId;
    /** 申请内容. */
    private String applyText;
    /** 申请日期. */
    @Temporal(TemporalType.DATE)
    private Date applyDate;
    /** 状态. */
    private String status = "待审批";
    /** 申请人. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    /** 申请中的图片. */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "applyId")
    private Set<Img> imgs;
    /** 申请的图书. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookId")
    private Book book;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getApplyText() {
        return applyText;
    }

    public void setApplyText(String applyText) {
        this.applyText = applyText;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Img> getImgs() {
        return imgs;
    }

    public void setImgs(Set<Img> imgs) {
        this.imgs = imgs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
