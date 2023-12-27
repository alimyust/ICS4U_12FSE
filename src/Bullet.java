import java.awt.*;

public class Bullet extends ParentMovingEntity{
    public Bullet(int x, int y, double vx, double vy) {
        super(x, y, 3, 3, vx, vy);
    }
    public void drawBullet(Graphics g, int r){
        g.setColor(Color.GREEN);
//        System.out.println(x +","+y);
        g.drawOval(x/r,y/r,w,h);
    }
}
