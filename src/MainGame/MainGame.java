package MainGame;

import Map.Dungeon;
import Player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGame {
    private static final int WID = 64*16;
    private static final int HGT= 64*16;
    public static Player player = new Player(WID/2,HGT/2);;
    public static Dungeon dun = new Dungeon(new Point(7, 0), new Point(7, 6), new Point(7, 6), "");;
    private static final String imgDir = "resources/images/";
    public static Game3D g3D;

    public static Color [] convertImageToColorArray(String imagePath) {
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
    public static String getImgDir() {
        return imgDir;
    }
    public static int getWID() {
        return WID;
    }

    public static int getHGT() {
        return HGT;
    }

    public static void main(String[] args) {
        g3D = new Game3D();
    }

}
