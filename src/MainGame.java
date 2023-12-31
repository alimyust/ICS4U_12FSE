import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainGame {
    private static final Dungeon dun = new Dungeon();
    protected static final int WID = 64*16;
    protected static final int HGT= 64*16;
    private static final Player player = new Player(WID/2,HGT/2);
    protected static final String imgDir = "resources/images/";
    protected static final int[][] imgArr = {
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
            convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
    };
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
    public static void main(String[] args) {
        Game3D g3d = new Game3D(dun,player);
        Game2D g2d = new Game2D(dun,player);
    }
}
