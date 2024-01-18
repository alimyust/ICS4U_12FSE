import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shotgun extends Gun {
    private BufferedImage[] fireFrame = {
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun00.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun01.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun02.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun03.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun04.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun05.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun06.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun07.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun08.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun09.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun10.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun11.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun12.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun13.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun14.png")),
            ImageIO.read(new File(MainGame.imgDir + "Player/Firing Shotgun/firingShotgun00.png")),
    };
    private BufferedImage[] reloadFrame;

    public Shotgun(double frameRate, int aoe, int range) throws IOException {
        super(frameRate, aoe, range);
        setFireFrame(fireFrame);
    }

    @Override
    public void drawGun(Graphics g) {
        super.drawGun(g);
        int scale = 3;
        int scaledWidth = getCurrentGunImage().getWidth() * scale;
        int scaledHeight = getCurrentGunImage().getHeight() * scale;
        int gunX = (int) (MainGame.WID / 2 - scaledWidth / 2 + 70 + 40 * Math.cos(Math.PI * this.getOffCount()));
        int gunY = (int) (MainGame.HGT / 2 - scaledHeight / 2 + 58 + 40 / 2 * Math.sin(-Math.PI * Math.abs(this.getOffCount())));
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getCurrentGunImage(), gunX, gunY, scaledWidth, scaledHeight, null);
    }
}
