package book.flow.model;

/**
 * 聊天信息模型类.
 * User: huang
 * Date: 17-8-7
 */
public class Message {

    /** 信息. */
    private String message;

    public Message() {

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

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
