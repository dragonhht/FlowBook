package book.flow.enity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 图书传阅申请表.
 * User: huang
 * Date: 17-8-15
 */
@Entity
@Table(name = "flow_apply")
public class FlowApply implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    /** 申请人. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applyUser")
    private User applyUser;
    /** 同意人. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "okUser")
    private User okUser;
    /** 申请图书. */
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    /** 说的的话. */
    private String wantSay = " ";
    /** 状态. 0 为未查看， 1 为同意， 2 为未同意*/
    private Integer status = 0;
    /** 日期. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyDate;
    /** 拒绝理由. */
    private String refuse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(User applyUser) {
        this.applyUser = applyUser;
    }

    public User getOkUser() {
        return okUser;
    }

    public void setOkUser(User okUser) {
        this.okUser = okUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getWantSay() {
        return wantSay;
    }

    public void setWantSay(String wantSay) {
        this.wantSay = wantSay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
    }
}
