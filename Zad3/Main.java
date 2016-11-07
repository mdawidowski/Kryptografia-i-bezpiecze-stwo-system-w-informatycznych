import java.io.*;

public class Main{

  public byte[] kluczdoascii(){
   String klucz = new String("");
   File fkey = new File("key.txt");
   try {
   try {
     Reader reader2 = new InputStreamReader(new FileInputStream(fkey),"ASCII");
     BufferedReader key = new BufferedReader(reader2);
   try {
     klucz = key.readLine();
     byte[] bytes = klucz.getBytes("US-ASCII");
     for(int i=0; i<=klucz.length()-1; i++){
       bytes[i] -= 97;
     }
     return bytes;
   } catch (UnsupportedEncodingException e) {}
   } catch (FileNotFoundException e) { System.out.println("Brak pliku z kluczem");  }
   } catch (IOException e) { System.out.println("Problem we/wy"); }
   return null;
 }

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
    byte[] klucz;
    Main start = new Main();
    switch (x[0]) {
      case "-p":
      start.przygotuj();
      break;
      case "-e":
      klucz = start.kluczdoascii();
      for(int i=0;i<=klucz.length-1;i++){
        System.out.println(klucz[i]);
      }
      //szyfrowanie

      break;
      case "-k":
      //kryptoanaliza
    }
  }
}
