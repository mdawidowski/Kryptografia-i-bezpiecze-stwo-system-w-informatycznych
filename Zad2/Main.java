import java.io.*;
import java.util.*;

public class Main{

  private char[][] alphabetTable = new char['z' + 1]['z' + 1];

  public void createAhabetTable() {
    for (char a = 'a'; a <= 'z'; a++) {
    char b = a;
    for (int z = 'a'; z <= 'z'; z++) {
    if (b == 'z' + 1) {
    b = 'a';
    }
    alphabetTable[a][z] = b;
    b++;
    }
    }
  }

  public void przygotuj(){
    String tekst = new String("");
    File orig = new File("orig.txt");
    File plain = new File("plain.txt");
    try {
      RandomAccessFile input = new RandomAccessFile(orig, "r");
      RandomAccessFile output = new RandomAccessFile(plain, "rw");
      try {
        while (tekst != null){
          tekst = input.readLine();
          if (tekst != null) {
            tekst = tekst.replaceAll("\\W", "");
            tekst = tekst.replaceAll("\\d", "");
            tekst = tekst.toLowerCase();
            output.writeBytes(tekst + "\n");
          }
        }
        input.close();
        output.close();
      } catch (IOException e) {
        System.out.println("Problem we/wy");
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("Brak pliku");
    }
  }

  public String crypt(String inputString, String passString) {
    char[] textCharTable = inputString.toCharArray();
    char[] passCharTable = passString.toCharArray();
    int index2 = 0;
    String tekst = "";
    StringBuilder sB = new StringBuilder(tekst);
          for (int index = 0; index < textCharTable.length; index++) {
            if(index2>=passString.length()){ index2 = 0; }
            sB.append(alphabetTable[textCharTable[index]][passCharTable[index2]]);
            index2 += 1;
          }
          tekst = sB.toString();
          return tekst;

  }

  public String decrypt(String inputString, String passString) {
  char[] textCharTable = inputString.toCharArray();
  char[] passCharTable = passString.toCharArray();
  int index2 = 0;
  String tekst = "";
  StringBuilder sB = new StringBuilder(tekst);
        for (int index = 0; index < textCharTable.length; index++) {
          if(index2>=passString.length()){ index2 = 0; }
          for (int z = 'a'; z <= 'z'; z++) {
            if (textCharTable[index] == alphabetTable[passCharTable[index2]][z]) {
              sB.append(alphabetTable['a'][z]);
            }
          }
          index2 += 1;
        }
        tekst = sB.toString();
        return tekst;

  }

  public static void main(String[] args) throws FileNotFoundException{
    String[] x = args;
    Main start = new Main();
    start.createAhabetTable();
    String inputString = "",passString, cryptoString;
    File plain = new File("plain.txt");
    File key = new File("key.txt");
    File crypted = new File("crypto.txt");
    File decrypt = new File("decrypt.txt");
    try {

      try {
        try {
          Reader reader = new InputStreamReader(new FileInputStream(plain),"UTF-8");
          BufferedReader fin = new BufferedReader(reader);
          Reader czytaj = new InputStreamReader(new FileInputStream(key),"UTF-8");
          BufferedReader fkey = new BufferedReader(czytaj);
          Reader reader2 = new InputStreamReader(new FileInputStream(crypted),"UTF-8");
          BufferedReader inputcryptedString = new BufferedReader(reader2);

          passString = fkey.readLine();



          switch (x[0]) {
            case "-p":
            start.przygotuj();
            break;

            case "-e":
            String chain;
            String tekst="";
            StringBuilder sB = new StringBuilder(tekst);
            inputString = fin.readLine();
            while(inputString != null){
              chain = start.crypt(inputString, passString);
              sB.append(chain);
              sB.append("\n");
              inputString = fin.readLine();
            }
            tekst = sB.toString();
            Writer writer = new OutputStreamWriter(new FileOutputStream(crypted), "UTF-8");
            BufferedWriter fout = new BufferedWriter(writer);
            fout.write(tekst);
            fout.close();
            break;

            case "-d":
            String chain2;
            String tekst2="";
            StringBuilder sB1 = new StringBuilder(tekst2);
            cryptoString = inputcryptedString.readLine();
            while(cryptoString != null){
              chain2 = start.decrypt(cryptoString, passString);
              sB1.append(chain2);
              sB1.append("\n");
              cryptoString = inputcryptedString.readLine();
            }
            tekst2 = sB1.toString();
            Writer writer2 = new OutputStreamWriter(new FileOutputStream(decrypt), "UTF-8");
            BufferedWriter fout2 = new BufferedWriter(writer2);
            fout2.write(tekst2);
            fout2.close();
            break;

            case "-k":
            System.out.println("Funkcja niegotowa");

          }
        } catch (UnsupportedEncodingException e) {}
        } catch (FileNotFoundException e) {System.out.println("Nie można znaleźć pliku key.txt"); }
      } catch (IOException e) {
        System.out.println("Błąd we/wy");
      }
  }
}
