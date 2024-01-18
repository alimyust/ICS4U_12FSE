import java.awt.*;

public class GoblinSlinger extends BaseEnemy {

    private static final Color[][][][] imgArr = new Color[][][][]
    { new Color[][][]{ // idle
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack00.png"),
    },new Color[][][]{//run
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerRun/GoblinSlingerRun0.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerRun/GoblinSlingerRun1.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerRun/GoblinSlingerRun2.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerRun/GoblinSlingerRun3.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerRun/GoblinSlingerRun4.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerRun/GoblinSlingerRun5.png"),
    },new Color[][][]{ // attack
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack00.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack01.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack02.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack03.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack04.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack05.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack06.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack07.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack08.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack09.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack10.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack11.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack12.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack13.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack14.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack15.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack16.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack17.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack18.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack19.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerAttack/GoblinSlingerAttack20.png")
    },  new Color[][][]{ //hurt
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerHurt/GoblinSlingerHurt0.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerHurt/GoblinSlingerHurt1.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerHurt/GoblinSlingerHurt2.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerHurt/GoblinSlingerHurt3.png")
    },  new Color[][][]{ //dead
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath0.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath1.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath2.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath3.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath4.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath5.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath6.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath7.png"),
        MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/GoblinSlinger/GoblinSlingerDeath/GoblinSlingerDeath8.png"),

    }};
    public GoblinSlinger(int x, int y) {
        super(x, y);
        setStartDist(1500);
        setStopDist(600);
        setFrameRate(0.1);
        setDamage(4);
        enemyImgArr = imgArr;
}
}
