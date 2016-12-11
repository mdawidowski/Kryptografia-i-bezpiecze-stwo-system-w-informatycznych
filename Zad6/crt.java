import java.io.*;

public class crt{
  public static int NWD(int a,int b){
    int c;
    while(b != 0)
    {
        c = a % b;
        a = b;
        b = c;
    }
    return a;
}

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
    int x=0,mzero,azero;
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
      tekst = fin.readLine();
      int check = NWD(p[x],b[x]);
      if (check != 1) {
        System.out.println("Cyfry nie są względnie pierwsze parami");
      }
      x++;
    }
    mzero = 1;
    azero = 0;
    for (int z=0; z<x; z++) {
      mzero *= b[z];
      azero += p[z] % b[z];
    }
    System.out.println(azero + " " + mzero);
    fin.close();
    fout.write(mzero + " " + azero);
    fout.close();
  }
}
