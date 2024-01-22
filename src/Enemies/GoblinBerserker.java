package Enemies;
import MainGame.MainGame;

import java.awt.*;

public class GoblinBerserker extends BaseEnemy{
    private static final Color[][][][] imgArr = new Color[][][][]
            { new Color[][][]{ // idle
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack00.png"),
            },new Color[][][]{//run
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerRun/GoblinBerserkerRun0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerRun/GoblinBerserkerRun1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerRun/GoblinBerserkerRun2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerRun/GoblinBerserkerRun3.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerRun/GoblinBerserkerRun4.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerRun/GoblinBerserkerRun5.png"),

            },new Color[][][]{ // attack
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack00.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack01.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack02.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack03.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack04.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack05.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack06.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack07.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack08.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerAttack/GoblinBerserkerAttack09.png"),


            },  new Color[][][]{ //hurt
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerHurt/GoblinBerserkerHurt0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerHurt/GoblinBerserkerHurt1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerHurt/GoblinBerserkerHurt2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerHurt/GoblinBerserkerHurt3.png"),

            },  new Color[][][]{ //dead
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath0.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath1.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath2.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath3.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath4.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath5.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath6.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath7.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),
                    MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinBerserker/GoblinBerserkerDeath/GoblinBerserkerDeath8.png"),




            }};
    public GoblinBerserker(int x, int y) {
        super(x, y);
        setStartDist(400);
        setStopDist(100);
        setFrameRate(0.5);
        setDamage(4);
        setHealth(5);
        enemyImgArr = imgArr;
    }
}
