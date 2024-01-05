import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Game2D extends BaseFrame{
    private Dungeon dun;
    private Player player;
    private static final int dunSizeRatio = 8;
    public Game2D(Dungeon dun, Player player) {
        super("Game2D", MainGame.WID/2, MainGame.HGT/2);
        this.dun = dun;
        this.player = player;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if(dun != null)
            dun.drawDungeon2D(g);
        if(player != null)
            player.drawPlayer(g);
    }

    @Override
    public void move() {
        super.move();
        player.movePlayer(keys);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
//        dun = new Dungeon();
    }

    public static int getDunSizeRatio() {
        return dunSizeRatio;
    }
}
