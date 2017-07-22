package book.flow.enity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 类型表.
 * User: huang
 * Date: 17-7-22
 */
@Entity
@Table(name = "type")
public class Type implements Serializable {

    /** 编号. */
    @Id
    @GeneratedValue
    private Integer typeId;
    /** 类型名. */
    private String typeName;
    /** 图书. */
    @ManyToMany(cascade = {}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_type", joinColumns = {@JoinColumn(name = "typeId")},
            inverseJoinColumns = {@JoinColumn(name = "bookId")})
    private Set<Book> books;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
