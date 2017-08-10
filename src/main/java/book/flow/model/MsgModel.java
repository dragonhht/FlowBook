package book.flow.model;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Description.
 * User: huang
 * Date: 17-8-10
 */
public class MsgModel {

    private String message;
    private Date date;

    public MsgModel() {
    }

    public MsgModel(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
