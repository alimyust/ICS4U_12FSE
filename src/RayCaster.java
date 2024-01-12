import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.PI;

public class RayCaster {
    private final Player player;
    private final Dungeon dun;
    private final int WID = Game3D.getWid3d();
    private final int HGT = Game3D.getHgt3d();
    private final double resolution = 2; //(10 is max before it's too high resolution for the display size)
    private final int fov = (int) (30 * resolution);
    private final double DR = Math.PI/180.0/ resolution; // degree
    private final int depth = (WID / (fov * 2));
    private final int mapS;
    private final int mapX;
    private final int mapY;
    private final MapNode[] mapW;
    private final int[] rayDist = new int[fov*2];
    private final Color[][] tileImgArr = {
            MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/DENTWALL.png"),
            MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/HEXAGONS.png"),
            MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/BIGTRUNK.png"),
            MainGame.convertImageToColorArray(MainGame.imgDir + "dirt.png"),
            MainGame.convertImageToColorArray(MainGame.imgDir + "grass.png"),
            MainGame.convertImageToColorArray(MainGame.imgDir + "redBrick.png"),
    };

    public RayCaster(Player player, Dungeon dun) {
        this.player = player;
        this.dun = dun;
        mapX = dun.getMap()[0].length;
        mapY = dun.getMap().length;
        mapS = mapX * mapY;
        mapW = flatten(dun.getMap());
    }

    public static MapNode[] flatten(MapNode[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        MapNode[] flattenedArray = new MapNode[rows * cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(array[i], 0, flattenedArray, i * cols, cols);
        return flattenedArray;
    }

    public void drawRays3d(Graphics2D g2) {
        int tSize = dun.getDSIZE();
        int renderDist = 32; //amount of walls rendered when looking around
        int distScale = 50; //how far away things look
        int darkScale =1000;
        int px = player.x;
        int py = player.y;
        double pa = player.getAngle();
        int mx = 0, my = 0, mp = 0, dof;
        double rx = 0;
        double ry = 0;
        double ra;
        double xo = 0;
        double yo = 0;
        double distT = 0;
        ra = pa - DR * fov;
        g2.setStroke(new BasicStroke(depth));

        for (int r = 0; r < fov * 2; r++) {
            // Horizontal Lines
            double distH = 1000000000, hx = px, hy = py;
            dof = 0;
            double aTan = -1 / Math.tan(ra);
            if (ra > PI) {
                ry = py / tSize * tSize - 0.001;
                rx = (py - ry) * aTan + px;
                yo = -tSize;
                xo = -yo * aTan;
            }
            if (ra < PI) {
                ry = py / tSize * tSize + tSize;
                rx = (py - ry) * aTan + px;
                yo = tSize;
                xo = -yo * aTan;
            }
            if (ra == 0 || ra == PI) {
                rx = px;
                ry = py;
                dof = renderDist;
            }
            while (dof < renderDist) {
                mx = (int) (rx) / tSize;
                my = (int) (ry) / tSize;
                mp = my * mapX + mx;
                if (mp > 0 && mp < mapS && mapW[mp].getwCode() != 0) {
                    hx = rx; // If a wall is hit save the x and y values and end loop
                    hy = ry;
                    distH = dist(px, py, hx, hy);
                    dof = renderDist;
                } else { // If a wall isn't found add the x and y offset
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }
            // Vertical Lines
            double distV = 1000000000, vx = px, vy = py;
            dof = 0;
            double nTan = -Math.tan(ra);
            double PI2 = Math.PI / 2; //90
            double PI3 = 3 * Math.PI / 2; //270
            if (ra > PI2 && ra < PI3) {
                rx = px / tSize * tSize - 0.001;
                ry = (px - rx) * nTan + py;
                xo = -tSize;
                yo = -xo * nTan;
            }
            if (ra < PI2 || ra > PI3) {
                rx = px / tSize * tSize + tSize;
                ry = (px - rx) * nTan + py;
                xo = tSize;
                yo = -xo * nTan;
            }
            if (ra == 0 || ra == PI) {
                rx = px;
                ry = py;
                dof = renderDist;
            }
            while (dof < renderDist) {
                mx = (int) (rx) / tSize;
                my = (int) (ry) / tSize;
                mp = my * mapX + mx;
                if (mp > 0 && mp < mapS && mapW[mp].getwCode() != 0) {
                    vx = rx;
                    vy = ry;
                    distV = dist(px, py, vx, vy);
                    dof = renderDist;
                } else {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }
            double shade = 1;
            if (distV < distH) {
                rx = vx;
                ry = vy;
                distT = distV;
                shade = 0.5F;
            }
            if (distV > distH) {
                rx = hx;
                ry = hy;
                distT = distH;
            }
            player.setPlayerRay((int) rx, (int) ry);
            // drawing Setup
            double ca = fixAng(pa - ra);
            distT = distT * Math.cos(ca); //player to ray distance
            int lineH = (int) ((mapS * HGT) / distT / distScale); // line height when drawing
            double tyStep = 32 / (double) lineH;
            double tyOff = 0;
            if (lineH > HGT) {
                tyOff = (lineH - HGT) / 2.0F;
                lineH = HGT;//clipping height
            }
            double lineOff = (double) HGT / 2 - (lineH / 2F); // Starting point after cutoff ( >0)
            rayDist[r] = (int) distT;
            //---draw walls---
            double ty = tyOff * tyStep; //texture y val
            int tx;
            int texSize = tSize / 2;
            int texSize_1 = texSize - 1;
            if (shade == 1) {
                tx = (int) ((rx / 2.0) % texSize);
                if (ra < PI) tx = texSize_1 - tx; //greater than 180
            } else {
                tx = (int) ((ry / 2.0) % texSize);
                if (ra > PI2 && ra < PI3) tx = texSize_1 - tx; //greater than 90 and less than 270
            }

            mp = fixMp(mp);
            for (int y = 0; y < lineH; y++) {
                int pixel = ((int) ty * texSize + tx);
                if (pixel > 1023) pixel = 1023; if (pixel < 0) pixel = 0;
                Color col = this.tileImgArr[mapW[mp].getwCode()][pixel];
                double darknessFactor = 1 - Math.min(distT / darkScale, 1);
                col = applyDarkness(col, darknessFactor);
                g2.setColor((shade != 1) ? col : col.darker().darker());
                g2.drawLine(r * depth, (int) (y + lineOff), r * depth, (int) (y + lineOff));
                ty += tyStep; // Adjust texture coordinate
            }
            //ceiling
            for (int y = (int) lineOff + lineH; y < HGT; y++) {
                double dy = y - (double) HGT / 2.0f; //distance from end of wall to end of screen
                double raFix = Math.cos(fixAng(pa - ra));
                // Adjust the floor tile size by scaling the texture coordinates
                double scaledPx = px / 2F;
                double scaledPy = py / 2F;
                tx = (int) (scaledPx + Math.cos(ra) * (480) * 32 / dy / raFix);
                ty = (int) (scaledPy + Math.sin(ra) * (480) * 32 / dy / raFix);
                //hours and hours of trial and error to align the floor and ceiling values for tx and ty
                mp = fixMp((int) (ty / 32) * mapX + tx / 32);
                int pixel = (((int) (ty) & 31) * texSize + (tx & 31));
                double tileDist = dist(px,py,getMapX(mp),getMapY(mp));
                double darknessFactor = 1 - Math.min(Math.abs(tileDist) / darkScale, 1);
                Color floorColor = applyDarkness(this.tileImgArr[mapW[mp].getfCode()][pixel], darknessFactor);
                Color ceilingColor = applyDarkness(this.tileImgArr[mapW[mp].getcCode()][pixel], darknessFactor);
                if (mapW[mp].getfCode() != -1) {
                    g2.setColor(floorColor);
                    g2.drawLine(r * depth, y, r * depth, y);
                }
                if (mapW[mp].getcCode() != -1) {
                    g2.setColor(ceilingColor);
                    g2.drawLine(r * depth, HGT - y, r * depth, HGT - y);
                }
            }
            ra += DR;
            ra = fixAng(ra);
        }
//        testE.drawBaseEnemy(g2, px, py, fixAng(pa), HGT, WID);
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += 2 * PI;
        if (angle > 2 * PI) angle -= 2 * PI;
        return angle;
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

    private int fixMp(int mp) {
        if (mp >= mapS) mp = mapS - 2;
        if (mp < 0) mp = 0;
        return mp;
    }

    public int[] getRayDist() {
        return rayDist;
    }

    public int getDepth() {
        return depth;
    }

    public int getFov() {
        return fov;
    }
    private int getMapX(int mp) {
        return mp % mapX * 64;
    }
    private int getMapY(int mp) {
        return mp / mapX * 64;
    }
    private Color applyDarkness(Color color, double factor) {
        int red = (int) (color.getRed() * factor);
        int green = (int) (color.getGreen() * factor);
        int blue = (int) (color.getBlue() * factor);

        // Ensure that the RGB values are within the valid range [0, 255]
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return new Color(red, green, blue);
    }
}
