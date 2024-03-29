package Player;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import MainGame.Music;
import static MainGame.MainGame.getHGT;
import static MainGame.MainGame.getWID;
import static MainGame.MainGame.getImgDir;


public class Shotgun extends Gun {


    private final BufferedImage[] fireFrame;

    {
        try {
            fireFrame = new BufferedImage[]{
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun00.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun01.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun02.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun03.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun04.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun05.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun06.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun07.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun08.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun09.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun10.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun11.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun12.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun13.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun14.png")),
                    ImageIO.read(new File(getImgDir() + "Player/Firing Shotgun/firingShotgun00.png")),
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage[] reloadFrame;

    public Shotgun(double frameRate, int aoe, int range, int damage) {
        super(frameRate, aoe, range,damage);
        setFireFrame(fireFrame);
        setGunSound(new Music("resources/sound/Player Sound/ShotgunFire.wav"));

    }

    @Override
    public void drawGun(Graphics g) {
        super.drawGun(g);
        int scale = 3;
        int scaledWidth = getCurrentGunImage().getWidth() * scale;
        int scaledHeight = getCurrentGunImage().getHeight() * scale;
        int gunX = (int) (getWID() / 2 - scaledWidth / 2 + 70 + 40 * Math.cos(Math.PI * this.getOffCount()));
        int gunY = (int) (getHGT() / 2 - scaledHeight / 2 + 58 + 40 / 2 * Math.sin(-Math.PI * Math.abs(this.getOffCount())));
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getCurrentGunImage(), gunX, gunY, scaledWidth, scaledHeight, null);
    }
}
