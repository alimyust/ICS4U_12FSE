package Player;

import MainGame.Music;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static MainGame.MainGame.*;
import static MainGame.MainGame.getImgDir;

public class SMG extends Gun{
    private final BufferedImage[] fireFrame;

    {
        try {
            fireFrame = new BufferedImage[]{
                    ImageIO.read(new File(getImgDir() + "Player/Firing SMG/SmallMachineGunFiring0.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing SMG/SmallMachineGunFiring1.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing SMG/SmallMachineGunFiring2.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing SMG/SmallMachineGunFiring3.png")),
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public SMG(double frameRate, int aoe, int range, int damage) {
        super(frameRate, aoe, range, damage);
        setFireFrame(fireFrame);
        setGunSound(new Music("resources/sound/Player Sound/SMGFire.wav"));

    }

    @Override
    public void drawGun(Graphics g) {
        super.drawGun(g);
        int scale = 3;
        int scaledWidth = getCurrentGunImage().getWidth() * scale;
        int scaledHeight = getCurrentGunImage().getHeight() * scale;
        int gunX = (int) (getWID() / 2 - scaledWidth / 2 -170 + 40 * Math.cos(Math.PI * this.getOffCount()));
        int gunY = (int) (getHGT() / 2 - scaledHeight / 2 + 70 + 40 / 2 * Math.sin(-Math.PI * Math.abs(this.getOffCount())));
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getCurrentGunImage(), gunX, gunY, scaledWidth, scaledHeight, null);
    }
}
