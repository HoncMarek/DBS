package semestral_project.app.providers;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.entities.Doctor;
import semestral_project.app.entities.Pacient;
import semestral_project.app.entities.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static semestral_project.utils.ScannerUtils.*;

/**
 * Třída poskytující přístup datům z databáze z tabulky testů.
 */
public class TestProvider implements IProvider<Test> {
    private DbConnector conn;

    private static TestProvider instance;

    public boolean insert(Test test) {
        String sql = "INSERT INTO Test(Name, Description, Price, Length) VALUES(?,?,?,?)";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o testu do SQL dotazu.
            statement.setString(1, test.getName());
            statement.setString(2, test.getDescription());
            statement.setBigDecimal(3, test.getPrice());
            statement.setInt(4, test.getLength());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Test test) {
        String sql = "UPDATE Test SET Name=?, Description=?, Price=?, Length=? WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o testu do SQL dotazu.
            statement.setString(1, test.getName());
            statement.setString(2, test.getDescription());
            statement.setBigDecimal(3, test.getPrice());
            statement.setInt(4, test.getLength());
            statement.setInt(5, test.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(Test test) {
        String sql = "DELETE FROM Test WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o testu do SQL dotazu.
            statement.setInt(1, test.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací konkrétní záznam o testu.
     */
    public Test getById(int id) {
        String sql = "SELECT * FROM Test WHERE Id=?"; // Query pro získání záznamu z tabulky Test dle Id.

        Test result = null; // Připravím si proměnnou pro výsledný záznam testu.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            if (rs.next()) // Pokud jsem našel nějaký záznam, nastavím ho jako výsledek této funkce.
            {
                result = Test.FromResultSet(rs);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací všechny záznamy o testech.
     */
    public List<Test> getAll() {
        String sql = "SELECT * FROM Test"; // Query pro získání všech záznamů z tabulky Test.

        List<Test> result = new ArrayList<>(); // Připravím si proměnnou pro záznamy z DB.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Test test = Test.FromResultSet(rs);
                result.add(test);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací seznam všech testů a jací doktoři je provádí.
     */
    public Map<Test, List<Doctor>> getAllTestToDoctorCombinations() {
        String sql = "select T.Id, T.Name as 'TestName', T.Description, T.Price, T.Length, D.Name, D.* from Test T left join DoctorToTest DTT on DTT.TestId = T.Id left join Doctor D on D.Id = DTT.DoctorId";

        Map<Test, List<Doctor>> result = new Hashtable<>();

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Test test = Test.FromResultSet(rs);
                test.setName(rs.getString("TestName"));

                Doctor doctor = Doctor.FromResultSet(rs);

                if (!result.containsKey(test)) {
                    result.put(test, new ArrayList<>());
                }

                result.get(test).add(doctor);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Test dummy() {
        Test dummy = new Test();
        return dummy;
    }

    @Override
    public void edit(Test entity, Scanner sc) {
        entity.setName(readString(sc, "Název: "));
        entity.setDescription(readString(sc, "Popis: "));
        entity.setPrice(readBigDecimal(sc, "Cena: "));
        entity.setLength(readInt(sc, "Délka: "));
    }

    /**
     * Vrací instanci provideru dat o testech.
     */
    public static TestProvider instance() {
        if (instance == null) {
            instance = new TestProvider();
            instance.conn = DbConnector.instance();
        }

        return instance;
    }
}
