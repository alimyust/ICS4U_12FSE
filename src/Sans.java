import java.awt.*;

public class Sans extends BaseEnemy{
    public Sans(int x, int y) {
        super(x, y);
        setStartDist(900);
        setStopDist(200);
        setFrameRate(0.1);
        enemyImgArr = new Color[][][] {
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/sans/sans0.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/sans/sans1.png"),
                MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/sans/sans2.png")
        };
    }
}
