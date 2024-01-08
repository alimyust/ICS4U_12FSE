import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity{
    public BaseEnemy(int x, int y, Player player) {
        super(x, y, 64,64);
        int distFromPlayer = dist(x, player.x, y, player.y);
    }

    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
        Graphics2D g2d = (Graphics2D) g;
        double angleRatio = -isPlayerLookingAt(player);
        int xPos = (int) (WID/2 * angleRatio + WID/2);
        if( xPos < 0 || xPos > WID) return;
        double eDist = Math.abs(dist(x,y, player.x,player.y));
        int scale = 200*64;
        g.setColor(Color.red);
//        if (eDist < rDist) {
//            g.drawLine((int) (xPos + 1.0 / eDist * scale), HGT / 2, (int) (xPos - 1.0 / eDist * scale), HGT / 2);
//        }
        int wid = (int) (1.0 / eDist * scale  * 2);
        g2d.setStroke(new BasicStroke(WID/32));
        for(int i = 0; i < wid; i++) {
            int rDist = ray.getRayDist()[Math.min(xPos / ray.getDepth() + i, ray.getRayDist().length - 1)];
            if (eDist < rDist) {
                for(int j=0; j < 128; j++) {
                    g.setColor(MainGame.enemyImgArr[0][i * 32/(wid)*32+j]);
                    g.drawLine(xPos -wid/2 + i , HGT/2 + j,
                            xPos -wid/2 + i , HGT/2 + j);
                }
            }
        }
    }

//        if(Math.abs(dist(player.x,x,player.y,y)) < rDist)
//            g.drawRect(xPos, HGT/2, w,h);
    public double isPlayerLookingAt(Player player) {
        double angle = Math.atan2(y - player.y, x- player.x); //slope between player and enemy
        double playerAngle = fixAng(player.getAngle());
        double ratio =( playerAngle - angle) ;
        if(player.y > y)
            ratio = -(angle - playerAngle +2 * PI);
        double angleTolerance = Math.toRadians(30);
        return ratio/ angleTolerance;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    public static BaseEnemy[] addEnemy(BaseEnemy[] eArr, ArrayList<Point> spots, Player player){
        for(int i=0; i < eArr.length; i++){
            int ind = (int) (Math.random()* (spots.size()-1));
            Point currSpot = spots.get(ind);
            eArr[i] = new BaseEnemy(currSpot.x*64,currSpot.y*64, player);
        }
        return eArr;
    }
    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}
