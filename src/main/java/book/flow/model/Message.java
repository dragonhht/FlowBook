package book.flow.model;

import java.util.Date;

/**
 * 聊天信息模型类.
 * User: huang
 * Date: 17-8-7
 */
public class Message {

    /** 信息. */
    private String message;
    /** 接受的用户编号. */
    private String userId;
    /** 时间. */
    private String sendDate;

    public Message() {

    }

    public Message(String message, String sendDate) {
        this.message = message;
        this.sendDate = sendDate;
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", userId=" + userId +
                ", sendDate=" + sendDate +
                '}';
    }
}
