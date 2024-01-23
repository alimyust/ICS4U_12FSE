package Player;
import MainGame.Music;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static MainGame.MainGame.getHGT;
import static MainGame.MainGame.getWID;
import static MainGame.MainGame.getImgDir;


public class Pistol extends Gun{
    private final BufferedImage[] fireFrame;

    {
        try {
            fireFrame = new BufferedImage[]{
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol0.png")),
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol1.png")),
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol2.png")),
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol3.png")),
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol0.png")),
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol0.png")),
                    ImageIO.read(new File(getImgDir() +"Player/Firing Pistol/firingPistol0.png")),

            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage[] reloadFrame;
    public Pistol(double frameRate, int aoe, int range, int damage) {
        super(frameRate, aoe, range, damage);
        setFireFrame(fireFrame);
        setGunSound(new Music("resources/sound/Player Sound/PistolFiring.wav"));
    }

    @Override
    public void drawGun(Graphics g) {
        super.drawGun(g);
        int scale = 3;
        int scaledWidth = getCurrentGunImage().getWidth() * scale;
        int scaledHeight = getCurrentGunImage().getHeight() * scale;
        int gunX = (int) (getWID()/2 - scaledWidth / 2 + 70 + 20*Math.cos(Math.PI * this.getOffCount()));
        int gunY = (int)(getHGT()/2 - scaledHeight / 2 + 58 + 20/2*Math.sin(-Math.PI *Math.abs( this.getOffCount())));
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getCurrentGunImage(), gunX, gunY, scaledWidth, scaledHeight, null);
    }
}