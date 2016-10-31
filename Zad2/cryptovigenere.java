/*
 * Krótka i prosta implementacja szyfru Vigenera.
 * Piotr Pawliszcze
 * www.algorytm.org
 *
 */
package cryptovigenere;

import java.util.Scanner;

/**
 *
 * @author Piotr Pawliszcze
 */
public class cryptovigenere {

    private String inputString;
    private String passString;
    //tablica składa sie ze 123 elementów ponieważ odpowiednikiem znaku jest jego wartość jako integer np a=97 z=123
    //bez znaków specjalnych jedynie spacja.
    private char[][] alphabetTable = new char['z' + 1]['z' + 1];
    private Scanner console = new Scanner(System.in);

    /**
     * Glowne wejscie do programu
     */
    public static void main(String[] args) {
        cryptovigenere app = new cryptovigenere();
        app.createAhabetTable();
        app.getDataFromConsole();

    }

    /**
     * Tworzy tablice Vigenera
     */
    public void createAhabetTable() {
        System.out.println("Tworze tablice Vigenera.");

        for (char a = 'a'; a <= 'z'; a++) {
            System.out.println("");
            char b = a;
            for (int z = 'a'; z <= 'z'; z++) {
                if (b == 'z' + 1) {
                    b = 'a';
                }
                alphabetTable[a][z] = b;
                System.out.print(alphabetTable[a][z] + ",");
                b++;

            }

        }
    }

    /**
     * Pobiera dane od uzytkownika
     */
    public void getDataFromConsole() {
        System.out.println("\n\n Wybierz opcje: \n\n 1. Szyfruj\n 2. Deszyfruj\n 3. Koniec\n");
        System.out.print("#:");
        String option = console.nextLine();
        if ("1".equals(option)) {
            System.out.println("Podaj text do szyfrowania (max 255 znakow)");
            System.out.print("  text:");
            inputString = console.nextLine();
            System.out.println("\nPodaj Haslo nie krrotsze niz " + inputString.length() + "(max 255 znakow)");
            System.out.print("  haslo:");
            passString = console.nextLine();
            crypt(inputString, passString);
            getDataFromConsole();
        } else if ("2".equals(option)) {
            System.out.println("Podaj text do odszyfrowania (max 255 znakow)");
            System.out.print("  text:");
            inputString = console.nextLine();
            System.out.println("\nPodaj Haslo nie krrotsze niz " + inputString.length() + "(max 255 znakow)");
            System.out.print("  haslo:");
            passString = console.nextLine();
            decrypt(inputString, passString);
            getDataFromConsole();
        } else if ("3".equals(option)) {
            System.out.println("Koniec.....");
        } else {
            getDataFromConsole();
        }

    }

    /**
     * Szyfruj dane
     */
    public void crypt(String inputString, String passString) {
        char[] textCharTable = inputString.toCharArray();
        char[] passCharTable = passString.toCharArray();
        System.out.print("\n    Rezultat:");
        for (int index = 0; index < textCharTable.length; index++) {
            System.out.print(alphabetTable[textCharTable[index]][passCharTable[index]]);
        }

    }

    /**
     * Deszyfruj dane
     */
    public void decrypt(String inputString, String passString) {
        char[] textCharTable = inputString.toCharArray();
        char[] passCharTable = passString.toCharArray();

        System.out.print("\n    Rezultat:");
        for (int index = 0; index < textCharTable.length; index++) {
            if (textCharTable[index] != ' ') {
                for (int z = 'a'; z <= 'z'; z++) {
                    if (textCharTable[index] == alphabetTable[passCharTable[index]][z]) {
                        System.out.print(alphabetTable['a'][z]);

                    }
                }
            } else {
                System.out.print(" ");
            }
        }

    }
}
