package semestral_project.app.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Datová reprezentace záznamu z tabulky Doctor.
 */
public class Doctor {
    private int id;
    private String name;
    private String surname;
    private String title;
    private int locationId;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public static Doctor FromResultSet(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();

        doctor.setId(rs.getInt("Id"));
        doctor.setName(rs.getString("Name"));
        doctor.setSurname(rs.getString("Surname"));
        doctor.setTitle(rs.getString("Title"));
        doctor.setLocationId(rs.getInt("LocationId"));

        return doctor;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", title='" + title + '\'' +
                ", locationId=" + locationId;
    }
}
