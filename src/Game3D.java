import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Game3D extends BaseFrame {
    private static final int WID = 64 * 15;
    private static final int HGT = 64 * 12;
    private RayCaster rayCast;
    private int lvl = 0;

    public Game3D(Dungeon dun, Player player) {
        super("Game3D", WID, HGT);
        this.setLocationRelativeTo(null);
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
        MainGame.dun.geteArr().forEach(e -> e.drawBaseEnemy(g, MainGame.player, HGT, WID, rayCast));
        MainGame.player.shootAnimation(g);
    }

    @Override
    public void move() {
        super.move();
        MainGame.player.movePlayer(keys, MainGame.dun);
        MainGame.player.shootEnemies(keys,MainGame.dun);
        MainGame.player.chooseGun(keys);
        MainGame.dun.geteArr().forEach(e -> e.moveEnemy(MainGame.player,MainGame.dun));
        if(pointDist(MainGame.dun.getDoorPoint(), new Point(MainGame.player.x/64,MainGame.player.y/64)) < 2)
            refreshDungeon();
    }

    private void refreshDungeon() {
        lvl++;
        System.out.println("refresh Dungeon");
        MainGame.dun = switch (lvl){
            case 0 -> new Dungeon(new Point(3,3),new Point(0,4),new Point(0,5),"");
            case 1 -> new Dungeon(new Point(3,1),new Point(5,2),new Point(5,2),"");
            case 2 -> new Dungeon(new Point(4,5),new Point(5,4),new Point(5,4),"");
            default -> throw new IllegalStateException("Unexpected value: " + lvl);
        };
        MainGame.player = new Player(WID/2, HGT/2);
        rayCast = new RayCaster(MainGame.player,MainGame.dun);
    }

    private int pointDist(Point doorPoint, Point point) {
        return (int) Math.sqrt((point.x - doorPoint.x) * (point.x - doorPoint.x) +
                (point.y - doorPoint.y) * (point.y - doorPoint.y));

    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }
    public static boolean notIntersectingMap(int ax, int ay, int aw, MapNode[][] map){
        int mapX = ax/64;
        int mapY = ay/64;
        if (mapX >= map[0].length || mapY >= map.length) return false;
        return map[mapY][mapX].getwCode() == 0;
    }
    

}
