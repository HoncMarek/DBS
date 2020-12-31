package semestral_project.app.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Datová reprezentace záznamu z tabulky Pacient.
 */
public class Pacient {
    private int id;
    private String name;
    private String surname;
    private String personalIdentifier;

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

    public String getPersonalIdentifier() {
        return personalIdentifier;
    }

    public void setPersonalIdentifier(String personalIdentifier) {
        this.personalIdentifier = personalIdentifier;
    }

    public static Pacient FromResultSet(ResultSet rs) throws SQLException {
        Pacient pacient = new Pacient();

        pacient.setId(rs.getInt("Id"));
        pacient.setName(rs.getString("Name"));
        pacient.setSurname(rs.getString("Surname"));
        pacient.setPersonalIdentifier(rs.getString("PersonalIdentifier"));

        return pacient;
    }

    @Override
    public String toString() {
        return "Id=" + id +
                "| Name='" + name + '\'' +
                "| Surname='" + surname + '\'' +
                "| PersonalIdentifier='" + personalIdentifier + '\'';
    }
}
