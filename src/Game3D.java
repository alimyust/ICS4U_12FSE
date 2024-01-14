import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class Game3D extends BaseFrame {
    private static final int WID = 64 * 15;
    private static final int HGT = 64 * 12;
    private Dungeon dun;
    private final Player player;
    private final RayCaster rayCast;
    private int lvl = 0;

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
        rayCast.drawRays3d(g2d);
        dun.geteArr().forEach(e -> e.drawBaseEnemy(g, player, HGT, WID, rayCast));
        player.shootAnimation(g);
    }

    @Override
    public void move() {
        super.move();
        player.movePlayer(keys, dun);
        player.shootEnemies(keys,dun);
        player.chooseGun(keys);
        dun.geteArr().forEach(e -> e.moveEnemy(player,dun));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }
    public boolean isIntersecting(int ax, int ay, int aw, int bx, int by, int bw){
        Shape aRect = new Rectangle2D.Double(ax,ay,aw,aw);
        return aRect.intersects(new Rectangle(bx,by,bw,bw));
    }
    public static boolean notIntersectingMap(int ax, int ay, int aw, MapNode[][] map){
        int mapX = ax/64;
        int mapY = ay/64;
        if (mapX >= map[0].length || mapY >= map.length) return false;
        return map[mapY][mapX].getwCode() == 0;
    }
    

}
