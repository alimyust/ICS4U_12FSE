import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity {

    protected Color[][][][] enemyImgArr;
    private double frame;
    private boolean isAlive;
    private final int speed = 5;
    private int stopDist;
    private int startDist;
    private double frameRate;
    private double damage;
    private double health;
    protected static final int BAT = 0;
    protected static final int SANS = 1;
    protected static final int GUNMAN = 2;
    protected static final int GOBLINSLINGER= 3;
    private int previousState;
    private int hurtCooldown = 300; // Adjust the cooldown time as needed
    private long lastHurtTime;
    private double lastHurtFrame;
    private final int IDLE =0;
    private final int RUN =1;
    private final int ATTACK =2;
    private final int HURT =3;
    private final int DEAD =4;
    private int enemyState;

    public BaseEnemy(int x, int y) {
        super(x, y, 64, 64);
        this.frame = 0;
        this.isAlive = true;
    }

    public static ArrayList<BaseEnemy> addEnemy(ArrayList<BaseEnemy> eArr, ArrayList<Point> spots, int eCount, int type) {
        for (int i = 0; i < eCount; i++) {
            int ind = (int) (Math.random() * (spots.size() - 1));
            Point currSpot = spots.get(ind);
            switch (type) {
//                case BAT -> eArr.add(new Bat(currSpot.x * 64, currSpot.y * 64));
//                case SANS -> eArr.add(new Sans(currSpot.x * 64, currSpot.y * 64));
//                case GUNMAN -> eArr.add(new GunMan(currSpot.x * 64, currSpot.y * 64));
                case GOBLINSLINGER ->eArr.add(new GoblinSlinger(currSpot.x * 64, currSpot.y * 64));
            }
        }
        return eArr;
    }

    public void moveEnemy(Player player, Dungeon dun) {
        double ang = Math.atan2(player.y - y, player.x - x);
        int dx = (int) (Math.cos(ang) * speed);
        int dy = (int) (Math.sin(ang) * speed);
        if(health <= 0) isAlive = false;
        if (!isAlive ||enemyState == HURT|| !Game3D.notIntersectingMap(x + dx, y + dy, dun.getMap())) return;
        if (playerEnemyDist(player) >= startDist) return;
        if (playerEnemyDist(player) > stopDist) {
            x += dx;
            y += dy;
            enemyState = RUN;
            return;
        }
        enemyState = ATTACK;
        if(Math.random() < 0.2) player.setHealth(player.getHealth() - damage);
    }

    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
        if(!isAlive) enemyState = DEAD;
        Color[][] sprite = enemyImgArr[enemyState][(int) (frame % (enemyImgArr[enemyState].length - 1))];
        System.out.println(enemyState);
        if (enemyState == HURT) {
            // Check if hurt cooldown is over
            if (System.currentTimeMillis() - lastHurtTime >= hurtCooldown) {
                enemyState = previousState; // Return to previous state

            } else {
                // Display the entire hurt animation during the cooldown
                sprite = enemyImgArr[HURT][(int) (lastHurtFrame % enemyImgArr[enemyState].length)];
                lastHurtFrame += frameRate;
            }
        }
        if(enemyState == DEAD){
            if(System.currentTimeMillis() - lastHurtTime >= hurtCooldown) {
                sprite = enemyImgArr[DEAD][(enemyImgArr[enemyState].length - 1)];
            } else {
                sprite = enemyImgArr[DEAD][(int) (lastHurtFrame % enemyImgArr[enemyState].length)];
                lastHurtFrame += frameRate;
            }
        }

//        if(!isAlive)
//            sprite = enemyImgArr[enemyState][enemyImgArr[DEAD].length - 1];

        double angleRatio = -isPlayerLookingAt(player, ray.getFov()/2);
        int xPos = (int) (WID / 2 * angleRatio + WID / 2);
        if (xPos - w < 0 || xPos > WID) return;

        double eDist = Math.abs(dist(x, y, player.x, player.y));
        double scaleSpeed = 18;
        double scaleMagnitude = 30;
        double scale = Math.min(3, 1.0 / (eDist / scaleSpeed) * scaleMagnitude); // Apply minimum scaling factor


        int wid = sprite[0].length;
        int hgt = sprite.length;
        int sWid = (int) (wid * scale); // Scaled width
        int sHgt = (int) (hgt * scale); // Scaled height

        // Adjust the vertical position based on the scaled he
        // ight
        int yPos = HGT / 2 - sHgt / 2;
        for (int x = 0; x < sWid; x++) {
            double wDist = ray.getRayDist()[Math.min((xPos + x) / ray.getDepth(), ray.getRayDist().length - 1)];
            if (wDist < eDist) continue; // draws a ray only when an enemy is closer than a wall
            for (int y = 0; y < sHgt; y++) {
                Color col = sprite[(int) (y / scale)][(int) (x / scale)];
                if (col.equals(Color.decode("#f8028a"))) continue;
                g.setColor(col);
                g.drawRect(xPos + x, yPos + y, 1, 1);
            }
        }
        frame += frameRate;

    }


    public double isPlayerLookingAt(Player player, int tolerance) {
        double angle = Math.atan2((y +h/2.0)- player.y, (x+w/2.0) - player.x); //slope between player and enemy
        double playerAngle = fixAng(player.getAngle());
        double ratio = (playerAngle - angle);
        if (player.y > (y +h/2.0)) ratio = -(angle - playerAngle + 2 * PI);
        double angleTolerance = Math.toRadians(tolerance);
        return ratio / angleTolerance;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    public void setHurtState() {
        // Save the previous state and frame
            health --;
            previousState = enemyState;
            lastHurtFrame = frame;
            lastHurtTime = System.currentTimeMillis();
            // Set the enemy to the hurt state
            enemyState = HURT;

    }
    public void setDeadState() {
        // Save the previous state and frame
        lastHurtTime = System.currentTimeMillis();
        enemyState = DEAD;

    }


    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
    public int playerEnemyDist(Player p) {
        return (int) Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
    public void setStopDist(int stopDist) {
        this.stopDist = stopDist;
    }
    public void setStartDist(int startDist) {
        this.startDist = startDist;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

}
