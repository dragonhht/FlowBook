package book.flow.enity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 举报图片.
 * User: huang
 * Date: 17-8-18
 */
@Entity
@Table(name = "report_img")
public class ReportImg implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
