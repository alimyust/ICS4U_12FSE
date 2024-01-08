import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game3D extends BaseFrame {
    private static final int WID = 64 * 15;
    private static final int HGT = 64 * 12;
    private Dungeon dun;
    private final Player player;
    private final RayCaster rayCast;

    public Game3D(Dungeon dun, Player player) {
        super("Game3D", WID, HGT);
        this.setLocationRelativeTo(null);
        this.dun = dun;
        this.player = player;
        this.rayCast = new RayCaster(player, dun);
    }

    public static int getWid3d() {
        return WID;
    }

    public static int getHgt3d() {
        return HGT;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.draw(g);
        assert rayCast != null;
        rayCast.drawRays3d(g2d);
        for (BaseEnemy e : dun.geteArr())
            e.drawBaseEnemy(g, player, HGT, WID, rayCast);
    }

    @Override
    public void move() {
        super.move();
        player.movePlayer(keys);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }
    

}
