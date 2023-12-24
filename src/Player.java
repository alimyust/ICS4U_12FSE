import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Player extends ParentEntity {


    private final ArrayList<Integer> rx = new ArrayList<>();
    private final ArrayList<Integer> ry = new ArrayList<>();
    private double angle = 0;
    private double dX;
    private double dY;

    public Player(int x, int y) {
        super(x, y, 10, 10);
        dX = x;
        dY = y;
    }

    public void movePlayer(boolean[] keys) {
        double speed = 3;
        double angleTurnSpeed = 0.05;
        if (keys[KeyEvent.VK_A]) {
            angle -= angleTurnSpeed;
            if (angle < 0)
                angle += 2 * Math.PI;
//            dX += Math.cos(angle) * speed;
//            dY += Math.sin(angle) * speed;
        }
        if (keys[KeyEvent.VK_D]) {
            angle += angleTurnSpeed;
            if (angle > 2 * Math.PI)
                angle -= 2 * Math.PI;
        }

        if (keys[KeyEvent.VK_W]) {
            dX += Math.cos(angle) * speed;
            dY += Math.sin(angle) * speed;
            x = (int) dX;
            y = (int) dY;
        }
        if (keys[KeyEvent.VK_S]) {
            dX -= Math.cos(angle) * speed;
            dY -= Math.sin(angle) * speed;
            x = (int) dX;
            y = (int) dY;
        }
//        if (keys[KeyEvent.VK_W]) {
//            dX += Math.cos(angle) * speed;
//            dY += Math.sin(angle) * speed;
//        }
//        if (keys[KeyEvent.VK_S]) {
//            dX -= Math.cos(angle) * speed;
//            dY -= Math.sin(angle) * speed;
//        }
//        x = (int) Math.round(dX);
//        y = (int) Math.round(dY);
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
        g.drawLine(x, y, (int) (x + 15 * Math.cos(angle)), (int) (y + 15 * Math.sin(angle)));
        g.drawRect(50, 50, 1, 1);
        for (int i = 0; i <= rx.size() - 1; i++)
            g2d.drawLine(x, y, rx.get(i), ry.get(i));//        g2d.rotate(-angle);
        rx.clear();
        ry.clear();

    }

    public void setPlayerRay(int rx, int ry) {
        this.rx.add(rx);
        this.ry.add(ry);

    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

}
