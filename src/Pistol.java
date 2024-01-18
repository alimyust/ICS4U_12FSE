import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pistol extends Gun{
    private BufferedImage[] fireFrame;
    private double xOff; // cos(a * pi);
    private double yOff; //sin(- abs(a) * pi)/2

    private double offCount;
    private double offSpeed;
    {
        try {
            fireFrame = new BufferedImage[]{
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol1.png")),
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol2.png")),
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol3.png")),
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),
                    ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),

            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage[] reloadFrame;
    public Pistol(double frameRate, int aoe, int range) {
        super(frameRate, aoe, range);
        setFireFrame(fireFrame);
    }

    @Override
    public void drawGun(Graphics g) {
        super.drawGun(g);
        int scale = 3;
        int scaledWidth = getCurrentGunImage().getWidth() * scale;
        int scaledHeight = getCurrentGunImage().getHeight() * scale;
        int gunX = (int) (MainGame.WID/2 - scaledWidth / 2 + 70 + 20*Math.cos(Math.PI * this.getOffCount()));
        int gunY = (int)(MainGame.HGT/2 - scaledHeight / 2 + 58 + 20/2*Math.sin(-Math.PI *Math.abs( this.getOffCount())));
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getCurrentGunImage(), gunX, gunY, scaledWidth, scaledHeight, null);
    }
}