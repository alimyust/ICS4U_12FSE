import java.awt.*;
import java.awt.image.BufferedImage;

public class Gun {
    private BufferedImage[] fireFrame;
    BufferedImage[] reloadFrame;
    private double gunFrame = 0;
    private final double frameRate;
    private final int aoe;
    private final int range;
    private double xOff; // cos(a * pi);
    private double yOff; //sin(- abs(a) * pi)/2

    private double offCount;
    private double offSpeed;
    private BufferedImage currentGunImage;  // Declare it as a field

    public Gun( double frameRate, int aoe, int range) {
        this.frameRate = frameRate;
        this.aoe = aoe;
        this.range = range;
        this.offSpeed = 0.1;
    }

    public void drawGun(Graphics g){
        if(Math.abs(offCount)> 1.5) {
            offSpeed = -offSpeed;
            offCount += offSpeed;
        }
        if(Player.isMoving())
            offCount += offSpeed;
        currentGunImage = fireFrame[(int) gunFrame];
        if(gunFrame > 0)
            gunFrame += frameRate;
        if(gunFrame >= fireFrame.length)
            gunFrame = 0;

    }

    public double getGunFrame() {
        return gunFrame;
    }

    public int getAoe() {
        return aoe;
    }

    public int getRange() {
        return range;
    }

    public BufferedImage getCurrentGunImage() {
        return currentGunImage;
    }

    public void setFireFrame(BufferedImage[] fireFrame) {
        this.fireFrame = fireFrame;
    }

    public double getOffCount() {
        return offCount;
    }

    public double getOffSpeed() {
        return offSpeed;
    }

    public void setOffSpeed(double offSpeed) {
        this.offSpeed = offSpeed;
    }

    public void setOffCount(double offCount) {

        this.offCount = offCount;
    }

    public void setGunFrame(double gunFrame) {
        this.gunFrame = gunFrame;
    }
}