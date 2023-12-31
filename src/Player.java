import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Player extends ParentEntity {


    private final ArrayList<Integer> rx = new ArrayList<>();
    private final ArrayList<Integer> ry = new ArrayList<>();
    private final ArrayList<Bullet> pBullets = new ArrayList<>();

    private double angle = 0;
    private double dX;
    private double dY;
    private double speed = 5;

    public Player(int x, int y) {
        super(x, y, 10, 10);
        dX = x;
        dY = y;
    }

    public void movePlayer(boolean[] keys) {
        double turnAngle = 0.1;
        if (keys[KeyEvent.VK_A])
            angle -= turnAngle;
        if (keys[KeyEvent.VK_D])
            angle += turnAngle;
        fixAngle();
        dX = Math.cos(angle) * speed;
        dY = Math.sin(angle) * speed;
        if (keys[KeyEvent.VK_W]) {
            x += (int) Math.round(dX);
            y += (int) Math.round(dY);
        }
        if (keys[KeyEvent.VK_S]) {
            x -= (int) Math.round(dX);
            y -= (int) Math.round(dY);
        }
        if(keys[KeyEvent.VK_SPACE])
            pBullets.add(new Bullet(x,y,dX*2,dY*2));
        pBullets.forEach(ParentMovingEntity::moveEntity);
    }

    public void drawPlayer(Graphics g) {
        int r = Game2D.getDunSizeRatio();
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.green);
        g.fillRect((x/r - w / 2), (y/r - h / 2), w, h);
        g.drawLine(x/r, y/r, (int) (x/r + 500 * Math.cos(angle)), (int) (y/r + 500 * Math.sin(angle)));
        g.setColor(Color.yellow);
        for (int i = 0; i <= rx.size() - 1; i++)
            g2d.drawLine(x/r, y/r, rx.get(i)/r, ry.get(i)/r);//        g2d.rotate(-angle);
        rx.clear();
        ry.clear();
        pBullets.forEach(b -> b.drawBullet(g, r));
    }

    public void setPlayerRay(int rx, int ry) {
        this.rx.add(rx);
        this.ry.add(ry);
    }
    public void fixAngle(){
        if (angle < 0)
            angle += 2 * Math.PI;
        if (angle > 2 * Math.PI)
            angle -= 2 * Math.PI;
    }

    public double getAngle() {
        return angle;
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

    public double getSpeed() {
        return speed;
    }
}
