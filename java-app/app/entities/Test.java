package semestral_project.app.entities;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Datová reprezentace záznamu z tabulky Test.
 */
public class Test {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int length;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static Test FromResultSet(ResultSet rs) throws SQLException {
        Test test = new Test();

        test.setId(rs.getInt("Id"));
        test.setName(rs.getString("Name"));
        test.setDescription(rs.getString("Description"));
        test.setPrice(rs.getBigDecimal("Price"));
        test.setLength(rs.getInt("Length"));

        return test;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", length=" + length;
    }
}
