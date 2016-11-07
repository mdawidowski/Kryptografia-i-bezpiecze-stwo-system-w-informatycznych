import java.io.*;

public class Main{

  public void przygotuj(){
    File orig = new File("orig.txt");
    File plain = new File("plain.txt");
    try {
      try {
        try {
          Reader reader = new InputStreamReader(new FileInputStream(orig),"UTF-8");
          BufferedReader fin = new BufferedReader(reader);
        } catch (UnsupportedEncodingException e) {}
      } catch (FileNotFoundException e) {}
    } catch (IOException e) {}
  }

  public static void main(String[] args) {
    String[] x = args;
    Main start = new Main();
    switch (x[0]) {
      case "-p":
      //przygotowywanie pliku
      break;
      case "-e":
      //szyfrowanie
      break;
      case "-k":
      //kryptoanaliza
    }
  }
}
