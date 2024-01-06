import java.awt.*;

import static java.lang.Math.PI;

public class RayCaster {
    private final Player player;
    private final Dungeon dun;
    private final double resolution = 4; //(10 is max before it's too high resolution for the display size)
    private final double DR = 0.0174533 / resolution;
    private final int WID = Game3D.getWid3d();
    private final int HGT = Game3D.getHgt3d();
    private final int mapS;
    private final int mapX;
    private final int mapY;
    private final MapNode[] mapW;
    private final BaseEnemy testE;
    public RayCaster(Player player, Dungeon dun) {
        this.player = player;
        this.dun = dun;
        this.testE = new BaseEnemy(64*12, 64*12, 2,10,10,0,0);
        mapX = dun.getMap()[0].length;
        mapY = dun.getMap().length;
        mapS = mapX * mapY;
        mapW = flatten(dun.getMap());
    }

    public static MapNode[] flatten(MapNode[][] array) {
//        System.out.println(Arrays.deepToString(array));
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
        int px = player.x;
        int py = player.y;
        double pa = player.getAngle();
        int mx, my, mp = 0, dof;
        float rx = 0;
        float ry = 0;
        float ra;
        float xo;
        float yo;
        float distT = 0;
        int fov = (int) (30 * resolution);
        ra = (float) (pa - DR * fov);
        int depth = (WID / (fov * 2));
        g2.setStroke(new BasicStroke(depth));

        double pdx = Math.cos(pa);
        double pdy = Math.sin(pa);
        if (pdx < 0) {
            xo = -10;
        } else {
            xo = 10;
        }//x offset to check map
        if (pdy < 0) {
            yo = -10;
        } else {
            yo = 10;
        }//y offset to check map
        int ipx = px / tSize;
        int ipy = py / tSize;
        int ipx_add_xo = (int) (px + xo) / tSize;
        int ipy_add_yo = (int) (py + yo) / tSize;
        int ipx_sub_xo = (int) (px - xo) / tSize;//x position and offset
        int ipy_sub_yo = (int) (py - yo) / tSize;//y position and offset
        if (mapW[ipy * mapX + ipx_add_xo].getwCode() == 0) player.x += (int) Math.round(pdx * player.getSpeed());
        if (mapW[ipy_add_yo * mapX + ipx].getwCode() == 0) player.y += (int) Math.round(pdy * player.getSpeed());
        if (mapW[ipy * mapX + ipx_sub_xo].getwCode() == 0) player.x -= (int) Math.round(pdx * player.getSpeed());
        if (mapW[ipy_sub_yo * mapX + ipx].getwCode() == 0) player.y -= (int) Math.round(pdy * player.getSpeed());
        px = player.x;
        py = player.y;

        for (int r = 0; r < fov * 2; r++) {
            // Horizontal Lines
            float distH = 1000000000, hx = px, hy = py;
            dof = 0;
            float aTan = (float) (-1 / Math.tan(ra));
            if (ra > PI) {
                ry = (float) (py / tSize * tSize - 0.001);
                rx = (py - ry) * aTan + px;
                yo = -tSize;
                xo = -yo * aTan;
            }
            if (ra < PI) {
                ry = (float) (py / tSize * tSize + tSize);
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
                    distH = (float) dist(px, py, hx, hy);
                    dof = renderDist;
                } else { // If a wall isn't found add the x and y offset
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }
            // Vertical Lines
            float distV = 1000000000, vx = px, vy = py;
            dof = 0;
            float nTan = (float) (-Math.tan(ra));
            double PI2 = Math.PI / 2; //90
            double PI3 = 3 * Math.PI / 2; //270
            if (ra > PI2 && ra < PI3) {
                rx = (float) (px / tSize * tSize - 0.001);
                ry = (px - rx) * nTan + py;
                xo = -tSize;
                yo = -xo * nTan;
            }
            if (ra < PI2 || ra > PI3) {
                rx = (float) (px / tSize * tSize + tSize);
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
                if (mp > 0 && mp <mapS && mapW[mp].getwCode() != 0) {
                    vx = rx;
                    vy = ry;
                    distV = (float) dist(px, py, vx, vy);
                    dof = renderDist;
                } else {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }
            float shade = 1;
            if (distV < distH) {
                rx = vx;
                ry = vy;
                distT = distV;
                shade = 0.5F;
                Color c1 = new Color(155, 13, 13);
                g2.setColor(c1);
            }
            if (distV > distH) {
                rx = hx;
                ry = hy;
                distT = distH;
                Color c2 = new Color(91, 4, 4);
                g2.setColor(c2);
            }
            player.setPlayerRay((int) rx, (int) ry);
            // drawing Setup
            float ca = fixAng((float) pa - ra);
            distT = (float) (distT * Math.cos(ca)); //player to ray distance
            int lineH = (int) ((mapS * HGT) / distT / distScale); // line height when drawing
            float tyStep = 32 / (float) lineH;
            float tyOff = 0;

            if (lineH > HGT) {
                tyOff = (lineH - HGT) / 2.0F;
                lineH = HGT;//clipping height
            }
            float lineOff = (float) HGT / 2 - (lineH / 2F); // Starting point after cutoff ( >0)
            //---draw walls---
            float ty = tyOff * tyStep; //texture y val
            int tx;
            int texSize = tSize/2;
            int texSize_1 = texSize -1 ;
            if (shade == 1) {
                tx = (int) ((rx / 2.0) % texSize);
                if (ra < PI) tx = texSize_1- tx; //greater than 180
            } else {
                tx = (int)((ry / 2.0) % texSize);
                if (ra > PI2 && ra < PI3)  tx = texSize_1 - tx; //greater than 90 and less than 270
            }
            if(mp >= mapW.length- 1) mp = mapW.length-1;
            if(mp < 0) mp =0;

            for (int y = 0; y < lineH; y++) {
                int pixel=((int)ty*texSize+ tx);
                Color col = MainGame.imgArr[mapW[mp].getwCode()][pixel];
                g2.setColor((shade != 1) ? col : col.darker().darker());
                g2.drawLine(r * depth, (int) (y + lineOff), r * depth, (int) (y + lineOff));
                ty += tyStep; // Adjust texture coordinate
            }
//            float dy = y-(HGT/2);
//            double raFix = Math.cos(fixAng(pa-ra));
//            tx = (int) (px/2 + Math.cos(ra)*(HGT/2)*32/dy/raFix);
//            ty = (int) (px/2 + Math.cos(ra)*(HGT/2)*32/dy/raFix); hardwood
            float tileScale = 2F; // Adjust this value as needed
            //ceiling
            for(int y=(int)lineOff+lineH;y<HGT;y++)
            {
                float dy= y - (float) HGT/2.0f; //distance from end of wall to end of screen
                float raFix=  (float)Math.cos(fixAng(pa-ra));
                // Adjust the floor tile size by scaling the texture coordinates
                float scaledPx = px / tileScale;
                float scaledPy = py / tileScale;
                tx = (int) (scaledPx + Math.cos(ra) * (480)* 32 / dy / raFix);
                ty = (int) (scaledPy + Math.sin(ra) * (480) * 32 / dy / raFix);
                //hours and hours of trial and error to align the floor and ceiling values for tx and ty
                mp = (int)(ty / 32)*mapX + tx /32;
                int pixel=(((int)(ty) & 31)*texSize + (tx & 31));
                //draw floor
                if (mapW[mp].getfCode() != -1) {
                    g2.setColor(MainGame.imgArr[mapW[mp].getfCode()][pixel]);
                    g2.drawLine(r * depth, y, r * depth, y);
                }
                if (mapW[mp].getcCode() != -1) {
                    g2.setColor(MainGame.imgArr[mapW[mp].getcCode()][pixel]);
                    g2.drawLine(r * depth, HGT - y, r * depth, HGT - y);
                }
            }
            ra += (float) DR;
            ra = fixAng(ra);
        }
        testE.drawBaseEnemy(g2,px,py,pa, HGT, WID);
    }

    public float fixAng(float angle) {
        if (angle < 0) angle += (float) (2 * PI);
        if (angle > 2 * PI) angle -= (float) (2 * PI);
        return angle;
    }

    public double fixAng(double angle) {
        if (angle < 0) angle += (2 * PI);
        if (angle > 2 * PI) angle -= (2 * PI);
        return angle;
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

}
