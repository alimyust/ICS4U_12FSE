package Player;
import ParentEntity.ParentEntity;
import MainGame.*;
import Enemies.BaseEnemy;
import Map.Dungeon;

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
    private Pistol pistol  = new Pistol(0.5, 15, 700, 1);
    private Shotgun shotgun = new Shotgun(0.5, 30, 500, 2);

    private double health;
    private static boolean isMoving;
    private final int[][] healthBar= {
            {20, 195, 235, 400, 380, 245, 205, 0},
            {60, 60, 20, 20, 40, 40, 80, 80}
    };
    public Player(int x, int y) {
        super(x, y, 10, 10);
        dX = x;
        dY = y;
        health = 100;
        curGun = pistol;
    }
    public void drawPlayerGUI(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));
        for (int i = 0 ;i < health; i += 1) {
            g2d.setClip(new Polygon(healthBar[0], healthBar[1],8));
            g.setColor(new Color(86 + i, 1, 1));
            g.fillRect(i*4,20,4,80);
            g.setColor(Color.white);
            g.drawPolygon(healthBar[0], healthBar[1],8);
            g2d.setClip(0,0,MainGame.WID, MainGame.HGT);
        }
        if(health == 0)
            Game3D.setGameState("gameover");

    }
    public void chooseGun(boolean[] keys){
        if (keys[KeyEvent.VK_1])
            curGun = pistol;
        if (keys[KeyEvent.VK_2])
            curGun = shotgun;
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
        isMoving = false;
        if (keys[KeyEvent.VK_W] &&
        Game3D.notIntersectingMap((int) (x + dX), (int) (y + dY), dun.getMap()) &&
                !checkCollisionWithEnemies(x+dX,y+dY, dun.geteArr() )) {
            x += (int) Math.round(dX);
            y += (int) Math.round(dY);
            isMoving = true;
        }
        if (keys[KeyEvent.VK_S] &&
        Game3D.notIntersectingMap((int) (x - dX), (int) (y - dY), dun.getMap())&&
                !checkCollisionWithEnemies(x-dX,y-dY, dun.geteArr() )) {
            x -= (int) Math.round(dX);
            y -= (int) Math.round(dY);
            isMoving = true;
        }
    }
    private boolean checkCollisionWithEnemies(double nextX, double nextY, ArrayList<BaseEnemy> enemies) {
        boolean colliding = false;
        for (BaseEnemy enemy : enemies) {
            if(!enemy.isAlive()) return false;
            Rectangle playerRect = new Rectangle((int) nextX, (int) nextY, w, h);
            if (playerRect.intersects(new Rectangle(enemy.x, enemy.y, enemy.w, enemy.h)))
                colliding = true;
        }
        return colliding;
    }

    public void shootEnemies(boolean[] keys, Dungeon dun) {
        if (!keys[KeyEvent.VK_SPACE]) return;
        if (curGun.getGunFrame() != 0) return;
        curGun.setGunFrame(1);
        for( BaseEnemy enemy: dun.geteArr()) {
            if (Math.abs(enemy.isPlayerLookingAt(this, curGun.getAoe())) >= 1) continue;
            if (dist(x, y, enemy.x, enemy.y) >= curGun.getRange()) continue;
            enemy.setHealth( enemy.getHealth() - curGun.damage);
            if(enemy.getHealth() <= 0) enemy.setDeadState();
            else enemy.setHurtState();
            return;
        }
    }
    public void shootAnimation(Graphics g){
        curGun.drawGun(g);
    }

    public void drawPlayer(Graphics g,int xOff, int yOff, int r) { //2d
        g.setColor(Color.green);
        g.fillRect(xOff+(x/r - w / 2), yOff+(y/r - h / 2), w, h);
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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public static boolean isMoving() {
        return isMoving;
    }

    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
}
