package Enemies;
import MainGame.MainGame;

import java.awt.*;

import static MainGame.MainGame.getImgDir;
import MainGame.Music;

public class GoblinBerserker extends BaseEnemy{
    private static final String imgDir = getImgDir() + "Enemies/GoblinBerserker/";

    private static final Color[][][][] imgArr = new Color[][][][]
            { new Color[][][]{ // idle
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack00.png"),
            },new Color[][][]{//run
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerRun/GoblinBerserkerRun0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerRun/GoblinBerserkerRun1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerRun/GoblinBerserkerRun2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerRun/GoblinBerserkerRun3.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerRun/GoblinBerserkerRun4.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerRun/GoblinBerserkerRun5.png"),

            },new Color[][][]{ // attack
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack00.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack01.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack02.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack03.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack04.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack05.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack06.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack07.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack08.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerAttack/GoblinBerserkerAttack09.png"),


            },  new Color[][][]{ //hurt
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerHurt/GoblinBerserkerHurt0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerHurt/GoblinBerserkerHurt1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerHurt/GoblinBerserkerHurt2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerHurt/GoblinBerserkerHurt3.png"),

            },  new Color[][][]{ //dead
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath0.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath1.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath2.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath3.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath4.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath5.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath6.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath7.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),
                    MainGame.convertImageTo2DColorArray(imgDir + "GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),




            }};
    public GoblinBerserker(int x, int y) {
        super(x, y);
        setStartDist(400);
        setStopDist(100);
        setFrameRate(0.5);
        setDamage(5);
        setHealth(35);
        setAttackFrame(7);
        setEnemyAttackSound(new Music("resources/sound/Enemy Sound/SkeletonSlash1.wav"));
        enemyImgArr = imgArr;
    }
}
