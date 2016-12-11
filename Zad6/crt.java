import java.io.*;

public class crt{
  public static void main(String[] args) throws IOException,UnsupportedEncodingException, FileNotFoundException {
  File odczyt = new File("uklad.txt");
  File zapis = new File("crt.txt");
  Reader reader = new InputStreamReader(new FileInputStream(odczyt),"US-ASCII");
  BufferedReader fin = new BufferedReader(reader);
  Writer writer = new OutputStreamWriter(new FileOutputStream(zapis), "US-ASCII");
  BufferedWriter fout = new BufferedWriter(writer);
  String tekst = fin.readLine(),a="",m="";
  int[] p = new int[20];
  int[] b = new int[20];
  int x=0;
  while (tekst!=null) {
    String[] part = tekst.split(" ");
    a = part[0];
    m = part[1];
    if (a.matches("[0-9]+") && a.matches("[0-9]+")) {
      p[x] = Integer.parseInt(a);
      b[x] = Integer.parseInt(m);
    }else {
      System.out.println("Znaleziono znak, który nie jest cyfrą!");
      System.exit(1);
    }
    x++;
    tekst = fin.readLine();
  }
  }
}
