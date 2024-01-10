import java.awt.*;
import java.awt.geom.AffineTransform;
import java.sql.SQLOutput;
import java.util.ArrayList;

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
        int scale = 400 * 64;

        int wid;
        int hgt;

        // Set the original width and height
        int originalWidth = 64;
        int originalHeight = 128;

        // Set the distance threshold for constant size
        double constantSizeThreshold = 100.0;

        if (eDist < constantSizeThreshold) {
            // Keep the original size up to the threshold
            wid = originalWidth;
            hgt = originalHeight;
        } else {
            // Scale the size beyond the threshold
            wid = (int) (1.0 / eDist * scale * 2);
            hgt = (int) (1.0 / eDist * scale * 4);
        }

//        AffineTransform transform = new AffineTransform();
//        transform.translate(xPos - wid / 2, HGT / 2);
//        transform.scale((double) wid / originalWidth, (double) hgt / originalHeight);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(ray.getDepth()));
        for(int y=0; y< hgt; y++){
            for(int x=0; x< 64/ray.getDepth(); x++){
                int rDist = ray.getRayDist()[Math.min(xPos / ray.getDepth() + x*ray.getDepth(), ray.getRayDist().length - 1)];
                if (eDist < rDist) {
                    g.setColor(Color.red);
                    System.out.println(x);
                    g.drawLine(xPos +x * ray.getDepth(),HGT/2,
                            xPos +x * ray.getDepth(),HGT/2 + 40);
                }
            }
        }
//        for (int y = 0; y < originalHeight; y++) {
//            for (int x = 0; x < originalWidth; x++) {
//                int rDist = ray.getRayDist()[Math.min(xPos / ray.getDepth() + x / ray.getDepth(), ray.getRayDist().length - 1)];
//                if (eDist < rDist) {
//                    int imageX = (int) (x * wid/originalWidth );
//                    int imageY = (int) (y * hgt/originalHeight);
//                    g.setColor(MainGame.enemyImgArr[0][imageY][imageX]);
//                    g2d.setTransform(transform);
//                    g2d.drawLine(x, y, x, y);
//                }
//            }
//        }
    }


//        for (int y = 0; y < hgt ; y++) {
//            for (int x = 0; x < wid; x++) {
//                System.out.println(x + "," + y);
//                g.setColor(MainGame.enemyImgArr[0][y][x]);
//                g.fillRect(x, y, 1, 1);
//            }
//        }
//        for(int i = 0; i < wid; i++) {
//            int rDist = ray.getRayDist()[Math.min(xPos / ray.getDepth() + i, ray.getRayDist().length - 1)];
//            if (eDist < rDist) {
//                for(int j=0; j < 128; j++) {
//                    g.setColor(MainGame.enemyImgArr[0][Math.min(i * 128/(wid)*64+j,8191)]);
//                    g.drawLine(xPos -wid/2 + i , HGT/2 + j,
//                            xPos -wid/2 + i , HGT/2 + j);
//                }
//            }
//        }
//    }
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
