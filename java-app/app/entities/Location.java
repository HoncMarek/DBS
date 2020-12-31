package semestral_project.app.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Datová reprezentace záznamu z tabulky Location.
 */
public class Location {
    private int id;
    private String name;
    private String city;
    private String street;
    private Time openedFrom;
    private Time openedTo;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Time getOpenedFrom() {
        return openedFrom;
    }

    public void setOpenedFrom(Time openedFrom) {
        this.openedFrom = openedFrom;
    }

    public Time getOpenedTo() {
        return openedTo;
    }

    public void setOpenedTo(Time openedTo) {
        this.openedTo = openedTo;
    }

    public static Location FromResultSet(ResultSet rs) throws SQLException {
        Location location = new Location();

        location.setId(rs.getInt("Id"));
        location.setName(rs.getString("Name"));
        location.setCity(rs.getString("City"));
        location.setStreet(rs.getString("Street"));
        location.setOpenedFrom(rs.getTime("OpenedFrom"));
        location.setOpenedTo(rs.getTime("OpenedTo"));

        return location;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", openedFrom=" + openedFrom +
                ", openedTo=" + openedTo;
    }
}

