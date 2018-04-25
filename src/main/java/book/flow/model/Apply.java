package book.flow.model;

/**
 * Description.
 *
 * @author: huang
 * Date: 18-4-25
 */
public class Apply {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
