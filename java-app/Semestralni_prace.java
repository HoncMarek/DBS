package dbs.semestralni_prace;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
https://github.com/HoncMarek/DBS
*/

public class Semestralni_prace {

    private static Scanner sc = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean konecProgramu = false;
        while (!konecProgramu) {
            vypisHlavniMenuProgramu();
            int volbaUzivatele = nacistVolbu();
            switch (volbaUzivatele) {
                case 0:
                    konecProgramu = true;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                default:
                    System.out.println("Neplatná volba");
            }
        }
    }
    
    private static void vypisHlavniMenuProgramu() {
        System.out.println("");
        System.out.println("0. Konec programu");
        System.out.println("1. Vložení");
        System.out.println("2. Editace");
        System.out.println("3. Mazání");
        System.out.println("4. Vyhledání podle ID");
        System.out.println("5. vnořený SELECT v SELECT");
        System.out.println("6. vnořený SELECT ve FROM");
        System.out.println("7. vnořený SELECT ve WHERE");
        System.out.println("8. GROUP BY");
        System.out.println("9. množinovou operaci");
        System.out.println("10. LEFT JOIN");
    }
    
    /**
     * Načítá volbu od uživatele
     *
     * @return vrací načtenou hodnotu typu int
     */
    private static int nacistVolbu() {
        int volba = -1;
        System.out.print("Zadej zvolenou polozku: ");
        try {
            volba = sc.nextInt();
        } catch (InputMismatchException e) {
            volba = -1;
        } finally {
            sc.nextLine();
        }
        return volba;
    }
}
