package book.flow.enity;

import javax.persistence.*;
import java.util.Date;

/**
 * 活动表.
 * User: huang
 * Date: 17-10-22
 */
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue
    private int activeId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeDate;
    private String activeTitle;
    private int status = 0;
    private String activeText;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public int getActiveId() {
        return activeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActiveText() {
        return activeText;
    }

    public void setActiveText(String activeText) {
        this.activeText = activeText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActiveTitle() {
        return activeTitle;
    }

    public void setActiveTitle(String activeTitle) {
        this.activeTitle = activeTitle;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activeId=" + activeId +
                ", activeDate=" + activeDate +
                ", activeTitle='" + activeTitle + '\'' +
                ", status=" + status +
                ", activeText='" + activeText + '\'' +
                ", user=" + user +
                '}';
    }
}
