import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pistol extends Gun{
    private BufferedImage[] fireFrame = {
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol1.png")),
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol2.png")),
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol3.png")),
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),
            ImageIO.read(new File(MainGame.imgDir +"Player/Firing Pistol/firingPistol0.png")),

    };
    private BufferedImage[] reloadFrame;
    public Pistol(double frameRate, int aoe, int range) throws IOException {
        super(frameRate, aoe, range);
        setFireFrame(fireFrame);
    }

    @Override
    public void drawGun(Graphics g) {
        super.drawGun(g);
        int scale = 3;
        int scaledWidth = getCurrentGunImage().getWidth() * scale;
        int scaledHeight = getCurrentGunImage().getHeight() * scale;
        int gunX = MainGame.WID/2 - scaledWidth / 2 + 70;
        int gunY = MainGame.HGT/2 - scaledHeight / 2 + 58;
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getCurrentGunImage(), gunX, gunY, scaledWidth, scaledHeight, null);
    }
}