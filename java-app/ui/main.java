package semestral_project.ui;

import semestral_project.app.dbconnect.DbConnector;
import semestral_project.app.providers.*;

import java.util.Scanner;

import static semestral_project.utils.ScannerUtils.readInt;

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

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            if (!handleCommonFunctionsSubmenu(option, sc, provider)) { // Pokud zvolená operace nebyla jednou ze společných.
                return; // Ukončuji, jiné operace totiž nemám.
            }
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

            if (!handleCommonFunctionsSubmenu(option, sc, provider)) { // Pokud zvolená operace nebyla jednou ze společných.
                return; // Ukončuji, jiné operace totiž nemám.
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

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            if (!handleCommonFunctionsSubmenu(option, sc, provider)) { // Pokud zvolená operace nebyla jednou ze společných.
                return; // Ukončuji, jiné operace totiž nemám.
            }
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

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            if (!handleCommonFunctionsSubmenu(option, sc, provider)) { // Pokud zvolená operace nebyla jednou ze společných.
                return; // Ukončuji, jiné operace totiž nemám.
            }
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

            int option = readInt(sc, "Vaše volba: "); // Zjistím volbu uživatele.

            if (!handleCommonFunctionsSubmenu(option, sc, provider)) { // Pokud zvolená operace nebyla jednou ze společných.
                return; // Ukončuji, jiné operace totiž nemám.
            }
        }
    }

    /**
     * Vypíše možnosti manipulace s tabulkou (seznam možných operací nad tabulkou, který mají všechny tabulky společné).
     */
    public static void printCommonFunctionsSubmenu() {
        System.out.println("Zvolte jakou akci chcete provést. \n" +
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
                       System.out.println(entity.toString() + "\n");
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
                        System.out.println(entity.toString() + "\n");
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
