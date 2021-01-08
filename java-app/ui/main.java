package semestral_project.ui;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.entities.Doctor;
import semestral_project.app.entities.Location;
import semestral_project.app.entities.Pacient;
import semestral_project.app.entities.Test;
import semestral_project.app.providers.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static semestral_project.utils.ScannerUtils.readInt;
import static semestral_project.utils.ScannerUtils.readString;

public class main {

    private static final String asciiArt = "__    __   ______   __    __  __     __  ______  _______  \r\n|  \\  /  \\ /      \\ |  \\  |  \\|  \\   |  \\|      \\|       \\ \r\n| $$ /  $$|  $$$$$$\\| $$  | $$| $$   | $$ \\$$$$$$| $$$$$$$\\\r\n| $$/  $$ | $$  | $$| $$  | $$| $$   | $$  | $$  | $$  | $$\r\n| $$  $$  | $$  | $$| $$  | $$ \\$$\\ /  $$  | $$  | $$  | $$\r\n| $$$$$\\  | $$  | $$| $$  | $$  \\$$\\  $$   | $$  | $$  | $$\r\n| $$ \\$$\\ | $$__/ $$| $$__/ $$   \\$$ $$   _| $$_ | $$__/ $$\r\n| $$  \\$$\\ \\$$    $$ \\$$    $$    \\$$$   |   $$ \\| $$    $$\r\n \\$$   \\$$  \\$$$$$$   \\$$$$$$      \\$     \\$$$$$$ \\$$$$$$$\nBy Marek Honc, Bruno Pfohl and Martin Hájek\n\n";

    public static void main(String[] args) {
        // Vypíšu ASCII art.
        System.out.println(asciiArt);

        // Zkusím se připojit na databázi. Pokud se nelze připojit, vypíšu hlášku a ukončím program.
        if (!DbConnector.IsConnected()) {
            System.out.println("Nepodařilo se připojit k databázi. Program se ukončuje.");
            return;
        }

        // Spustím hlavní menu.
        menu();
    }


    /**
     * Spouští hlavní menu (hlavní smyčku programu).
     */
    public static void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Zvolte se kterou tabulkou chcete manipulovat. \n" +
                    "1) Doktoři \n" +
                    "2) Pacienti \n" +
                    "3) Místa \n" +
                    "4) Testy \n" +
                    "5) Rezervace \n"
            );

            int option = readInt(sc, "Vaše volba: ");

            switch (option) {
                case 1:
                    doctorSubmenu(sc, DoctorProvider.instance());
                    break;
                case 2:
                    pacientSubmenu(sc, PacientProvider.instance());
                    break;
                case 3:
                    locationSubmenu(sc, LocationProvider.instance());
                    break;
                case 4:
                    testSubmenu(sc, TestProvider.instance());
                    break;
                case 5:
                    bookingSubmenu(sc, BookingProvider.instance());
                    break;
                default:
                    return;
            }
        }
    }


    /**
     * Spouští menu pro manipulaci s tabulkou Pacient.
     * @param sc Instance scanneru pro získání dat ze vstupu.
     * @param provider Provider pro zízkání dat z databáze.
     */
    public static void pacientSubmenu(Scanner sc, PacientProvider provider) {
        while(true) { // Hlavní smyčka podprogramu pro manipulaci s tabulkou Pacient.
            printCommonFunctionsSubmenu(); // Vypíšu hlavní menu (funkce společné pro všechny tabulky).
            System.out.println("6) Pacienti a počet jejich rezervací\n" +
                    "7) Pozitivní pacienti\n"
            );

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            // Předám zpracování volby handleru pro společné operace.
            if (handleCommonFunctionsSubmenu(option, sc, provider)) {
                continue;
            }

            switch (option) {
                case 6:
                    for ( Map.Entry<Pacient, Integer> pair : provider.getPacientsReservationCount().entrySet()) {
                        System.out.println(pair.getKey().toString());
                        System.out.println("Počet rezervací: " + pair.getValue() + "\n");
                    }
                    break;
                case 7:
                    for (Pacient pacient: provider.getPositivePacients()) {
                        System.out.println(pacient.toString());
                    }
                    break;
                default:
                    return;
            }

            System.out.println();
        }
    }

    /**
     * Spouští menu pro manipulaci s tabulkou Booking.
     * @param sc Instance scanneru pro získání dat ze vstupu.
     * @param provider Provider pro zízkání dat z databáze.
     */
    public static void bookingSubmenu(Scanner sc, BookingProvider provider) {
        while(true) { // Hlavní smyčka podprogramu pro manipulaci s tabulkou Pacient.
            printCommonFunctionsSubmenu(); // Vypíšu hlavní menu (funkce společné pro všechny tabulky).

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            // Předám zpracování volby handleru pro společné operace.
            if (handleCommonFunctionsSubmenu(option, sc, provider)) {
                return;
            }
        }
    }

    /**
     * Spouští menu pro manipulaci s tabulkou Doctor.
     * @param sc Instance scanneru pro získání dat ze vstupu.
     * @param provider Provider pro zízkání dat z databáze.
     */
    public static void doctorSubmenu(Scanner sc, DoctorProvider provider) {
        while(true) { // Hlavní smyčka podprogramu pro manipulaci s tabulkou Pacient.
            printCommonFunctionsSubmenu(); // Vypíšu hlavní menu (funkce společné pro všechny tabulky).
            System.out.println("6) Všichni doktoři, kteří nemají dnes rezervace\n" +
                    "7) Výčet počtu rezervací pro tento den\n" +
                    "8) Přidat doktorovi test\n"
            );

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            // Předám zpracování volby handleru pro společné operace.
            if (handleCommonFunctionsSubmenu(option, sc, provider)) {
                continue;
            }

            switch (option) {
                case 6:
                    for (Doctor doctor : provider.getDoctorsWithNoReservationForDate(new Date())) {
                        System.out.println(doctor.toString());
                    }
                    break;
                case 7:
                    for (Map.Entry<Doctor, Integer> pair : provider.getDoctorsReservationCountForDay(new Date()).entrySet()) {
                        System.out.println(pair.getKey().toString());
                        System.out.println("Počet dnešních rezervací: " + pair.getValue() + "\n");
                    }
                    break;

                case 8:
                    int doctorId = readInt(sc, "Zadejte Id doktora: ");
                    int testId = readInt(sc, "Zadejte Id testu: ");

                    Doctor doctor = provider.getById(doctorId);

                    if (doctor != null) {
                        System.out.println("Zvoleno: " + doctor.toString());

                       Test test = TestProvider.instance().getById(testId);

                       if (test != null) {
                           provider.addTestToDoctor(doctor, test);
                           break;
                       }
                       else {
                           System.out.println("Záznam neexistuje!");
                       }
                    }
                    else {
                        System.out.println("Záznam neexistuje!");
                    }

                    break;
                default:
                    return;
            }

            System.out.println();
        }
    }

    /**
     * Spouští menu pro manipulaci s tabulkou Location.
     * @param sc Instance scanneru pro získání dat ze vstupu.
     * @param provider Provider pro zízkání dat z databáze.
     */
    public static void locationSubmenu(Scanner sc, LocationProvider provider) {
        while(true) { // Hlavní smyčka podprogramu pro manipulaci s tabulkou Pacient.
            printCommonFunctionsSubmenu(); // Vypíšu hlavní menu (funkce společné pro všechny tabulky).
            System.out.println("6) Odběrová místa podle názvu města");

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            // Předám zpracování volby handleru pro společné operace.
            if (handleCommonFunctionsSubmenu(option, sc, provider)) {
                continue;
            }

            switch (option) {
                case 6:
                    String city = readString(sc, "Zadejte město: ");

                    for (Location location : provider.getCovidCheckpointsFromCity(city)) {
                        System.out.println(location.toString());
                    }
                    break;
                default:
                    return;
            }

            System.out.println();
        }
    }

    /**
     * Spouští menu pro manipulaci s tabulkou Test.
     * @param sc Instance scanneru pro získání dat ze vstupu.
     * @param provider Provider pro zízkání dat z databáze.
     */
    public static void testSubmenu(Scanner sc, TestProvider provider) {
        while(true) { // Hlavní smyčka podprogramu pro manipulaci s tabulkou Pacient.
            printCommonFunctionsSubmenu(); // Vypíšu hlavní menu (funkce společné pro všechny tabulky).
            System.out.println("6) Seznam všech testů a jací doktoři jej provádí\n");

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            // Předám zpracování volby handleru pro společné operace.
            if (handleCommonFunctionsSubmenu(option, sc, provider)) {
                continue;
            }

            switch (option) {
                case 6:
                    for (Map.Entry<Test, List<Doctor>> pair : provider.getAllTestToDoctorCombinations().entrySet()) {
                        System.out.println("[" +pair.getKey().toString() + "]\n---------------------------------------------------------------");

                        for (Doctor doctor : pair.getValue()) {
                            System.out.println(doctor.toString());
                        }

                        System.out.println("\n");
                    }
                    break;
                default:
                    return;
            }

            System.out.println();
        }
    }

    /**
     * Vypíše možnosti manipulace s tabulkou (seznam možných operací nad tabulkou, který mají všechny tabulky společné).
     */
    public static void printCommonFunctionsSubmenu() {
        System.out.println("Zvolte jakou akci chcete provést. \n" +
                "ostatní) Hlavní menu\n" +
                "1) Zobrazit všechny záznamy \n" +
                "2) Zobrazit konkrétní záznam \n" +
                "3) Přidat nový záznam \n" +
                "4) Upravit stávající záznam \n" +
                "5) Smazat záznam \n"
        );
    }

    /**
     * Metoda, která obsluhuje základní manipulaci s jakoukoliv tabulkou v databázi (CRUD + getById + getAll).
     * @param sc Instance scanneru pro získání dat ze vstupu.
     * @param provider Provider pro zízkání dat z databáze.
     */
    public static <TEntity> boolean handleCommonFunctionsSubmenu(int option, Scanner sc, IProvider<TEntity> provider) {
            switch (option) {
                case 1: // Zobrazení všech záznamů z tabulky.
                    for (TEntity entity : provider.getAll()) {
                        System.out.println(entity);
                    }
                    break;
                case 2: // Zobrazení záznamu podle konrétního id.
                    int id = readInt(sc, "Zadejte Id záznamu: ");
                    if (id > 0){
                       TEntity entity = provider.getById(id);

                       if (entity != null) {
                           System.out.println(entity.toString() + "\n");
                       }
                       else {
                           System.out.println("Záznam se zadaným Id neexistuje.");
                       }
                    }
                    break;
                case 3: // Vytvoření nového záznamu.
                    TEntity entity = provider.dummy(); // Vytvořím si nový dummy objekt.
                    provider.edit(entity, sc); // Spustím dialog pro vložení dat do dummy objektu..
                    provider.insert(entity); // Vložím objekt do databáze.
                    break;
                case 4: // Upravení stávajícího záznamu.
                    id = readInt(sc, "Zadejte Id záznamu: ");
                    if (id > 0){
                        entity = provider.getById(id); // Nejdříve si najdu záznam v DB.
                        System.out.println("Zvolený záznam: ");
                        System.out.println(entity.toString() + "\n\n");

                        provider.edit(entity, sc); // Spustím dialog pro vložení dat do dummy objektu.
                        provider.update(entity); // Uložím změny provedené na objektu do databáze.
                    }
                    break;
                case 5: // Smazání záznamu podle jeho Id.
                    id = readInt(sc, "Zadejte Id záznamu: ");
                    if (id > 0){
                        entity = provider.getById(id);
                        System.out.println("Zvolený záznam: ");

                        if (entity != null) {
                            System.out.println(entity.toString() + "\n");
                        }
                        else {
                           System.out.println("Záznam se zadaným Id neexistuje.");
                        }

                        System.out.println();

                        if (readInt(sc, "Pro potvrzení zadejte 1: ") == 1) {
                            provider.delete(entity);
                        }
                    }
                    break;
                default:
                    return false;
            }

            return true;
    }

}
