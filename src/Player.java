import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Player extends ParentEntity {

    private final ArrayList<Bullet> pBullets = new ArrayList<>();
    private double angle = Math.PI;
    private double dX;
    private double dY;

    public Player(int x, int y) {
        super(x, y, 10, 10);
        dX = x;
        dY = y;
    }

    public void movePlayer(boolean[] keys) {
        double speed = 5;
        double turnAngle = 0.05;
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
            pBullets.add(new Bullet(x,y,dX,dY));
        pBullets.forEach(ParentMovingEntity::moveEntity);

    }

    public void drawPlayer(Graphics g) {
        int r = Game2D.getDunSizeRatio();
        g.setColor(Color.RED);
        g.fillRect((x/r - w / 2), (y/r - h / 2), w, h);
        pBullets.forEach(b -> b.drawBullet(g, r));
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


}
