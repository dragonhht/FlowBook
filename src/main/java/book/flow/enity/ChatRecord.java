package book.flow.enity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 聊天记录表.
 * User: huang
 * Date: 17-8-9
 */
@Entity
@Table(name = "chat_record")
public class ChatRecord implements Serializable {

    /** 编号. */
    @Id
    @GeneratedValue
    private Long chatId;
    /** 发送者. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender")
    private User sender;
    /** 接收者. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver")
    private User receiver;
    /** 信息内容. */
    private String message;
    /** 发送时间. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    /** 查看标志. */
    private boolean looked = false;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isLooked() {
        return looked;
    }

    public void setLooked(boolean looked) {
        this.looked = looked;
    }

    @Override
    public String toString() {
        return "ChatRecord{" +
                "chatId=" + chatId +
                ", message='" + message + '\'' +
                ", sendDate=" + sendDate +
                ", looked=" + looked +
                '}';
    }
}
