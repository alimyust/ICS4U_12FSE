import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

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
        rayCast.drawRays3d(g2d);
        for (BaseEnemy e : dun.geteArr())
            e.drawBaseEnemy(g, player, HGT, WID, rayCast);
    }

    @Override
    public void move() {
        super.move();
        player.movePlayer(keys, dun);
        player.shootEnemies(keys,dun);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }
    public boolean isIntersecting(int ax, int ay, int aw, int bx, int by, int bw){
        Shape aRect = new Rectangle2D.Double(ax,ay,aw,aw);
        return aRect.intersects(new Rectangle(bx,by,bw,bw));
    }
    public static boolean isIntersectingMap(int ax, int ay, int aw, MapNode[][] map){
        int mapX = ax/64;
        int mapY = ay/64;
        return map[mapY][mapX].getwCode() == 0;
    }
    

}
