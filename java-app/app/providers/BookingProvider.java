package semestral_project.app.providers;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.entities.Booking;
import semestral_project.app.entities.Doctor;
import semestral_project.app.entities.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static semestral_project.utils.ScannerUtils.*;


/**
 * Třída poskytující přístup datům z databáze z tabulky rezervací.
 */
public class BookingProvider implements IProvider<Booking> {
    private DbConnector conn;

    private static BookingProvider instance;

    public boolean insert(Booking booking) {
        String sql = "INSERT INTO Booking(DoctorToTestId, PacientId, StartsAt, Paid, IsPositive) VALUES(?,?,?,?,?)";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o pacientovi do SQL dotazu.
            statement.setInt(1, booking.getDoctorToTestId());
            statement.setInt(2, booking.getPacientId());
            statement.setDate(3, booking.getStartsAt());
            statement.setBoolean(4, booking.isPaid());
            statement.setBoolean(5, booking.isPositive());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (Exception e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Booking booking) {
        String sql = "UPDATE Booking SET DoctorToTestId=?, PacientId=?, StartsAt=?, Paid=?, IsPositive=? WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o pacientovi do SQL dotazu.
            statement.setInt(1, booking.getDoctorToTestId());
            statement.setInt(2, booking.getPacientId());
            statement.setDate(3, booking.getStartsAt());
            statement.setBoolean(4, booking.isPaid());
            statement.setBoolean(5, booking.isPositive());
            statement.setInt(6, booking.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(Booking booking) {
        String sql = "DELETE FROM Booking WHERE Id=?";

        boolean result = false;

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.

        try {
            // Vložím data o pacientovi do SQL dotazu.
            statement.setInt(1, booking.getId());

            result = statement.execute(); // Spustím dotaz na DB.
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací konkrétní záznam o rezervaci.
     */
    public Booking getById(int id) {
        String sql = "SELECT * FROM Booking WHERE Id=?"; // Query pro získání záznamu z tabulky Booking dle Id.

        Booking result = null; // Připravím si proměnnou pro výsledný záznam rezervace.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            if (rs.next()) // Pokud jsem našel nějaký záznam, nastavím ho jako výsledek této funkce.
            {
                result = Booking.FromResultSet(rs);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Vrací všechny záznamy o rezervacích.
     */
    public List<Booking> getAll() {
        String sql = "SELECT * FROM Booking"; // Query pro získání všech záznamů z tabulky Booking.

        List<Booking> result = new ArrayList<>(); // Připravím si proměnnou pro záznamy z DB.

        PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
        try {
            ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

            while (rs.next()) // Parsuju jednotlivé řádky z DB, dokud jsou dostupné.
            {
                Booking booking = Booking.FromResultSet(rs);
                result.add(booking);
            }
        } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Booking dummy() {
        Booking dummy = new Booking();
        return dummy;
    }

    @Override
    public void edit(Booking entity, Scanner sc) {
        Date startsAt = readDate(sc, "Začátek rezervace: ");

        if (startsAt != null) {
            entity.setStartsAt(startsAt);
        }

        entity.setPacientId(readInt(sc, "Id pacienta: "));
        entity.setPaid(readBoolean(sc, "Je rezervace zaplacená? (a/cokoliv): ", "a"));
        entity.setPositive(readBoolean(sc, "Je pacient pozitivní? (a/cokoliv): ", "a"));

        int doctorId = readInt(sc, "Zadejte Id doktora: ");
        int testId = readInt(sc, "Zadejte Id testu: ");

        Doctor doctor = DoctorProvider.instance().getById(doctorId);

        if (doctor != null) {
            System.out.println("Zvoleno: " + doctor.toString());

            Test test = TestProvider.instance().getById(testId);

            if (test != null) {
                System.out.println("Zvoleno: " + test.toString());

                String sql = "SELECT Id FROM DoctorToTest WHERE DoctorId=? AND TestId=?";

                PreparedStatement statement = this.conn.prepare(sql); // Připravím si statement, který budu pouštět na DB.
                try {
                    statement.setInt(1, doctorId);
                    statement.setInt(2, testId);

                    ResultSet rs = statement.executeQuery(); // Spustím dotaz na DB.

                    if (rs.next()) {
                        entity.setDoctorToTestId(rs.getInt("Id"));
                    }
                } catch (SQLException e) { // Musím počítat s tím, že JDBC může vyhodit výjimku.
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Záznam neexistuje!");
            }
        }
    }

    /**
     * Vrací instanci provideru dat o rezervacích.
     */
    public static BookingProvider instance() {
        if (instance == null) {
            instance = new BookingProvider();
            instance.conn = DbConnector.instance();
        }

        return instance;
    }
}
