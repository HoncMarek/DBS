package semestral_project.utils;

import sun.java2d.pipe.SpanShapeRenderer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Pomocná třída pro práci se vstupem od uživatele.
 */
public class ScannerUtils {
    /**
     * Vrací volbu uživatele (číslo).
     * @param sc Instance Scanneru pro získání dat ze vstupu.
     * @param msg Zpráva která se má zobrazit uživateli.
     * @return Číslo, které uživatel zadal.
     */
    public static int readInt(Scanner sc, String msg) {
        int num = -1;
        System.out.print(msg);

        try {
            num = sc.nextInt();
        } catch (InputMismatchException e) {
            num = -1;
        } finally {
            sc.nextLine();
        }

        System.out.println();

        return num;
    }

    /**
     * Vrací volbu uživatele (desetinné číslo).
     * @param sc Instance Scanneru pro získání dat ze vstupu.
     * @param msg Zpráva která se má zobrazit uživateli.
     * @return Číslo, které uživatel zadal.
     */
    public static BigDecimal readBigDecimal(Scanner sc, String msg) {
        BigDecimal num;
        System.out.print(msg);

        try {
            num = sc.nextBigDecimal();
        } catch (InputMismatchException e) {
            num = BigDecimal.valueOf(-1);
        } finally {
            sc.nextLine();
        }

        System.out.println();

        return num;
    }

    /**
     * Vrací volbu uživatele (text).
     * @param sc Instance Scanneru pro získání dat ze vstupu.
     * @param msg Zpráva která se má zobrazit uživateli.
     * @return Text, který uživatel zadal.
     */
    public static String readString(Scanner sc, String msg) {
        System.out.print(msg);

        String result = sc.nextLine();

        return result;
    }

    /**
     * Vrací volbu uživatele (datum).
     * @param sc Instance Scanneru pro získání dat ze vstupu.
     * @param msg Zpráva která se má zobrazit uživateli.
     * @return Zparsované datum, které uživatel zadal.
     */
    public static Date readDate(Scanner sc, String msg) {
        Date result = null;

        String input = readString(sc, msg);

        try {
            result = new SimpleDateFormat("dd. MM. yyyy").parse(input);
        } catch (ParseException e) {
            // NOTE: Chybu jsem odchytil a nepotřebuju ji vůbec řešit (pokud zparsování nevýjde, přejdu k vrácení null).
        }

        return result;
    }

    /**
     * Vrací volbu uživatele (boolean).
     * @param sc Instance Scanneru pro získání dat ze vstupu.
     * @param msg Zpráva která se má zobrazit uživateli.
     * @param trueValue Hodnota, která reprezentuje stav True.
     * @return Text, který uživatel zadal.
     */
    public static boolean readBoolean(Scanner sc, String msg, String trueValue) {
        String input = readString(sc, msg);

        return input.equals(trueValue);
    }
}
