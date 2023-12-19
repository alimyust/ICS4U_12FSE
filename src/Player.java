import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;


public class Player extends ParentEntity {


    private double angle = 0;
    private double dX;
    private double dY;
    public Player(int x, int y) {
        super(x, y, 10, 10);
        dX=x;
        dY = y;
    }

    public void movePlayer(boolean[] keys) {
        int range = 1;
        if (keys[KeyEvent.VK_A]) {
            angle -= 0.1;
            if (angle < 0)
                angle += 2 * Math.PI;
        }
        if (keys[KeyEvent.VK_D]) {
            angle += 0.1;
            if (angle > 2 * Math.PI)
                angle -= 2 * Math.PI;
        }
        System.out.println("dX = " + dX + "   dY = " + dY);
        System.out.println("X = " + x + "   Y = " + y);
        if (keys[KeyEvent.VK_W]) {
            dX += Math.cos(angle) * range;
            dY += Math.sin(angle) * range;
        }
        if (keys[KeyEvent.VK_S]) {
            dX -= Math.cos(angle) * range;
            dY -= Math.sin(angle) * range;
        }
        x = (int) Math.round(dX);
        y = (int) Math.round(dY);
    }

    public double getAngle() {
        return angle;
    }


    public void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);

//        g2d.translate(x , y );
//        g2d.rotate(angle);
        g.fillRect((x - w / 2), (y - h / 2), w, h);
        g.drawLine(x, y, (int) (x + 15*Math.cos(angle)), (int) (y+15*Math.sin(angle)));
//        g2d.rotate(-angle);
//        g2d.translate(-x,-y);

    }
    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

}
