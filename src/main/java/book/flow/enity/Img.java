package book.flow.enity;

import javax.persistence.*;
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
    private int imgId;
    /** 路径. */
    private String imgPath;
    @ManyToOne
    @JoinColumn(name = "applyId")
    private Apply apply;

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

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public Apply getApply() {
        return apply;
    }

    public void setApply(Apply apply) {
        this.apply = apply;
    }
}
