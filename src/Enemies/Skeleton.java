package Enemies;
import MainGame.MainGame;
import java.awt.*;

import static MainGame.MainGame.getImgDir;

public class Skeleton extends BaseEnemy{
    private static final String imgDir = getImgDir() + "Enemies/Skeleton/";

    private static final Color[][][][] imgArr = new Color[][][][]
            { new Color[][][]{ // idle
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack0.png"),
            },new Color[][][]{//run
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonRun/SkeletonRun0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonRun/SkeletonRun1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonRun/SkeletonRun2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonRun/SkeletonRun3.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonRun/SkeletonRun4.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonRun/SkeletonRun5.png"),
            },new Color[][][]{ // attack
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack3.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack4.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack5.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack6.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonAttack/SkeletonAttack7.png"),
            },  new Color[][][]{ //hurt
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonHurt/SkeletonHurt0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonHurt/SkeletonHurt1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonHurt/SkeletonHurt2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonHurt/SkeletonHurt3.png")
            },  new Color[][][]{ //dead
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath3.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath4.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath5.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath6.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath7.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath7.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath7.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "SkeletonDeath/SkeletonDeath7.png"),

            }};

    public Skeleton(int x, int y) {
        super(x, y);
        setStartDist(800);
        setStopDist(100);
        setFrameRate(0.5);
        setDamage(3);
        setHealth(4);
        enemyImgArr = imgArr;
    }
}
