package semestral_project.app.providers;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.entities.Pacient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static semestral_project.utils.ScannerUtils.readString;

/**
 * Třída poskytující přístup datům z databáze z tabulky pacientů.
 */
public class PacientProvider implements IProvider<Pacient>{
    private DbConnector conn;

    private static PacientProvider instance;

    public boolean insert(Pacient pacient) {
        String sql = "INSERT INTO Pacient(Name, Surname, PersonalIdentifier) VALUES(?,?,?)";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o pacientovi do SQL dotazu.
            statement.setString(1, pacient.getName());
            statement.setString(2, pacient.getSurname());
            statement.setString(3, pacient.getPersonalIdentifier());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Pacient pacient) {
        String sql = "UPDATE Pacient SET Name=?, Surname=?, PersonalIdentifier=? WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o pacientovi do SQL dotazu.
            statement.setString(1, pacient.getName());
            statement.setString(2, pacient.getSurname());
            statement.setString(3, pacient.getPersonalIdentifier());
            statement.setInt(4, pacient.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(Pacient pacient) {
        String sql = "DELETE FROM Pacient WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o pacientovi do SQL dotazu.
            statement.setInt(1, pacient.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Vrací konkrétní záznam o pacientovi.
     */
    public Pacient getById(int id) {
        String sql = "SELECT * FROM Pacient WHERE Id=?"; // Query pro získání záznamu z tabulky Pacient dle Id.

        Pacient result = null; // Připravím si proměnnou pro výsledný záznam pacienta.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            if (rs.next()) // Pokud jsem našel nějaký záznam, nastavím ho jako výsledek této funkce.
            {
                result = Pacient.FromResultSet(rs);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací všechny záznamy o pacientech.
     */
    public List<Pacient> getAll() {
        String sql = "SELECT * FROM Pacient"; // Query pro získání všech záznamů z tabulky Pacient.

        List<Pacient> result = new ArrayList<>(); // Připravím si proměnnou pro záznamy z DB.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Pacient pacient = Pacient.FromResultSet(rs);
                result.add(pacient);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Pacient dummy() {
        Pacient dummy = new Pacient();
        return dummy;
    }

    @Override
    public void edit(Pacient entity, Scanner sc) {
        entity.setName(readString(sc, "Křestní jméno: "));
        entity.setSurname(readString(sc, "Příjmení: "));
        entity.setPersonalIdentifier(readString(sc, "Rodné číslo: "));
    }

    /**
     * Vrací instanci provideru dat o Pacientech.
     */
    public static PacientProvider instance() {
        if (instance == null) {
            instance = new PacientProvider();
            instance.conn = DbConnector.instance();
        }

        return instance;
    }
}
