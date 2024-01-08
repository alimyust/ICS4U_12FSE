import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity{
    private int distFromPlayer = 0;
    public BaseEnemy(int x, int y, Player player) {
        super(x, y, 64,64);
        this.distFromPlayer = dist(x,player.x,y,player.y);
    }

    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, int[] rayDist) {
        g.setColor(Color.red);
        double angleRatio = -isPlayerLookingAt(player);
//        if(rayDist[dist(player.x, x, player.y, y)/64] > dist(player.x, x, player.y, y))
        g.drawRect((int) (WID/2  *angleRatio + WID/2 - w/2), HGT/2, w,h);
    }
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
