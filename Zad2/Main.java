import java.io.*;
import java.util.*;

public class Main{

  public byte[] kluczdoascii(){
    String klucz = new String("");
    File fkey = new File("key.txt");
    try {
      RandomAccessFile key = new RandomAccessFile(fkey, "r");
    try {
      klucz = key.readLine();
      byte[] bytes = klucz.getBytes("US-ASCII");
      for(int i=0; i<=klucz.length()-1; i++){
        bytes[i] -= 97;
      }
      return bytes;
    } catch (IOException e) { System.out.println("Problem we/wy"); }
    } catch (FileNotFoundException e) { System.out.println("Brak pliku z kluczem");  }
    return null;
  }

  public byte[] tekstdoascii(){
    String tekst = new String("");
    String out = new String("");
    File plain = new File("plain.txt");
    StringBuilder build = new StringBuilder(tekst);
    try {
      RandomAccessFile input = new RandomAccessFile(plain, "r");
      while (tekst != null) {
       try {
         tekst = input.readLine();
         if (tekst != null) {
           build.append(tekst);
          }
       } catch (IOException e) { System.out.println("Błąd we/wy"); }
      }
      try {
        out = build.toString();
        byte[] bytes = out.getBytes("US-ASCII");
        return bytes;
      } catch (UnsupportedEncodingException e) { System.out.println("Problem z zamianą na ASCII"); }
    } catch (FileNotFoundException e) { System.out.println("Błąd we/wy"); }
    return null;
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

  public void szyfruj(byte[] asciiklucz, byte[] asciitekst){
    int pom, z=0;
    char[] szyfr = new char[asciitekst.length];
    String tekst = new String("");
    File crypto = new File("crypto.txt");
    try {
      RandomAccessFile output = new RandomAccessFile(crypto, "rw");
        for(int x=0;x<asciitekst.length;x++){
          if(z>=asciiklucz.length){ z = 0; }
          pom = asciitekst[x] + asciiklucz[z];
          if(pom>122){ pom -= 26; }
          szyfr[x] = (char)pom;
          try {
            output.writeChar(szyfr[x]);
          } catch (IOException e) { System.out.println("Błąd we/wy"); }
          z += 1;
        }
    } catch (FileNotFoundException e) { System.out.println("Brakuj pliku crypto!"); }
  }

  public void deszyfruj(){
    System.out.println("Funkcja niegotowa");
  }

  public void krypto(){
    System.out.println("Funkcja niegotowa");
  }

  public static void main(String[] args) throws FileNotFoundException{
    String[] x = args;
    Main start = new Main();
    switch (x[0]) {
      case "-p":
      start.przygotuj();
      break;
      case "-e":
      byte[] asciiklucz = start.kluczdoascii();
      byte[] asciitekst = start.tekstdoascii();
      start.szyfruj(asciiklucz, asciitekst);
      break;
      case "-d":
      start.deszyfruj();
      break;
      case "-k":
      start.krypto();
    }
  }
}
