import java.awt.*;
import java.awt.geom.AffineTransform;
import java.sql.SQLOutput;
import java.util.ArrayList;

import static java.awt.Color.RED;
import static java.awt.Color.red;
import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity {
    public BaseEnemy(int x, int y, Player player) {
        super(x, y, 64, 64);
        int distFromPlayer = dist(x, player.x, y, player.y);
    }




    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
        double angleRatio = -isPlayerLookingAt(player);
        int xPos = (int) (WID / 2 * angleRatio + WID / 2);
        if (xPos < 0 || xPos > WID) return;

        double eDist = Math.abs(dist(x, y, player.x, player.y));
        double scale = Math.max(0.1, 1.0 / (eDist / 9) * 200); // Apply minimum scaling factor
        int wid = 64;
        int hgt = 128;
        int sWid = (int) (wid * scale); // Scaled width
        int sHgt = (int) (hgt * scale); // Scaled height

        // Adjust the vertical position based on the scaled height
        int yPos = 300 + hgt - sHgt;
        int yTopBound = (int) (200);  int yBotBound = (int) (HGT/2-hgt);
        yPos = Math.max(yPos, yTopBound);
        yPos = Math.min(yPos, yBotBound);
        for (int x = 0; x < sWid; x++) {
            double wDist = ray.getRayDist()[Math.min((xPos + x) / ray.getDepth(), ray.getRayDist().length - 1)];
            if (wDist < eDist) continue; // draws a ray only when an enemy is closer than a wall
            for (int y = 0; y < sHgt; y++) {
                Color col = MainGame.enemyImgArr[0][(int) (y / scale)][(int) (x / scale)];
                if (col.equals(Color.decode("#f8028a"))) continue;
                g.setColor(col);
                g.drawRect(xPos + x, yPos + y, 1, 1);
            }
        }
    }


/*        double eDist = Math.abs(dist(x, y, player.x, player.y));
        double scale = 1.0/(eDist /2)* 800;
        int wid = 64;
        int hgt = 128;
        int sWid = (int) (wid * scale); // Scaled width
        int sHgt = (int) (hgt * scale); // Scaled height

        for (int x = 0; x < wid; x++) {
            double wDist = ray.getRayDist()[Math.min((xPos + x)/ray.getDepth(), ray.getRayDist().length - 1)];
            if (wDist < eDist) continue; // draws a ray only when an enemy is closer than a wall
            for (int y = 0; y <hgt ; y++) {
                Color col = MainGame.enemyImgArr[0][y][x];
                if (col.equals(Color.decode("#f8028a"))) continue;
                g.setColor(col);
                g.drawRect((int) (xPos +x * scale), (int) (200+y), 1, 1);
            }
        }*/


    public double isPlayerLookingAt(Player player) {
        double angle = Math.atan2(y - player.y, x - player.x); //slope between player and enemy
        double playerAngle = fixAng(player.getAngle());
        double ratio = (playerAngle - angle);
        if (player.y > y)
            ratio = -(angle - playerAngle + 2 * PI);
        double angleTolerance = Math.toRadians(30);
        return ratio / angleTolerance;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    public static BaseEnemy[] addEnemy(BaseEnemy[] eArr, ArrayList<Point> spots, Player player) {
        for (int i = 0; i < eArr.length; i++) {
            int ind = (int) (Math.random() * (spots.size() - 1));
            Point currSpot = spots.get(ind);
            eArr[i] = new BaseEnemy(currSpot.x * 64, currSpot.y * 64, player);
        }
        return eArr;
    }
    private int getMapX(int mp, int wid) {
        return mp % wid * 64;
    }
    private int getMapY(int mp, int wid) {
        return mp / wid * 64;
    }

    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}
