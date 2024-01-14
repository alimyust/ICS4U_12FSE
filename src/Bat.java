import java.awt.*;

public class Bat extends BaseEnemy{
    public Bat(int x, int y) {
        super(x, y);
        setStartDist(500);
        setStopDist(100);
        setFrameRate(0.3);
        enemyImgArr = new Color[][][] {
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat0.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat1.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat2.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat3.png")
        };
    }
}
