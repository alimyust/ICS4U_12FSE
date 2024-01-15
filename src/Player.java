import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;


public class Player extends ParentEntity {


    private final ArrayList<Integer> rx = new ArrayList<>();
    private final ArrayList<Integer> ry = new ArrayList<>();
    private double angle = 0;
    private double dX;
    private double dY;
    private Gun curGun;
    public Player(int x, int y) {
        super(x, y, 10, 10);
        dX = x;
        dY = y;
        curGun = new Pistol(0.2, 10, 600);
    }
    public void chooseGun(boolean[] keys){
        try {
            if (keys[KeyEvent.VK_1])
                curGun = new Pistol(0.2, 10, 600);
            if (keys[KeyEvent.VK_2])
                curGun = new Shotgun(0.3, 20, 400);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void movePlayer(boolean[] keys, Dungeon dun) {
        double turnAngle = 0.1;
        if (keys[KeyEvent.VK_A])
            angle -= turnAngle;
        if (keys[KeyEvent.VK_D])
            angle += turnAngle;
        fixAngle();
        double speed = 10;
        dX = Math.cos(angle) * speed;
        dY = Math.sin(angle) * speed;
        if (keys[KeyEvent.VK_W] &&
        Game3D.notIntersectingMap((int) (x + dX), (int) (y + dY), w, dun.getMap())) {
            x += (int) Math.round(dX);
            y += (int) Math.round(dY);
        }
        if (keys[KeyEvent.VK_S] &&
        Game3D.notIntersectingMap((int) (x - dX), (int) (y - dY), w, dun.getMap())) {
            x -= (int) Math.round(dX);
            y -= (int) Math.round(dY);
        }
    }

    public void shootEnemies(boolean[] keys, Dungeon dun) {
        if (!keys[KeyEvent.VK_SPACE]) return;
        if (curGun.getGunFrame() != 0) return;
        curGun.setGunFrame(1);
        for( BaseEnemy enemy: dun.geteArr()) {
            if (Math.abs(enemy.isPlayerLookingAt(this, curGun.getAoe())) >= 1) continue;
            if (dist(x, y, enemy.x, enemy.y) >= curGun.getRange()) continue;
            enemy.setAlive(false);
        }

    }
    public void shootAnimation(Graphics g){
        curGun.drawGun(g);
    }

    public void drawPlayer(Graphics g) { //2d
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

    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}
