import java.io.*;

public class Main{

  public void przygotuj(){
    String tekst = new String("");
    File orig = new File("orig.txt");
    File plain = new File("plain.txt");
    try {
      RandomAccessFile input = new RandomAccessFile(orig, "r");
      RandomAccessFile output = new RandomAccessFile(plain, "rw");
      try {
        tekst = "";
        while (tekst != null){
          tekst = input.readLine();
          System.out.println(tekst);
        }
      } catch (IOException e) {
        System.out.println("Problem we/wy");
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("Brak pliku");
    }
  //  tekst = input.readLine();
  }

  public void szyfruj(){
    System.out.println("Funkcja niegotowa");
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
      start.szyfruj();
      break;
      case "-d":
      start.deszyfruj();
      break;
      case "-k":
      start.krypto();
    }
  }
}
