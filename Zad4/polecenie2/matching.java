//dane są argumentami programu
public class matching{
  public static void main(String[] args) {
    String x = args[0] ,y = args[1];
    float roznice = 0, dlugosc=x.length();
    char[] xarray = x.toCharArray();
    char[] yarray = y.toCharArray();
    for (int z=0; z <= dlugosc-1; z++) {
      if(xarray[z]!=yarray[z]){
        roznice +=1;
      }
    }
    if(roznice>0){
      float procent = roznice/dlugosc;
      procent *= 100;
      int x1,y1;
      System.out.print("Liczba bitow rozniaca wyniki: " + roznice + " tj. ");
      System.out.format("%.2f", procent);
      System.out.println("%  z " + dlugosc);
    }
  }
}
