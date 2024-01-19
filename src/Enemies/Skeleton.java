package Enemies;
import MainGame.MainGame;
import java.awt.*;

public class Skeleton extends BaseEnemy{
    private static final Color[][][][] imgArr = new Color[][][][]
            { new Color[][][]{ // idle
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack0.png"),
            },new Color[][][]{//run
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonRun/SkeletonRun0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonRun/SkeletonRun1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonRun/SkeletonRun2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonRun/SkeletonRun3.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonRun/SkeletonRun4.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonRun/SkeletonRun5.png"),
            },new Color[][][]{ // attack
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack3.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack4.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack5.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack6.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonAttack/SkeletonAttack7.png"),
            },  new Color[][][]{ //hurt
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonHurt/SkeletonHurt0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonHurt/SkeletonHurt1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonHurt/SkeletonHurt2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonHurt/SkeletonHurt3.png")
            },  new Color[][][]{ //dead
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath3.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath4.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath5.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath6.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/Skeleton/SkeletonDeath/SkeletonDeath7.png"),
            }};

    public Skeleton(int x, int y) {
        super(x, y);
        setStartDist(800);
        setStopDist(100);
        setFrameRate(0.5);
        setDamage(2);
        setHealth(2);
        enemyImgArr = imgArr;
    }
}
