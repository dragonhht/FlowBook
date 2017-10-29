package book.flow.enity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理员申请表.
 * User: huang
 * Date: 17-10-28
 */
@Entity
@Table(name = "apply_admin")
public class ApplyAdmin implements Serializable {

    @Id
    @GeneratedValue
    private int applyId;
    private String applyText = "";
    private int status;
    @Temporal(TemporalType.DATE)
    private Date applyDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getApplyText() {
        return applyText;
    }

    public void setApplyText(String applyText) {
        this.applyText = applyText;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}

