package MainGame;
import ParentEntity.ParentEntity;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Game2D extends BaseFrame{
    private static final int dunSizeRatio = 8;
    public Game2D() {
        super("MainGame.Game2D", MainGame.WID/2, MainGame.HGT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
//        MainGame.dun.drawDungeon2D(g);
//        MainGame.player.drawPlayer(g);
//        for(ParentEntity e: MainGame.dun.geteArr())
//            e.draw2d(g, Color.MAGENTA);

    }

    @Override
    public void move() {
        super.move();
//        MainGame.MainGame.player.movePlayer(keys, MainGame.MainGame.dun);
//        MainGame.MainGame.player.shootEnemies(keys, MainGame.MainGame.dun);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
//        dun = new Map.Dungeon();
    }

    public static int getDunSizeRatio() {
        return dunSizeRatio;
    }
}
