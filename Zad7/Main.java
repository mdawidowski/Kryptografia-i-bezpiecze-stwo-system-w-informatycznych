import javafx.util.converter.BigIntegerStringConverter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String ELGAMAL = "elgamal.txt";
    private static final String PRIVATE = "private.txt";
    private static final String PUBLIC = "public.txt";
    private static final String PLAIN = "plain.txt";
    private static final String MESSAGE = "message.txt";
    private static final String CRYPTO = "crypto.txt";
    private static final String DECRYPT = "decrypt.txt";
    private static final String SIGNATURE = "signature.txt";
    private static final String VERIFY = "verify.txt";


    public static void main(String[] args) {

        // blad
        if (args.length > 1 || args.length == 0) {
            drukujOgolnyBlad();
            return;
        }

        // generuje liczbê pierwsza p, generator g i zapisuje je jako kolejne wiersze w pliku elgamal.txt
        if (args[0].equals("-g")) {
            String wyjscie = "1665997633093155705263923663680487185948531888850484859473375695734301776192932338784530163"
                    + "\n" + "170057347237941209366519667629336535698946063913573988287540019819022183488419112350737049";

            zapiszDoPliku(ELGAMAL, wyjscie);
        }

        // czyta z powyzszego pliku i generuje pare kluczy zapisanych w plikach private.txt oraz public.txt.
        if (args[0].equals("-k")) {
            List<BigInteger> liczby = czytajPlikDoBigInt(ELGAMAL, 2);

            BigInteger p = liczby.get(0);
            BigInteger g = liczby.get(1);

            BigInteger b = randomBigInt(BigInteger.ONE, p.subtract(BigInteger.ONE));
            BigInteger beta = g.modPow(b, p);

            String publicString = p.toString() + "\n" + g.toString() + "\n" + beta.toString();
            zapiszDoPliku(PUBLIC, publicString);
            String privateString = p.toString() + "\n" + g.toString() + "\n" + b.toString();
            zapiszDoPliku(PRIVATE, privateString);
        }

        // odczytuje klucz publiczny, nastepnie odczytuje wiadomosc z pliku plain.txt i zapisuje zaszyfrowana wiadomosc w pliku crypto.txt.
        // Jesli warunek m < p nie jest spelniony, sygnalizuje blad.
        if (args[0].equals("-e")) {
            List<BigInteger> publicKey = czytajPlikDoBigInt(PUBLIC, 3);

            List<BigInteger> plain = czytajPlikDoBigInt(PLAIN, 1);

            BigInteger p = publicKey.get(0);
            BigInteger g = publicKey.get(1);
            BigInteger beta = publicKey.get(2);

            BigInteger m = plain.get(0);

            if (m.compareTo(p) > 0) {
                System.out.println("Wartosc z pliku " + PLAIN + " jest wieksza, niz liczba pierwsza z " + PUBLIC);
                return;
            }

            BigInteger k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            while (k.gcd(p).compareTo(BigInteger.ONE) != 0) {
                k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            }

            BigInteger r = g.modPow(k, p);
            BigInteger t = (m.multiply(beta.modPow(k, p))).mod(p);

            String crypto = r.toString() + "\n" + t.toString();
            zapiszDoPliku(CRYPTO, crypto);
        }

        // odczytuje klucz prywatny i kryptogram, a odszyfrowana wiadomosc zapisuje w pliku decrypt.txt.
        if (args[0].equals("-d")) {
            List<BigInteger> privateKey = czytajPlikDoBigInt(PRIVATE, 3);

            List<BigInteger> crypto = czytajPlikDoBigInt(CRYPTO, 2);

            BigInteger p = privateKey.get(0);
            BigInteger g = privateKey.get(1);
            BigInteger b = privateKey.get(2);

            BigInteger r = crypto.get(0);
            BigInteger t = crypto.get(1);

            BigInteger mod = r.modPow(b, p);
            BigInteger m = (mod.modInverse(p).multiply(t)).mod(p);

            String decrypt = m.toString();
            zapiszDoPliku(DECRYPT, decrypt);
        }

        // odczytuje klucz prywatny, nastêpnie odczytuje wiadomosc z pliku message.txt i produkuje podpis, czyli dwa wiersze zapisane do pliku signature.txt.
        if (args[0].equals("-s")) {
            List<BigInteger> privateKey = czytajPlikDoBigInt(PRIVATE, 3);

            List<BigInteger> message = czytajPlikDoBigInt(MESSAGE, 1);

            BigInteger p = privateKey.get(0);
            BigInteger g = privateKey.get(1);
            BigInteger b = privateKey.get(2);

            BigInteger m = message.get(0);

            if (m.compareTo(p) > 0) {
                System.out.println("Wartosc z pliku " + MESSAGE + " jest wieksza, niz liczba pierwsza z " + PUBLIC);
                return;
            }

            BigInteger k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            while (k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) != 0) {
                k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            }

            BigInteger r = g.modPow(k, p);
            BigInteger x = ((m.subtract(b.multiply(r))).multiply(k.modInverse(p.subtract(BigInteger.ONE)))).mod(p.subtract(BigInteger.ONE));

            String signature = r.toString() + "\n" + x.toString();
            zapiszDoPliku(SIGNATURE, signature);
        }

        // odczytuje klucz publiczny, wiadomosc z pliku message.txt oraz podpis z pliku signature.txt i weryfikuje ten podpis.
        // Wynik weryfikacji jest wyswietlany na ekranie ale rowniez zapisywany w pliku verify.txt.
        if (args[0].equals("-v")) {
            List<BigInteger> publicKey = czytajPlikDoBigInt(PUBLIC, 3);

            List<BigInteger> message = czytajPlikDoBigInt(MESSAGE, 1);

            List<BigInteger> signature = czytajPlikDoBigInt(SIGNATURE, 2);

            BigInteger p = publicKey.get(0);
            BigInteger g = publicKey.get(1);
            BigInteger beta = publicKey.get(2);

            BigInteger m = message.get(0);

            BigInteger r = signature.get(0);
            BigInteger x = signature.get(1);

            BigInteger a = g.modPow(m, p);
            BigInteger b = (r.modPow(x, p).multiply(beta.modPow(r, p))).mod(p);

            if (a.compareTo(b) == 0) {
                String poprawny = "Podpis zweryfikowany pomyslnie";
                System.out.println(poprawny);
                zapiszDoPliku(VERIFY, poprawny);
            } else {
                String niepoprawny = "Podpis jest niepoprawny!";
                System.out.println(niepoprawny);
                zapiszDoPliku(VERIFY, niepoprawny);
            }
        }
    }

    private static void drukujOgolnyBlad() {
        System.out.println("Niewlasciwa komenda, przykladowe wywolanie:\n java ElGamal -k");
    }

    private static void sprawdzWynikCzytania(String nazwaPliku, List<BigInteger> listaDoSprawdzenia, int iloscliczb) {
        if (listaDoSprawdzenia.size() != iloscliczb) {
            System.out.println("Plik " + nazwaPliku + " nie zawiera " + iloscliczb + " liczb.");
            System.exit(0);
        }
    }

    public static BigInteger randomBigInt(BigInteger min, BigInteger max) {
        Random rnd = new Random();
        do {
            BigInteger i = new BigInteger(max.bitLength(), rnd);
            if (i.compareTo(max) <= 0) {

                if (i.compareTo(min) > 0) {
                    return i;
                } else {
                    randomBigInt(min, max);
                }
            }
        } while (true);
    }

    private static String czytajPlik(String nazwaPliku) {
        try {
            return new String(Files.readAllBytes(Paths.get(nazwaPliku))).trim();
        } catch (IOException e) {
            throw new RuntimeException("Nie znaleziono pliku wejsciowego!");
        }
    }

    private static List<BigInteger> czytajPlikDoBigInt(String nazwaPliku, int ileLiczb) {
        String plikString = czytajPlik(nazwaPliku);
        List<BigInteger> liczby = konwertujStringNaLiczby(plikString);
        sprawdzWynikCzytania(nazwaPliku, liczby, ileLiczb);

        return liczby;
    }

    private static List<BigInteger> konwertujStringNaLiczby(String string) {
        String stringLiczby = "";

        for (char znak : string.toCharArray()) {
            if (Character.isDigit(znak)) {
                stringLiczby += znak;
            } else {
                stringLiczby += " ";
            }
        }

        List<BigInteger> listaBigInt = new ArrayList<>();
        BigIntegerStringConverter bigIntegerStringConverter = new BigIntegerStringConverter();

        for (String liczbaString : stringLiczby.trim().replaceAll(" +", " ").split(" ")) {
            listaBigInt.add(bigIntegerStringConverter.fromString(liczbaString));
        }

        return listaBigInt;
    }

    private static void zapiszDoPliku(String nazwaPliku, String tekst) {
        try {
            Files.write(Paths.get(nazwaPliku), tekst.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
