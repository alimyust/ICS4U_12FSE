import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity {

    protected Color[][][] enemyImgArr;
    private double frame;
    private boolean isAlive;
    private final int speed = 5;
    private int stopDist;
    private int startDist;
    private double frameRate;

    protected static final int BAT = 0;
    protected static final int SANS = 1;
    protected static final int GUNMAN = 2;



    public BaseEnemy(int x, int y) {
        super(x, y, 64, 64);
        this.frame = 0;
        this.isAlive = true;
    }

    public static ArrayList<BaseEnemy> addEnemy(ArrayList<BaseEnemy> eArr, ArrayList<Point> spots, int eCount, int type) {
        for (int i = 0; i < eCount; i++) {
            int ind = (int) (Math.random() * (spots.size() - 1));
            Point currSpot = spots.get(ind);
            switch (type) {
                case BAT -> eArr.add(new Bat(currSpot.x * 64, currSpot.y * 64));
                case SANS -> eArr.add(new Sans(currSpot.x * 64, currSpot.y * 64));
                case GUNMAN -> eArr.add(new GunMan(currSpot.x * 64, currSpot.y * 64));
            }
        }
        return eArr;
    }

    public void moveEnemy(Player player, Dungeon dun) {
        double ang = Math.atan2(player.y - y, player.x - x);
        int dx = (int) (Math.cos(ang) * speed);
        int dy = (int) (Math.sin(ang) * speed);
        if (!isAlive || !Game3D.notIntersectingMap(x + dx, y + dy, w, dun.getMap())) return;
        if (playerEnemyDist(player) < startDist && playerEnemyDist(player) > stopDist) {
            x += dx;
            y += dy;
        }
    }

    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
        double angleRatio = -isPlayerLookingAt(player, ray.getFov());
        Color[][] sprite = enemyImgArr[(int) (frame % (enemyImgArr.length - 1))];
        frame += frameRate;

        int xPos = (int) (WID / 2 * angleRatio + WID / 2);
        if (xPos < 0 || xPos > WID) return;

        double eDist = Math.abs(dist(x, y, player.x, player.y));
        double scaleSpeed = 18;
        double scaleMagnitude = 30;
        double scale = Math.min(3, 1.0 / (eDist / scaleSpeed) * scaleMagnitude); // Apply minimum scaling factor

        int wid = sprite[0].length;
        int hgt = sprite.length;
        int sWid = (int) (wid * scale); // Scaled width
        int sHgt = (int) (hgt * scale); // Scaled height

        // Adjust the vertical position based on the scaled height
        int yPos = HGT / 2 - sHgt / 2;
        if (!isAlive) {
            sprite = enemyImgArr[enemyImgArr.length - 1];
            yPos += sHgt / 1.2;
        }
        for (int x = 0; x < sWid; x++) {
            double wDist = ray.getRayDist()[Math.min((xPos + x) / ray.getDepth(), ray.getRayDist().length - 1)];
            if (wDist < eDist) continue; // draws a ray only when an enemy is closer than a wall
            for (int y = 0; y < sHgt; y++) {
                Color col = sprite[(int) (y / scale)][(int) (x / scale)];
                if (col.equals(Color.decode("#f8028a"))) continue;
                g.setColor(col);
                g.drawRect(xPos + x, yPos + y, 1, 1);
            }
        }
    }


    public double isPlayerLookingAt(Player player, int tolerance) {
        double angle = Math.atan2(y - player.y, x - player.x); //slope between player and enemy
        double playerAngle = fixAng(player.getAngle());
        double ratio = (playerAngle - angle);
        if (player.y > y) ratio = -(angle - playerAngle + 2 * PI);
        double angleTolerance = Math.toRadians(tolerance);
        return ratio / angleTolerance;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
    public int playerEnemyDist(Player p) {
        return (int) Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
    }

    public void setStopDist(int stopDist) {
        this.stopDist = stopDist;
    }
    public void setStartDist(int startDist) {
        this.startDist = startDist;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

}
