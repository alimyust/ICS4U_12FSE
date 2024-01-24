package Enemies;
import MainGame.Music;
import ParentEntity.ParentEntity;
import Player.Player;
import Map.*;
import MainGame.Game3D;
import java.awt.*;
import java.util.ArrayList;

// A class that has all of the functions each enemy would need. The enemies have a separate class where
//the right variables are set, but the behaviour for each enemy is mechanically the same.
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
    public static final int BAT = 0;
    public static final int GOBLINBERSERKER = 1;
    public static final int SKELETON = 2;
    public static final int GOBLINSLINGER= 3;
    private int previousState;
    private long lastHurtTime;
    private long lastDeadTime;
    private double lastHurtFrame;
    private double lastDeadFrame;
    private final int IDLE =0;
    private final int RUN =1;
    private final int ATTACK =2;
    private final int HURT =3;
    private final int DEAD =4;
    private int enemyState;
    private int attackFrame;

    private Music enemyAttackSound;
    private int oldEnemyState;

    public BaseEnemy(int x, int y) {
        super(x, y, 64, 64);
        this.frame = 0;
        this.isAlive = true;
    }

    public static ArrayList<BaseEnemy> addEnemy(ArrayList<BaseEnemy> eArr, ArrayList<Point> spots, int eCount, int type) {
        //picks an open spot and adds the respective enemy based on the code
        for (int i = 0; i < eCount; i++) {
            int ind = (int) (Math.random() * (spots.size() - 1));
            Point currSpot = spots.get(ind);
            switch (type) {
                case GOBLINBERSERKER -> eArr.add(new GoblinBerserker(currSpot.x * 64, currSpot.y * 64));
                case SKELETON -> eArr.add(new Skeleton(currSpot.x * 64, currSpot.y * 64));
                case GOBLINSLINGER ->eArr.add(new GoblinSlinger(currSpot.x * 64, currSpot.y * 64));
            }
        }
        return eArr;
    }

    public void moveEnemy(Player player, Dungeon dun) {
        double ang = Math.atan2(player.y - y, player.x - x);
        int dx = (int) (Math.cos(ang) * speed);
        int dy = (int) (Math.sin(ang) * speed);
        if (enemyState == DEAD ||enemyState == HURT|| !Game3D.notIntersectingMap(x + dx, y + dy, dun.getMap())) return;
        if (playerEnemyDist(player) >= startDist) return; // range where enemy will start moving
        if (playerEnemyDist(player) > stopDist) {
            x += dx;
            y += dy;
            enemyState = RUN;
            return;
        }
        this.playSound();
        enemyState = ATTACK;
        if(Math.random() < 0.2) player.setHealth(player.getHealth() - damage);
    }

    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
        if(!isAlive) return; // only draw when alive
        if(oldEnemyState != enemyState) frame = 0;
        Color[][] sprite = enemyImgArr[enemyState][(int) (frame % (enemyImgArr[enemyState].length - 1))];
        if (enemyState == HURT) {            // Check if hurt cooldown is over
            if (System.currentTimeMillis() - lastHurtTime >= 300) {
                enemyState = RUN;
            } else {
                sprite = enemyImgArr[HURT][(int) (lastHurtFrame % enemyImgArr[enemyState].length)];
                lastHurtFrame += frameRate;  // Display the entire hurt animation during the cooldown
            }
        }
        if(enemyState == DEAD){
            if(System.currentTimeMillis() - lastDeadTime >= 1000) {
                isAlive = false;
                return;
            } else {
                sprite = enemyImgArr[DEAD][(int) (lastDeadFrame % enemyImgArr[enemyState].length)];
                lastDeadFrame = Math.min(frameRate + lastDeadFrame,enemyImgArr[enemyState].length-1);
            }
        }

        double angleRatio = -isPlayerLookingAt(player, ray.getFov()/2);
        int xPos = (int) (WID / 2 * angleRatio + WID / 2);
//        System.out.println(angleRatio);
        if (xPos < -96 || xPos > WID) return;
        double eDist = Math.abs(dist(x, y, player.x, player.y));
        double scaleSpeed = 18;
        double scaleMagnitude = 30;
        double scale = Math.min(3.5, 1.0 / (eDist / scaleSpeed) * scaleMagnitude); // Apply minimum scaling factor

        int wid = sprite[0].length;
        int hgt = sprite.length;
        int sWid = (int) (wid * scale); // Scaled width
        int sHgt = (int) (hgt * scale); // Scaled height
        // Adjust the vertical position based on the scaled he
        // ight
        int yPos = HGT / 2 - sHgt / 2;
        double wDist;
        for (int x = 0; x < sWid; x++) {
            if(xPos + x<0) continue;
            wDist = ray.getRayDist()[Math.min((xPos + x) / ray.getDepth(), ray.getRayDist().length - 1)];
            if (wDist < eDist) continue; // draws a ray only when an enemy is closer than a wall
            for (int y = 0; y < sHgt; y++) {
                Color col = sprite[(int) (y / scale)][(int) (x / scale)];
                if (col.equals(Color.decode("#f8028a"))) continue;
                g.setColor(col);
                g.drawRect(xPos + x, yPos + y, 1, 1);
            }
        }
        oldEnemyState = enemyState;
        frame += frameRate;

    }


    public double isPlayerLookingAt(Player player, int tolerance) {
        double angle = Math.atan2(player.y + 32 - (y + 48), player.x + 32 - (x + 48));
        double playerAngle = player.getAngle();

        // Calculate the smallest angle difference considering circular nature
        double angleDifference = fixAng(playerAngle - angle) - Math.PI;
        double angleTolerance = Math.toRadians(tolerance);
        return angleDifference / angleTolerance;
    }

    // Assuming fixAng function looks like this:
    private double fixAng(double angle) {
        // Normalize angle to the range [0, 2π)
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    public void setHurtState() {
        // Save the previous state and frame
        frame = 0;
        enemyState = HURT;
//        previousState = enemyState;
        lastHurtFrame = frame;
        lastHurtTime = System.currentTimeMillis();

    }
    public void setDeadState() {
        frame = 0;
        if(enemyState == DEAD) return;
        enemyState = DEAD;
        lastDeadTime = System.currentTimeMillis();

    }
    public void playSound(){
//        if(enemyAttackSound)
        if((int)frame % attackFrame == 0 && (int)(frame - frameRate)% attackFrame != 0){
            enemyAttackSound.play();
        }
    }

    public void setAttackFrame(int attackFrame) {
        this.attackFrame = attackFrame;
    }

    public void setEnemyAttackSound(Music enemyAttackSound) {
        this.enemyAttackSound = enemyAttackSound;
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
