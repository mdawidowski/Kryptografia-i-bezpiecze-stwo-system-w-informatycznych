import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class GetSetPixels{
  public static void main(String args[])throws IOException{
    BufferedImage img = null;
    File f = null;
    int a,r,g,b,p;
    //read image
    try{
      f = new File("plain.bmp");
      img = ImageIO.read(f);
    }catch(IOException e){
      System.out.println(e);
    }

    //get image width and height
    int width = img.getWidth();
    int height = img.getHeight();

    /**
     * Since, Sample.jpg is a single pixel image so, we will
     * not be using the width and height variable in this
     * project.
     */


    /**
     * to keep the project simple we will set the ARGB
     * value to 255, 100, 150 and 200 respectively.
     */
    for (int x=0;x<=width-1; x+=2) {
      for (int y=0;y<=height-1; y+=2 ) {
        p = img.getRGB(x,y);
        a = (p>>24) & 0xff;
        r = (p>>16) & 0xff;
        g = (p>>8) & 0xff;
        b = p & 0xff;
        a=255;
        r=255;
        g=255;
        b=255;
        // System.out.println(p + " " + a + " " + r + " " + g + " " + b);
        p = (a<<24) | (r<<16) | (g<<8) | b;
        // System.out.println(p);
        img.setRGB(x, y, p);
      }
    }
    for (int x=1;x<=width-1; x+=2) {
      for (int y=1;y<=height-1; y+=2 ) {
        p = img.getRGB(x,y);
        a = (p>>24) & 0xff;
        r = (p>>16) & 0xff;
        g = (p>>8) & 0xff;
        b = p & 0xff;
        a=255;
        r=0;
        g=0;
        b=0;
        // System.out.println(p + " " + a + " " + r + " " + g + " " + b);
        p = (a<<24) | (r<<16) | (g<<8) | b;
        // System.out.println(p);
        img.setRGB(x, y, p);
      }}
    //set the pixel value

    //write image
    try{
      f = new File("plain1.bmp");
      ImageIO.write(img, "bmp", f);
    }catch(IOException e){
      System.out.println(e);
    }
  }//main() ends here
}//class ends here
