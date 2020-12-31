package semestral_project.app.providers;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.entities.Doctor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static semestral_project.utils.ScannerUtils.readString;


/**
 * Třída poskytující přístup datům z databáze z tabulky doktorů.
 */
public class DoctorProvider implements IProvider<Doctor> {
    private DbConnector conn;

    private static DoctorProvider instance;

    public boolean insert(Doctor doctor) {
        String sql = "INSERT INTO Doctor(Name, Surname, Title, LocationId) VALUES(?,?,?,?)";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o doktorovi do SQL dotazu.
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setString(3, doctor.getTitle());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Doctor doctor) {
        String sql = "UPDATE Doctor SET Name=?, Surname=?, Title=?, LocationId=? WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o doktorovi do SQL dotazu.
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSurname());
            statement.setString(3, doctor.getTitle());
            statement.setInt(4, doctor.getLocationId());
            statement.setInt(5, doctor.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(Doctor doctor) {
        String sql = "DELETE FROM Doctor WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o doktorovi do SQL dotazu.
            statement.setInt(1, doctor.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací konkrétní záznam o doktorovi.
     */
    public Doctor getById(int id) {
        String sql = "SELECT * FROM Doctor WHERE Id=?"; // Query pro získání záznamu z tabulky Doctor dle Id.

        Doctor result = null; // Připravím si proměnnou pro výsledný záznam doktora.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            if (rs.next()) // Pokud jsem našel nějaký záznam, nastavím ho jako výsledek této funkce.
            {
                result = Doctor.FromResultSet(rs);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací všechny záznamy o doktorech.
     */
    public List<Doctor> getAll() {
        String sql = "SELECT * FROM Doctor"; // Query pro získání všech záznamů z tabulky Doctor.

        List<Doctor> result = new ArrayList<>(); // Připravím si proměnnou pro záznamy z DB.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Doctor doctor = Doctor.FromResultSet(rs);
                result.add(doctor);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Doctor dummy() {
        Doctor dummy = new Doctor();
        return dummy;
    }

    @Override
    public void edit(Doctor entity, Scanner sc) {
        entity.setName(readString(sc, "Křestní jméno: "));
        entity.setSurname(readString(sc, "Příjmení: "));
        entity.setSurname(readString(sc, "Titul: "));
    }

    /**
     * Vrací instanci provideru dat o doktorech.
     */
    public static DoctorProvider instance() {
        if (instance == null) {
            instance = new DoctorProvider();
            instance.conn = DbConnector.instance();
        }

        return instance;
    }
}
