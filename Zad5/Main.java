import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main{
  public static void main(String args[])throws IOException{
    BufferedImage image = null;
    File f = null;

    try{
      f = new File("plain.bmp");
      image = ImageIO.read(f);
      int x = image.getWidth();
      int y = image.getHeight();
      System.out.println(x + " " + y);
      int color = image.getRGB(500, 500);
      color += 2;
      image.setRGB(0, 0, color);
      System.out.println(color);
    }catch(IOException e){
      System.out.println("Error: "+e);
    }

    try{
      f = new File("obraz1.bmp");
      ImageIO.write(image, "bmp", f);
    }catch(IOException e){
      System.out.println("Error: "+e);
    }
  }
}
