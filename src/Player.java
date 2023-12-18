import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;


public class Player extends ParentEntity {


    private double angle = 0;
    private double dX = 0;
    private double dY = 0;
    public Player(int x, int y) {
        super(x, y, 10, 10);
    }

    public void movePlayer(boolean[] keys) {
        int range = 8;
        if (keys[KeyEvent.VK_A]) {
            angle -= 0.1;
            if (angle < 0)
                angle += 2 * Math.PI;
            dX = Math.cos(angle) * range;
            dY = Math.sin(angle) * range;
        }
        if (keys[KeyEvent.VK_D]) {
            angle += 0.1;
            if (angle > 2 * Math.PI)
                angle -= 2 * Math.PI;
            dX = Math.cos(angle) * range;
            dY = Math.sin(angle) * range;

        }
        if (keys[KeyEvent.VK_W]) {
            x += dX;
            y += dY;
        }
        if (keys[KeyEvent.VK_S]) {
            x -= dX;
            y -= dY;
        }
    }

    public double getAngle() {
        return angle;
    }


    public void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);

//        g2d.translate(x , y );
//        g2d.rotate(angle);
        g.fillRect(x - w / 2, y - h / 2, w, h);
        g.drawLine(x, y, (int) (x + dX * 5), (int) (y + dY * 5));
//        g2d.rotate(-angle);
//        g2d.translate(-x,-y);

    }
    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

}
