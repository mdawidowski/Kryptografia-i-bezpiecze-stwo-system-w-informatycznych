import java.io.*;

public class Main{

  public void przygotuj(){
    File orig = new File("orig.txt");
    File plain = new File("plain.txt");
    String tekst = "", all = "";
    StringBuilder sB = new StringBuilder(all);
    try {
      try {
        try {
          Reader reader = new InputStreamReader(new FileInputStream(orig),"ASCII");
          BufferedReader fin = new BufferedReader(reader);
          Writer writer = new OutputStreamWriter(new FileOutputStream(plain), "UTF-8");
          BufferedWriter fout = new BufferedWriter(writer);
          tekst = fin.readLine();
          while(tekst!=null){
            tekst = tekst.replaceAll("[,.!?:;'-0123456789]", "");
            tekst = tekst.toLowerCase();
            System.out.println(tekst);
            sB.append(tekst);
            sB.append("\n");
            tekst = fin.readLine();
          }
          all = sB.toString();
          fout.write(all);
          fin.close();
          fout.close();
        } catch (UnsupportedEncodingException e) {}
      } catch (FileNotFoundException e) {}
    } catch (IOException e) {}
  }

  public static void main(String[] args) {
    String[] x = args;
    Main start = new Main();
    switch (x[0]) {
      case "-p":
      start.przygotuj();
      break;
      case "-e":
      //szyfrowanie
      break;
      case "-k":
      //kryptoanaliza
    }
  }
}
