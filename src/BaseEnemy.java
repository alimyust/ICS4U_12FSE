import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity {

    public BaseEnemy(int x, int y) {
        super(x, y, 64, 64);
    }
public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
    double angleRatio = -isPlayerLookingAt(player, 30);
    Color[][] sprite = MainGame.enemyImgArr[0];

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
        if (player.y > y)
            ratio = -(angle - playerAngle + 2 * PI);
        double angleTolerance = Math.toRadians(tolerance);
        return ratio / angleTolerance;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    public static BaseEnemy[] addEnemy(BaseEnemy[] eArr, ArrayList<Point> spots) {
        for (int i = 0; i < eArr.length; i++) {
            int ind = (int) (Math.random() * (spots.size() - 1));
            Point currSpot = spots.get(ind);
            eArr[i] = new BaseEnemy(currSpot.x * 64, currSpot.y * 64);
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
