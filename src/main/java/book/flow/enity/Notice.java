package book.flow.enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 公告类.
 * User: huang
 * Date: 17-7-22
 */
@Entity
@Table(name = "notice")
public class Notice implements Serializable {

    /** 编号. */
    @Id
    @GeneratedValue
    private Integer noticeId;
    /** 发布日期. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date noticeDate;
    /** 公告内容. */
    @Size(min = 1, message = "{notice.text.null.error}")
    private String noticeText;
    /** 发布者. */
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
