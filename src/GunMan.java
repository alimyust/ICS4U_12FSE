import java.awt.*;

public class GunMan extends BaseEnemy{
    public GunMan(int x, int y) {
        super(x, y);
        setStartDist(1200);
        setStopDist(400);
        setFrameRate(0.1);
        setDamage(2);

        enemyImgArr = new Color[][][] {
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GunMan/Running/runningGunMan0.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GunMan/Running/runningGunMan1.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GunMan/Running/runningGunMan2.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GunMan/Running/runningGunMan3.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GunMan/Running/runningGunMan4.png"),
        };
    }
}
