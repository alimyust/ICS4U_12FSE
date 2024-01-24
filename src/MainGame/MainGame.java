package MainGame;

import Map.Dungeon;
import Player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/* GR 12 ICS4U FSE : Escape the Dungeon
*  Author : Ali Mustafa
*
* Uses a 2.5D ray-casting engine to imitate a 3d look.
* 3 types of enemies with different properties
* 3 types of guns with different properties
* Working minimap that tracks player, enemy, and door to the next level
* 10 fixed levels with handpicked textures
* Endless mode that goes until you die.
* Randomly generated dungeon using Cellular Automata Algorithm
* Grouped classes into different packages
* Basic sounds for effects
* Control menu with buttons to look through the stats of different guns
* About 75 hours of work over December and January
* */
public class MainGame {
    private static final int WID = 64*16;
    private static final int HGT= 64*16;
    public static Player player;// = new Player(WID/2,HGT/2);;
    public static Dungeon dun;// = new Dungeon(new Point(7, 0), new Point(7, 6), new Point(7, 6), "");;
    private static final String imgDir = "resources/images/"; //easy to change path if there are issues with
    public static Game3D g3D;

    public static Color [] convertImageToColorArray(String imagePath) {
        Color[] colorArray;
        try { //converts to color array so it can be drawn using raycasting(can't display as a
//            image because the perspective changes
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
        try { // using 2d for sprites
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
