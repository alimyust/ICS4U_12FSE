import java.awt.image.BufferedImage;

public class Sprite {
    private double x;
    private double y;
    private BufferedImage texture; // Use BufferedImage for the sprite texture
    private int width;
    private int height;

    public Sprite(double x, double y, BufferedImage texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}