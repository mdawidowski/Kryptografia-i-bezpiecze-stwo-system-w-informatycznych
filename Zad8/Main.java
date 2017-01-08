import javafx.util.converter.BigIntegerStringConverter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String input = "wejscie.txt";
    private static final String output = "wyjscie.txt";

    public static void main(String[] args) {

        // TEST FERMATA
        if (args.length == 1 && args[0].equals("-f")) {
            String wejscie = czytajPlik(input);
            List<BigInteger> lista = konwertujStringNaLiczby(wejscie);
            String wynik = testFermata(lista.get(0));

            System.out.println(wynik);
            zapis(output, wynik);

            return;
        }

        // RABIN-MILLER
        if (args.length == 0) {
            String wejscie = czytajPlik(input);
            List<BigInteger> lista = konwertujStringNaLiczby(wejscie);
            String wynik = rabinMiller(lista);

            System.out.println("Wynik zapisano w pliku wyjscie.txt");
            zapis(output, wynik);

            return;
        }

        // ERROR
        System.out.println("Niewlasciwa komenda, dostepne wywolania:\n java RabinMiller -f\n java RabinMiller");
    }

    private static String testFermata(BigInteger liczba) {
        BigInteger k = BigInteger.ZERO;
        BigInteger b_before = BigInteger.ZERO;
        BigInteger a = BigInteger.valueOf(randInt(2, liczba.subtract(BigInteger.ONE).intValue()));
        BigInteger m = liczba.subtract(BigInteger.ONE);
        BigInteger bj = a.modPow(m, liczba);

        if (bj.compareTo(BigInteger.ONE) != 0) {
            return "prawdopodobnie zlozona";
        }

        return "brak pewnosci, dla a =" + a.toString();
    }

    private static String rabinMiller(List<BigInteger> listaLiczb) {
        if (listaLiczb.size() == 3) {
            BigInteger liczba1 = listaLiczb.get(0);
            BigInteger liczba3 = listaLiczb.get(2);
            BigInteger liczba2 = (listaLiczb.get(1).multiply(liczba3)).subtract(BigInteger.ONE);

            boolean pierwsza;

            for (int i = 0; i < 40; i++) {
                BigInteger k = BigInteger.ZERO;
                BigInteger b_before = BigInteger.ZERO;
                pierwsza = true;
                BigInteger a = BigInteger.valueOf(randInt(2, liczba1.subtract(BigInteger.ONE).intValue()));
                if (a.gcd(liczba1).compareTo(BigInteger.ONE) != 0) {
                    BigInteger ret = a.gcd(liczba1);
                    return ret.toString();
                }
                BigInteger m = liczba2;
                while (m.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ONE) != 0) {
                    k = k.add(BigInteger.ONE);
                    m = m.divide(BigInteger.valueOf(2));
                }

                BigInteger bj = a.modPow(m, liczba1);

                if (bj.compareTo(BigInteger.ONE) == 0 || bj.compareTo(liczba1.subtract(BigInteger.ONE)) == 0) {
                    continue;
                }

                for (BigInteger j = BigInteger.ZERO; k.compareTo(j) > 0; j.add(BigInteger.ONE)) {
                    BigInteger bj_before = bj;
                    bj = bj_before.modPow(BigInteger.valueOf(2), liczba1);
                    if (bj.compareTo(BigInteger.ONE) == 0 && pierwsza) {
                        b_before = bj_before;
                        pierwsza = false;
                        break;
                    }
                }

                BigInteger ret = (b_before.subtract(BigInteger.ONE)).gcd(liczba1);
                if (ret.compareTo(BigInteger.ONE) != 0) {
                    return ret.toString();
                }
            }
            return "prawdopodobnie pierwsza";
        } else if (listaLiczb.size() == 2) {
            BigInteger liczba1 = listaLiczb.get(0);
            BigInteger liczba2 = listaLiczb.get(1);

            for (int i = 0; i < 40; i++) {
                BigInteger k = BigInteger.ZERO;
                BigInteger b_before = BigInteger.ZERO;
                boolean pierwsza = true;
                BigInteger a = BigInteger.valueOf(randInt(2, liczba1.subtract(BigInteger.ONE).intValue()));
                if (a.gcd(liczba1).compareTo(BigInteger.ONE) != 0) {
                    BigInteger ret = a.gcd(liczba1);
                    return ret.toString();
                }
                BigInteger m = liczba2;
                while (m.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ONE) != 0) {
                    k = k.add(BigInteger.ONE);
                    m = m.divide(BigInteger.valueOf(2));
                }
                BigInteger bj = a.modPow(m, liczba1);
                if (bj.compareTo(BigInteger.ONE) != 0) {
                    return "liczba r:" + liczba2.toString() + " nie jest wykladnikiem uniwersalnym: (" + a.toString() + "^" + liczba2.toString() + ") mod " + liczba1.toString() + " = " + bj.toString();
                }
                if (bj.compareTo(BigInteger.ONE) == 0 || bj.compareTo(liczba1.subtract(BigInteger.ONE)) == 0) {
                    continue;
                }
                for (BigInteger j = BigInteger.ZERO; k.compareTo(j) > 0; j.add(BigInteger.ONE)) {
                    BigInteger bj_before = bj;
                    bj = bj.modPow(BigInteger.valueOf(2), liczba1);
                    if (bj.compareTo(BigInteger.ONE) == 0 && pierwsza) {
                        b_before = bj_before;
                        pierwsza = false;
                        break;
                    }
                }

                BigInteger ret = (b_before.subtract(BigInteger.ONE)).gcd(liczba1);
                if (ret.compareTo(BigInteger.ONE) != 0) {
                    return ret.toString();
                }

            }
            return "prawdopodobnie pierwsza";
        } else if (listaLiczb.size() == 1) {
            BigInteger liczba1 = listaLiczb.get(0);

            for (int i = 0; i < 40; i++) {
                BigInteger k = BigInteger.ZERO;
                BigInteger b_before = BigInteger.ZERO;
                boolean pierwsza = true;
                BigInteger a = BigInteger.valueOf(randInt(2, liczba1.subtract(BigInteger.ONE).intValue()));
                if (a.gcd(liczba1).compareTo(BigInteger.ONE) != 0) {
                    BigInteger ret = a.gcd(liczba1);
                    return ret.toString();
                }
                BigInteger m = liczba1.subtract(BigInteger.ONE);
                while (m.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ONE) != 0) {
                    k = k.add(BigInteger.ONE);
                    m = m.divide(BigInteger.valueOf(2));
                }
                BigInteger bj = a.modPow(m, liczba1);
                if (bj.compareTo(BigInteger.ONE) == 0 || bj.compareTo(liczba1.subtract(BigInteger.ONE)) == 0) {
                    continue;
                }
                for (BigInteger j = BigInteger.ZERO; k.compareTo(j) > 0; j.add(BigInteger.ONE)) {
                    BigInteger bj_before = bj;
                    bj = bj.modPow(BigInteger.valueOf(2), liczba1);
                    if (bj.compareTo(BigInteger.ONE) == 0 && pierwsza) {
                        b_before = bj_before;
                        pierwsza = false;
                        break;
                    }
                }

                if (bj.compareTo(BigInteger.ONE) != 0) {
                    return "na pewno zlozona";
                } else {
                    if ((b_before.subtract(liczba1)).compareTo(BigInteger.ONE.negate()) != 0) {
                        BigInteger ret = (b_before.subtract(BigInteger.ONE)).gcd(liczba1);
                        return ret.toString();
                    }
                }


            }
            return "prawdopodobnie pierwsza";
        }

        return "blad";
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();

        int next = (max - min) + 1;

        int randomNum = rand.nextInt(next > 0 ? next : 1) + min;

        return randomNum;
    }

    private static String czytajPlik(String nazwaPliku) {
        try {
            return new String(Files.readAllBytes(Paths.get(nazwaPliku))).trim();
        } catch (IOException e) {
            throw new RuntimeException("Nie znaleziono pliku wejsciowego!");
        }
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

    private static void zapis(String nazwaPliku, String tekst) {
        try {
            Files.write(Paths.get(nazwaPliku), tekst.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
