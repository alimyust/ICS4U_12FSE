import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGame {
    protected static final int WID = 64*16;
    protected static final int HGT= 64*16;
    protected static Player player = new Player(WID/2,HGT/2);;
    protected static Dungeon dun = new Dungeon(new Point(3,3),new Point(0,4),new Point(0,5),"");
    protected static final String imgDir = "resources/images/";
    protected  static Game3D g3d;
    protected  static Game2D g2d;

    protected static Color [] convertImageToColorArray(String imagePath) {
        Color[] colorArray;
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int imgHgt = image.getHeight();
            int imgWid = image.getWidth();
            colorArray = new Color[imgHgt*imgWid];
            for (int y = 0; y < imgHgt; y++) {
                for (int x = 0; x < imgWid; x++) {
                    if( y*imgHgt + x >= colorArray.length) break;
                    colorArray[y*imgHgt + x] = new Color(image.getRGB(x, y));
                }
            }
        } catch (IOException e) {
            System.out.println("what why");
            throw new RuntimeException(e);
        }
        return colorArray;
    }

    public static Color [][] convertImageTo2DColorArray(String imagePath) {
        Color[][] colorArray;
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int imgHgt = image.getHeight();
            int imgWid = image.getWidth();
            colorArray = new Color[imgHgt][imgWid];
            for (int y = 0; y < imgHgt; y++)
                for (int x = 0; x < imgWid; x++)
                    colorArray[y][x] = new Color(image.getRGB(x, y));
        } catch (IOException e) {
            System.out.println("what why");
            throw new RuntimeException(e);
        }
        return colorArray;
    }

    public static void main(String[] args) {
        g3d = new Game3D(dun,player);
        g2d = new Game2D(dun,player);
    }

}
