package book.flow.enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论表.
 * User: huang
 * Date: 17-7-3
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    /** 评论编号. */
    @Id
    @GeneratedValue
    private int commentId;
    /** 评论内容. */
    private String commentText;
    /** 评论时间. */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentDate;
    /** 评论用户. */
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;


    /**
     * 获取 评论编号.
     *
     * @return commentId 评论编号.
     */
    public int getCommentId() {
        return this.commentId;
    }

    /**
     * 设置 评论编号.
     *
     * @param commentId 评论编号.
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * 获取 评论内容.
     *
     * @return commentText 评论内容.
     */
    public String getCommentText() {
        return this.commentText;
    }

    /**
     * 设置 评论内容.
     *
     * @param commentText 评论内容.
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * 获取 评论时间.
     *
     * @return commentDate 评论时间.
     */
    public Date getCommentDate() {
        return this.commentDate;
    }

    /**
     * 设置 评论时间.
     *
     * @param commentDate 评论时间.
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * 获取 评论用户.
     *
     * @return user 评论用户.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * 设置 评论用户.
     *
     * @param user 评论用户.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
