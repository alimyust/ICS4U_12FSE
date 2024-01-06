import java.awt.*;

public class BaseEnemy extends ParentMovingEntity{
    private final int z;
    public BaseEnemy(int x, int y,int z, int w, int h, double vx, double vy) {
        super(x, y, w, h, vx, vy);
        this.z =50;
    }

    public void drawBaseEnemy(Graphics2D g, int px, int py, double pa,int HGT, int WID){
        double slopoeEtoP = (double) (py - y) / (double)(px - x);
        double angDiff = (Math.atan(slopoeEtoP) - pa);
        double enemyDist = dist(px,py,x,y);
        System.out.println(angDiff);
        g.setColor(Color.RED);
        g.drawRect((int) (angDiff*WID*angDiff) + WID/2 , HGT/2, 16,16);
//        float sx = this.x - px;
//        float sy = this.y - py;
//        //rotation matrix
//        float a = (float) (sy*Math.cos(pa)+sx*Math.sin(pa));
//        float b = (float) (sx*Math.cos(pa)-sy*Math.sin(pa));
////        sx = a; sy = b;
//        float scale = 108;
//        float q = (a*scale/b) +(WID/2);
//        float p = (z*scale/b) + (HGT/2);
//        g.setColor(Color.YELLOW);
//        g.drawRect((int) (a), (int) (b), 30,30);
//        g.drawRect((int) (sx), (int) (sy), 30,30);
//        g.setColor(Color.red);
//        g.drawRect((int) (q), (int) (p), 30,30);
//

    }

    public double dist(int ax, int ay, int bx, int by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}
