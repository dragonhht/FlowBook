package book.flow.model;

/**
 * Description.
 * User: huang
 * Date: 17-8-10
 */
public class MsgModel {

    private String message;
    private String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MsgModel{" +
                "message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
