package book.flow.enity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 图片表.
 * User: huang
 * Date: 17-8-4
 */
@Entity
@Table(name = "img")
public class Img implements Serializable {

    /** 编号. */
    @Id
    @GeneratedValue
    private Integer imgId;
    /** 路径. */
    private String imgPath;

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
