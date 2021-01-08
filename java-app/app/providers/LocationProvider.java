package semestral_project.app.providers;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.entities.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static semestral_project.utils.ScannerUtils.readString;

/**
 * Třída poskytující přístup datům z databáze z tabulky míst.
 */
public class LocationProvider implements IProvider<Location> {
    private DbConnector conn;

    private static LocationProvider instance;

    public boolean insert(Location location) {
        String sql = "INSERT INTO Location(Name, City, Street, OpenedFrom, OpenedTo) VALUES(?,?,?,?,?)";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o místu do SQL dotazu.
            statement.setString(1, location.getName());
            statement.setString(2, location.getCity());
            statement.setString(3, location.getStreet());
            statement.setTime(4, location.getOpenedFrom());
            statement.setTime(5, location.getOpenedTo());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Location location) {
        String sql = "UPDATE Location SET Name=?, City=?, Street=?, OpenedFrom=?, OpenedTo=? WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o místu do SQL dotazu.
            statement.setString(1, location.getName());
            statement.setString(2, location.getCity());
            statement.setString(3, location.getStreet());
            statement.setTime(4, location.getOpenedFrom());
            statement.setTime(5, location.getOpenedTo());
            statement.setInt(6, location.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(Location location) {
        String sql = "DELETE FROM Location WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o místu do SQL dotazu.
            statement.setInt(1, location.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací konkrétní záznam o místu.
     */
    public Location getById(int id) {
        String sql = "SELECT * FROM Location WHERE Id=?"; // Query pro získání záznamu z tabulky Location dle Id.

        Location result = null; // Připravím si proměnnou pro výsledný záznam místě.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            if (rs.next()) // Pokud jsem našel nějaký záznam, nastavím ho jako výsledek této funkce.
            {
                result = Location.FromResultSet(rs);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací všechny záznamy o místech.
     */
    public List<Location> getAll() {
        String sql = "SELECT * FROM Location"; // Query pro získání všech záznamů z tabulky Location.

        List<Location> result = new ArrayList<>(); // Připravím si proměnnou pro záznamy z DB.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Location location = Location.FromResultSet(rs);
                result.add(location);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací všechny záznamy o místech.
     */
    public List<Location> getCovidCheckpointsFromCity(String city) {
        String sql = "select L.* from Location L where City like ? intersect select L.* from Location L where Id in (select D.LocationId from Test T join DoctorToTest DTT on DTT.TestId = T.Id join Doctor D on DTT.DoctorId = D.Id where T.Name like 'PCR' or T.Name like 'Antigen')";

        List<Location> result = new ArrayList<>(); // Připravím si proměnnou pro záznamy z DB.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            statement.setString(1, "%" + city + "%");

            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Location location = Location.FromResultSet(rs);
                result.add(location);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Location dummy() {
        Location dummy = new Location();
        return dummy;
    }

    @Override
    public void edit(Location entity, Scanner sc) {
        entity.setName(readString(sc, "Název: "));
        entity.setCity(readString(sc, "Město: "));
        entity.setStreet(readString(sc, "Ulice: "));
    }

    /**
     * Vrací instanci provideru dat o místech.
     */
    public static LocationProvider instance() {
        if (instance == null) {
            instance = new LocationProvider();
            instance.conn = DbConnector.instance();
        }

        return instance;
    }
}
