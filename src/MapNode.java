import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapNode {
    private final int bCode;
    private final Color col;
    private final Color[] rainbowColors = {
            new Color(255, 255, 255),     // WHITE
            new Color(0, 0, 0),     // BLACK
            new Color(255, 0, 0),     // Red
            new Color(255, 165, 0),   // Orange
            new Color(255, 255, 0),   // Yellow
            new Color(0, 255, 0),     // Green
            new Color(0, 0, 255),     // Blue
            new Color(75, 0, 130),    // Indigo
            new Color(128, 0, 128),   // Violet
            new Color(148, 0, 211)    // Purple
    };
    private final String[] imgArr = {
            MainGame.imgDir + "redBrick.png",
            MainGame.imgDir + "redBrick.png",
            MainGame.imgDir + "redBrick.png",
            MainGame.imgDir + "redBrick.png",
            MainGame.imgDir + "redBrick.png",
            MainGame.imgDir + "redBrick.png",
            MainGame.imgDir + "redBrick.png",
    };
    int[] texture;
    public MapNode(int bCode) {
        this.bCode = bCode;
        if(bCode >= rainbowColors.length) bCode = rainbowColors.length-1;
        this.col = rainbowColors[bCode];
//        texture = convertImageToColorArray(imgArr[bCode]);
    }

    public static int[] convertImageToColorArray(String imagePath) {
        int[] colorArray = new int[64 * 64];
        System.out.println("in convertImg to col");
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));

            // Check if the image is 64x64 pixels
            if (image.getWidth() != 64 || image.getHeight() != 64)
                throw new IllegalArgumentException("Image dimensions must be 64x64 pixels.");
            // Iterate through each pixel and get the color
            for (int y = 0; y < 64; y++) {
                for (int x = 0; x < 64; x++) {
                    int rgb = image.getRGB(x, y);
                    colorArray[y * 64 + x] = rgb;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return colorArray;
    }
    public int getbCode() {
        return bCode;
    }

    public Color getCol() {
        return col;
    }
}
