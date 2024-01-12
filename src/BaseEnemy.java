import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.PI;

public class BaseEnemy extends ParentEntity {

    private final Color[][][] enemyImgArr;
    private int frame;
    private boolean isAlive;
    private final int speed = 5;
    private Random random = new Random();
    private double velocityX = 0.0;  // Initial horizontal velocity
    private double velocityY = 0.0;  // Initial vertical velocity
    private double wanderAngle = 0.0;
    private double wanderRadius = 50.0;  // Adjust as needed
    private double wanderDistance = 100.0;  // Adjust as needed
    private double wanderChangeRate = Math.toRadians(10);  // Adjust as needed

    public BaseEnemy(int x, int y, Color[][][] imgArr) {
        super(x, y, 64, 64);
        enemyImgArr = imgArr;
        this.frame = 0;
        this.isAlive = true;

    }

    public static BaseEnemy[] addEnemy(BaseEnemy[] eArr, ArrayList<Point> spots, Color[][][] imgArr) {
        for (int i = 0; i < eArr.length; i++) {
            int ind = (int) (Math.random() * (spots.size() - 1));
            Point currSpot = spots.get(ind);
            eArr[i] = new BaseEnemy(currSpot.x * 64, currSpot.y * 64, imgArr);
        }
        return eArr;
    }

    public void moveEnemy(Player player, Dungeon dun) {
        double ang = Math.atan2(player.y - y, player.x - x);
        int dx = (int) (Math.cos(ang) * speed);
        int dy = (int) (Math.sin(ang) * speed);
        if (!isAlive || !Game3D.notIntersectingMap(x + dx, y + dy, w, dun.getMap())) return;
        if (playerEnemyDist(player) < 800) {
            x += dx;
            y += dy;
        } else {
            wander();
        }
    }

    private void wander() {
        // Update the wander angle over time
        wanderAngle += random.nextDouble() * wanderChangeRate - wanderChangeRate * 0.5;
        // Calculate the position of the wander circle center
        double circleCenterX = x + wanderDistance * Math.cos(wanderAngle);
        double circleCenterY = y + wanderDistance * Math.sin(wanderAngle);

        // Generate a random point within the wander circle
        double targetAngle = random.nextDouble() * 2 * Math.PI;
        double targetX = circleCenterX + wanderRadius * Math.cos(targetAngle);
        double targetY = circleCenterY + wanderRadius * Math.sin(targetAngle);

        // Apply steering force to move towards the random point
        double angleToTarget = Math.atan2(targetY - y, targetX - x);
        double desiredVelocityX = speed * Math.cos(angleToTarget);
        double desiredVelocityY = speed * Math.sin(angleToTarget);

        // Calculate the steering force
        double steeringForceX = desiredVelocityX - velocityX;
        double steeringForceY = desiredVelocityY - velocityY;

        // Apply the steering force to the enemy's velocity
        velocityX += steeringForceX;
        velocityY += steeringForceY;

        // Limit the speed (optional)
        double currentSpeed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (currentSpeed > speed) {
            double scale = speed / currentSpeed * 4;
            velocityX *= scale;
            velocityY *= scale;
        }

        // Update the enemy's position based on the new velocity
        x += velocityX;
        y += velocityY;
    }

    public void drawBaseEnemy(Graphics g, Player player, int HGT, int WID, RayCaster ray) {
        double angleRatio = -isPlayerLookingAt(player, 30);
        Color[][] sprite = enemyImgArr[frame % (enemyImgArr.length - 1)];
        frame++;

        int xPos = (int) (WID / 2 * angleRatio + WID / 2);
        if (xPos < 0 || xPos > WID) return;

        double eDist = Math.abs(dist(x, y, player.x, player.y));
        double scaleSpeed = 18;
        double scaleMagnitude = 30;
        double scale = Math.min(3, 1.0 / (eDist / scaleSpeed) * scaleMagnitude); // Apply minimum scaling factor

        int wid = sprite[0].length;
        int hgt = sprite.length;
        int sWid = (int) (wid * scale); // Scaled width
        int sHgt = (int) (hgt * scale); // Scaled height

        // Adjust the vertical position based on the scaled height
        int yPos = HGT / 2 - sHgt / 2;
        if (!isAlive) {
            sprite = enemyImgArr[enemyImgArr.length - 1];
            yPos += sHgt / 1.2;
        }
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
    }


    public double isPlayerLookingAt(Player player, int tolerance) {
        double angle = Math.atan2(y - player.y, x - player.x); //slope between player and enemy
        double playerAngle = fixAng(player.getAngle());
        double ratio = (playerAngle - angle);
        if (player.y > y) ratio = -(angle - playerAngle + 2 * PI);
        double angleTolerance = Math.toRadians(tolerance);
        return ratio / angleTolerance;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    private int getMapX(int mp, int wid) {
        return mp % wid * 64;
    }

    private int getMapY(int mp, int wid) {
        return mp / wid * 64;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

    public int playerEnemyDist(Player p) {
        return (int) Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
    }
}
