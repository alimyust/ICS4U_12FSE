import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureViewer extends JFrame {

    private final int texWidth = 64;
    private final int texHeight = 64;
    private final int[][] texture;

    public TextureViewer(int[][] texture) {
        this.texture = texture;
        initUI();
    }

    private void initUI() {
        setTitle("Texture Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextures(g);
            }
        };

        add(panel);

        setSize(800, 800);
        setLocationRelativeTo(null);
    }

    private void drawTextures(Graphics g) {
        int xStart = 10;
        int yStart = 10;
        int xGap = texWidth + 20;

        for (int i = 0; i < texture.length; i++) {
            drawTexture(g, xStart + i * xGap, yStart, texture[i]);
        }
    }

    private void drawTexture(Graphics g, int x, int y, int[] textureData) {
        BufferedImage img = new BufferedImage(texWidth, texHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < texWidth; i++) {
            for (int j = 0; j < texHeight; j++) {
                int color = textureData[j * texWidth + i];
                img.setRGB(i, j, color);
            }
        }

        g.drawImage(img, x, y, this);
    }

    public static void main(String[] args) {
        // Assuming 'texture' is the 2D array containing your textures
        int texWidth = 64, texHeight = 64;
        int[][] texture = new int[8][texWidth * texHeight];
        for (int x = 0; x < texWidth; x++) {
            for (int y = 0; y < texHeight; y++) {
                int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
                // int xcolor = x * 256 / texWidth;
                int ycolor = y * 256 / texHeight;
                int xycolor = y * 128 / texHeight + x * 128 / texWidth;

                texture[0][y * texWidth + x] = 65536 * 254 * (((x != y) && (x != texWidth - y))? 1:0); // flat red texture with black cross
                texture[1][y * texWidth + x] = xycolor + 256 * xycolor + 65536 * xycolor; // sloped greyscale
                texture[2][y * texWidth + x] = 256 * xycolor + 65536 * xycolor; // sloped yellow gradient
                texture[3][y * texWidth + x] = xorcolor + 256 * xorcolor + 65536 * xorcolor; // xor greyscale
                texture[4][y * texWidth + x] = 256 * xorcolor; // xor green
                texture[5][y * texWidth + x] = 65536 * 192 * (((x % 16 != 0) && (y % 16 != 0))? 1:0); // red bricks
                texture[6][y * texWidth + x] = 65536 * ycolor; // red gradient
                texture[7][y * texWidth + x] = 128 + 256 * 128 + 65536 * 128; // flat grey texture
            }
        }

        // Create and display the form
        SwingUtilities.invokeLater(() -> new TextureViewer(texture).setVisible(true));
    }
}
