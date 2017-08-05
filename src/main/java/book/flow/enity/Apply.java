package book.flow.enity;

import org.hibernate.annotations.Check;
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
    private Integer applyId;
    /** 申请内容. */
    private String applyText;
    /** 申请日期. */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;
    /** 状态. */
    private String status = "待审批";
    /** 申请人. */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    /** 申请中的图片. */
    @OneToMany
    @JoinColumn(name = "applyId")
    private Set<Img> imgs;

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
}
