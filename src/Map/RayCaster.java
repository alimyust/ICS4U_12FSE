package Map;
import MainGame.*;
import java.awt.*;

import static java.lang.Math.PI;

/* Raycasting engine
From 3D Sages online youtube tutorial with insipation from Lode's Online Raycasting Tutorial
https://lodev.org/cgtutor/raycasting.html#Untextured_Raycaster_
https://github.com/3DSage/OpenGL-Raycaster_v1
https://www.youtube.com/watch?v=gYRrGTC7GtA ( and part 2 and 3)
* */
public class RayCaster {
    private final int WID = Game3D.getWid3d();
    private final int HGT = Game3D.getHgt3d();
    private final double resolution = 2; //(10 is max before it's too high resolution for the display size)
    private final int fov = (int) (30 * resolution); // more rays for same fov increases resp;itopm
    private final double DR = Math.PI/180.0/ resolution; // 1 degree in radians
    private final int depth = (WID / (fov * 2)); // line thickness
    private final int mapS;
    private final int mapX;
    private final int mapY;
    private final MapNode[] mapW;
    private final int[] rayDist = new int[fov*2];

    public RayCaster() {
        mapX = MainGame.dun.getMap()[0].length;
        mapY = MainGame.dun.getMap().length;
        mapS = mapX * mapY;
        mapW = flatten(MainGame.dun.getMap());
    }

    public static MapNode[] flatten(MapNode[][] array) {
        //turns 2d array into 1d array
        int rows = array.length;
        int cols = array[0].length;
        MapNode[] flattenedArray = new MapNode[rows * cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(array[i], 0, flattenedArray, i * cols, cols);
        return flattenedArray;
    }

    public void drawRays3d(Graphics2D g2) {
        /*Works by first giving each ray its own angle. A xOffset and yOffset is calculated to figure out what is needed to hit the next
        horizontal / vertical line. Then the ray is extended until it intersects with a horizontal
        or vertical line in the grid. If it's a wall the distance and final point is saved. If it isn't than the ray adds the
        xOffset and yOffset until it is. This way you can figure out where each ray hits.
        * */
        int tSize = MainGame.dun.getDSIZE();
        int renderDist = 32; //amount of walls rendered when looking around
        int distScale = 50; //how far away things look
        int darkScale =Math.max(1000 -Game3D.getDeathAnimationCounter(), 0);
        int px = MainGame.player.x;
        int py = MainGame.player.y;
        double pa = MainGame.player.getAngle();
        int mx = 0, my = 0, mp = 0, dof;
        double rx = 0; //ray x
        double ry = 0; //ray y
        double ra; // ray angle
        double xo = 0; // x offset
        double yo = 0; // y offset
        double distT = 0; // total distance from player to end of ray
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
                mx = (int) ((rx) / tSize);
                my = (int) ((ry) / tSize);
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
            MainGame.player.setPlayerRay((int) rx, (int) ry);
            // drawing Setup

            /* Now that we have a distance, subtracting it from a max wall height will give us how tall the wall should be.
            * As the player gets further away, the ray dist gets longer ( to a certain amount) Now taking that length and
            * subtracting a constant max length will make it look like the wall scales with distance.
            * */
            double ca = fixAng(pa - ra);
            distT = distT * Math.cos(ca); //player to ray distance
            int lineH = (int) ((mapS * HGT) / distT / distScale); // line height when drawing (cap)
            double tyStep = 32 / (double) lineH;
            double tyOff = 0;
            if (lineH > HGT) {
                tyOff = (lineH - HGT) / 2.0F;
                lineH = HGT;//clipping height
            }
            double lineOff = (double) HGT / 2 - (lineH / 2F); // Starting point after cutoff ( >0)
            rayDist[r] = (int) distT;
            //---draw walls---
            /*For drawing textured walls, it uses the same process as just the vertical walls, but now each tile drawn is broken
            into the x and y. the closer you are, the more times tx and ty can step, so the higher quality the sprite looks.
            The texture is taken from a 1d colour array that is converted at the start of teh program for all the textures.
            Shading applied based on distance.
            * */
            double ty = tyOff * tyStep; //texture y val
            int tx;
            int texSize = 32;
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
                Color col = mapW[mp].getWallTexture()[pixel];
                float shading = 1;//(float) y / lineH;
                col = new Color(
                        (int) (col.getRed() * shading),
                        (int) (col.getGreen() * shading),
                        (int) (col.getBlue() * shading)
                );
                double darknessFactor = 1 - Math.min(distT/ darkScale, 1);
                col = applyDarkness(col, darknessFactor);
                g2.setColor((shade != 1) ? col : col.darker().darker());
                g2.drawLine(r * depth, (int) (y + lineOff), r * depth, (int) (y + lineOff));
                ty += tyStep; // Adjust texture coordinate
            }
            if(mapW[mp].getwCode() == -1) continue;
            //can skip floors for a node with a wall texture.
            //ceiling
            /*Now instead of getting the exact distance from each wall, we need a perspective to draw the ceil/floors.
            This is done by comparing the player angle and ray angle. The textures are calculated using a scaled x
            and y value that changes based on the perspective angle that was calculated.
            * */
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
                g2.setColor(applyDarkness(mapW[mp].getCeilTexture()[pixel], darknessFactor));
                g2.drawLine(r * depth, y, r * depth, y);
                g2.setColor(applyDarkness(mapW[mp].getFloorTexture()[pixel], darknessFactor));
                g2.drawLine(r * depth, HGT - y, r * depth, HGT - y);

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
        if (mp >= mapS) mp = mapS - 1;
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
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));
        return new Color(red, green, blue);
    }
}
