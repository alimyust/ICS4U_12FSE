package Enemies;
import MainGame.MainGame;
import java.awt.*;

import static MainGame.MainGame.getImgDir;

public class GoblinSlinger extends BaseEnemy {
    
    private static final String imgDir = getImgDir() + "Enemies/GoblinSlinger/";
    private static final Color[][][][] imgArr = new Color[][][][]
    { new Color[][][]{ // idle
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack00.png"),
    },new Color[][][]{//run
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerRun/GoblinSlingerRun0.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerRun/GoblinSlingerRun1.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerRun/GoblinSlingerRun2.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerRun/GoblinSlingerRun3.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerRun/GoblinSlingerRun4.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerRun/GoblinSlingerRun5.png"),
    },new Color[][][]{ // attack
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack00.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack01.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack02.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack03.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack04.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack05.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack06.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack07.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack08.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack09.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack10.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack11.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack12.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack13.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack14.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack15.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack16.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack17.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack18.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack19.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerAttack/GoblinSlingerAttack20.png")
    },  new Color[][][]{ //hurt
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerHurt/GoblinSlingerHurt0.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerHurt/GoblinSlingerHurt1.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerHurt/GoblinSlingerHurt2.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerHurt/GoblinSlingerHurt3.png")
    },  new Color[][][]{ //dead
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath0.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath1.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath1.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath2.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath3.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath3.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath4.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath5.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath6.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath6.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath5.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath5.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath6.png"),
        MainGame.convertImageTo2DColorArray(imgDir + "GoblinSlingerDeath/GoblinSlingerDeath6.png"),


    }};
    public GoblinSlinger(int x, int y) {
        super(x, y);
        setStartDist(800);
        setStopDist(300);
        setFrameRate(0.5);
        setDamage(2);
        setHealth(5);
        enemyImgArr = imgArr;
}
}
