import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

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
    private final int texWidth = 64;
    private final int texHeight = 64;
    // Variable to determine whether the ray hit a north-south or east-west wall side

    public RayCaster(Player player, Dungeon dun) {
        this.player = player;
        this.dun = dun;
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
        int renderDist = mapS; //amount of walls rendered when looking around
        int distScale = 100; //how far away things look
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
        int depth = (int) (2*10/ resolution);
        g2.setStroke(new BasicStroke(depth));

        double pdx = Math.cos(pa);
        double pdy = Math.sin(pa);
        if(pdx<0){ xo=-10;} else{ xo=10;}//x offset to check map
        if(pdy<0){ yo=-10;} else{ yo=10;}//y offset to check map
        int ipx=px/tSize;
        int ipy=py/tSize;
        int ipx_add_xo=(int)(px+xo)/tSize;
        int ipy_add_yo=(int)(py+yo)/tSize;
        int ipx_sub_xo=(int)(px-xo)/tSize;//x position and offset
        int ipy_sub_yo=(int)(py-yo)/tSize;//y position and offset
        if (mapW[ipy * mapX + ipx_add_xo].getbCode() == 0)
            player.x += (int) Math.round(pdx * player.getSpeed());
        if (mapW[ipy_add_yo * mapX + ipx].getbCode() == 0)
            player.y += (int) Math.round(pdy * player.getSpeed());
        if (mapW[ipy * mapX + ipx_sub_xo].getbCode() == 0)
            player.x -= (int) Math.round(pdx * player.getSpeed());
        if (mapW[ipy_sub_yo * mapX + ipx].getbCode() == 0)
            player.y -= (int) Math.round(pdy * player.getSpeed());
        px = player.x;
        py = player.y;

        for (int r = 0; r < fov*2; r++) {
            int hmt = 0, vmt = 0;
            // Horizontal Lines
            float distH = 1000000000, hx = px, hy = py;
            dof = 0;
            float aTan = (float) (-1 / Math.tan(ra));
            if (ra > PI) {
                ry = (float) (py / tSize * tSize - 0.0001);
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
                if (mp > 0 && mp < mapX * mapY && mapW[mp].getbCode() != 0) {
                    hx = rx;
                    hy = ry;
                    hmt=mapW[mp].getbCode()-1;
                    distH = (float) dist(px, py, hx, hy);
                    dof = renderDist;
                } else {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }
            // Vertical Lines
            float distV = 1000000000, vx = px, vy = py;
            dof = 0;
            float nTan = (float) (-Math.tan(ra));
            double PI2 = Math.PI / 2;
            double PI3 = 3 * Math.PI / 2;
            if (ra > PI2 && ra < PI3) {
                rx = (float) (px / tSize * tSize - 0.0001);
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
                if (mp > 0 && mp < mapX * mapY && mapW[mp].getbCode() != 0) {
                    vx = rx;
                    vy = ry;
                    vmt=mapW[mp].getbCode()-1;
                    distV = (float) dist(px, py, vx, vy);
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
                hmt=vmt;
                shade = 0.5;
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
            // Walls
            float ca = (float) pa - ra;
            ca = fixAng(ca);
            distT = (float) (distT * Math.cos(ca));
            float lineH = (distT != 0) ? (mapS * HGT) / distT / distScale : 0;
            float lineO = Math.max(0, HGT / 2 - lineH / 2);
//            if (lineO < 0) lineO = 0;

            float ty_step=(float)tSize/lineH;
            float ty_off=0;
            int lineOff = HGT/2-((int)lineH>>1);
            if (lineH > HGT) lineH = HGT;
            //line offset
            ra += (float) DR;
            ra = fixAng(ra);
            if(mp >= 256)
                mp = 256;
            if(mp < 0)
                mp = 0;
//            g2.draw(new Line2D.Float(r * depth, lineO, r * depth, lineO + lineH));
            //---draw walls---
            int[] All_Textures = MainGame.imgArr[mapW[mp].getbCode()];
            float ty=ty_off*ty_step+hmt*tSize;
            float tx;
            if(shade==1){ tx=(int)(rx/2.0)%tSize; if(ra>PI){ tx=tSize-1-tx;}}
            else        { tx=(int)(ry/2.0)%tSize; if(ra> PI/2.0 && ra<3*PI/2.0){ tx=tSize-1-tx;}}
            for (int y = 0; y < lineH; y++) {
                int texInd = (int) (ty) * texWidth + (int) (tx);
                if(texInd >= 4096)
                    texInd = 4095;
                int c = All_Textures[texInd];
                if (shade != 1)
                    c = (c & 0xfefefe) >> 1;
                g2.setColor(new Color(c));
                g2.drawLine(r * depth, y + lineOff, r * depth, y + lineOff);
                ty += ty_step;
            }
        }
    }

    public float fixAng(float angle) {
        while (angle < 0)
            angle += (float) (2 * PI);
        while (angle > 2 * PI)
            angle -= (float) (2 * PI);
        return angle;
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

}
