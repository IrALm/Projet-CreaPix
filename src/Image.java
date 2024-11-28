import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Helper class to create, modify and save images.
 * @author Mathieu Vavrille
 * For example:
 * ```java
 * Image img = new Image (100, 200);
 * img.setRectangle(10, 20, 50, 30, Color.YELLOW); // Color should be imported with `import java.awt.Color`
 * img.save("test.png");
 * ```
 */
public class Image
{
  private final BufferedImage image; // The image

  /** Constructs an empty image (initially black) of width `width` and height `height` */
  public Image(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  }


  /**
   * Sets one pixel of the image.
   * WARNING : NO CHECK IS DONE. IF YOU WRITE OUTSIDE THE IMAGE IT WILL RAISE AN ERROR
   */
  public void setPixel(int x, int y, Color col) {
    image.setRGB(x,y,col.getRGB());
  }
  /**
   * Sets all the pixels in the given region to the given color.
   * WARNING : END COORDINATES EXCLUDED.
   * WARNING : NO CHECK IS DONE. IF YOU WRITE OUTSIDE THE IMAGE IT WILL RAISE AN ERROR
   */
  public void setRectangle(int startX, int endX, int startY, int endY, Color color) {
    for(int x = startX; x < endX; x++) {
      for(int y = startY; y < endY; y++) {
        setPixel(x,y,color);
      }
    }
  }

  /* La méthode ci-dessous permet de réprésenter les pixels sur une région triangulaire */
  public void setTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
    int minX = Math.max(0, Math.min(x1, Math.min(x2, x3)));
    int maxX = Math.min(image.getWidth() - 1, Math.max(x1, Math.max(x2, x3)));
    int minY = Math.max(0, Math.min(y1, Math.min(y2, y3)));
    int maxY = Math.min(image.getHeight() - 1, Math.max(y1, Math.max(y2, y3)));

    for (int x = minX; x <= maxX; x++) {
        for (int y = minY; y <= maxY; y++) {
            if (estDansTriangle(x, y, x1, y1, x2, y2, x3, y3)) {
                setPixel(x, y, color);
            }
        }
    }
  }
  private double aireDuTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
    return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
  }
  private boolean estDansTriangle(int x , int y , int x1 , int y1 , int x2 , int y2 , int x3 , int y3){

    double aireTriangleABC = aireDuTriangle(x1, y1,x2, y2, x3, y3);
    double aireTrianglePAB = aireDuTriangle(x, y, x1, y1,x2, y2);
    double aireTrianglePBC = aireDuTriangle(x, y, x2, y2, x3, y3);
    double aireTrianglePCA = aireDuTriangle(x, y, x3, y3, x1, y1);
    return (aireTriangleABC == aireTrianglePAB + aireTrianglePBC + aireTrianglePCA);
  }

  /**
   * Saves the image to a file, in PNG format
   */
  public void save(String filename) throws IOException {
    File fic = new File(filename);
    fic = new File(fic.getAbsolutePath());
    ImageIO.write(image,"png",fic);
  }

  /**
   * Number of pixels in X dimension
   */
  public int width() {
    return image.getWidth();
  }

  /**
   * Number of pixels in Y dimension
   */
  public int height() {
    return image.getHeight();
  }
  public BufferedImage getImage(){ return this.image ; }
}

