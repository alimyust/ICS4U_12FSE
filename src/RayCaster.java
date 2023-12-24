import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

import static java.lang.Math.PI;

public class RayCaster{
    private final Player player;
    private final Dungeon dun;
    private final double resolution = 10; //(10 is max before it's too high resolution for the display size)
    private final double DR = 0.0174533/resolution;
    //changes what the degree value is so that more rays are drawn without actually increasing the fov
    private final int WID = Game3D.getWid3d();
    private final int HGT = Game3D.getHgt3d();
    private final int mapS;
    private final int mapX;
    private final int mapY;
    private final int[] map;
    public RayCaster(Player player, Dungeon dun){
        this.player = player;
        this.dun = dun;
        mapX = dun.getMap()[0].length;
        mapY = dun.getMap().length;
        mapS = mapX * mapY;
        map = flatten(dun.getMap());
    }
    public void drawRays3d(Graphics2D g2)
    {
        int tSize = dun.getDSIZE();
        int renderDist = 64; //amount of walls rendered when looking around
        int distScale = 32; //how far away things look
        int px = ParentEntity.x;
        int py = ParentEntity.y;
        double pa = player.getAngle();
        int mx, my, mp, dof;
        float rx = 0, ry = 0, ra, xo = 0, yo = 0, distT = 0;
        int fov = (int) (30 * resolution);
        ra = (float) (pa-DR*fov);
        if(ra < 0) { ra += 2 * PI; }
        if(ra > 2* PI) {ra -= 2* PI;}
        rx = Math.round(rx);
        ry = Math.round(ry);

        for (int r= 0; r < 2*fov; r++)
        {
            // Horizontal Lines
            float distH = 10000000, hx = px, hy = py;
            dof = 0;
            float aTan = (float) (-1 / Math.tan(ra));
            if(ra > PI) { ry = (float) (py / tSize * tSize -0.0001); rx = (py-ry)*aTan+px; yo = -tSize;xo = -yo*aTan;}
            if(ra < PI) { ry = (float) (py / tSize * tSize + tSize); rx = (py-ry)*aTan+px; yo = tSize; xo = -yo*aTan;}
            if(ra == 0 || ra == PI) {rx = px; ry = py; dof = renderDist;}
            while (dof < renderDist)
            {
                mx = (int) (rx) / tSize; my = (int) (ry) / tSize; mp = my*mapX + mx;
                if(mp > 0 && mp < mapX*mapY && map[mp]==0) {hx = rx; hy = ry; distH = (float)dist(px,py,hx,hy); dof = renderDist;}
                else  {rx += xo; ry += yo; dof += 1;}
            }
            // Vertical Lines
            float distV = 10000000, vx = px, vy = py;
            dof = 0;
            float nTan = (float) (-Math.tan(ra));
            double PI2 = Math.PI / 2;
            double PI3 = 3 * Math.PI / 2;
            if(ra > PI2 && ra < PI3) { rx = (float) (px / tSize * tSize -0.0001); ry = (px-rx)*nTan+py; xo = -tSize; yo = -xo*nTan;}
            if(ra < PI2 || ra > PI3) { rx = (float) (px / tSize * tSize + tSize); ry = (px-rx)*nTan+py; xo = tSize; yo = -xo*nTan;}
            if(ra == 0 || ra == PI) {rx = px; ry = py; dof = renderDist;}
            while (dof < renderDist)
            {
                mx = (int) (rx) / tSize; my = (int) (ry) / tSize; mp = my*mapX + mx;
                if(mp > 0 && mp < mapX*mapY && map[mp]==0) {vx = rx; vy = ry; distV = (float)dist(px,py,vx,vy); dof = renderDist;}
                else  {rx += xo; ry += yo; dof += 1;}
            }
            if(distV < distH) {
                rx = vx;
                ry = vy;
                distT = distV;
                Color c1 = new Color(155, 13, 13);
                g2.setColor(c1);
            }
            if(distV > distH) {
                rx = hx;
                ry = hy;
                distT = distH;
                Color c2 = new Color(91, 4, 4);
                g2.setColor(c2);
            }
            player.setPlayerRay((int) rx, (int) ry);

            // Walls
            float ca = (float)pa - ra;
            if(ca < 0) {ca += 2* PI;}
            if(ca >  2* PI) {ca -= 2* PI;}
            distT = (float) (distT *Math.cos(ca));
            float lineH = (distT != 0) ? (mapS * HGT) / distT/distScale : 0;
            float lineO = Math.max(0, HGT / 2 - lineH / 2);
            if (lineH > HGT)
                lineH = HGT;
            if (lineO<0)
                lineO = 0;
//            System.out.println(lineH + ", " + lineO);
            int depth = (int) (10 * 2.0 / resolution);
            g2.setStroke(new BasicStroke(depth));
            g2.draw(new Line2D.Float(r*depth, lineO, r*depth,lineH+lineO));


            ra += (float) DR; if(ra < 0) { ra += (float) (2 * PI);} if(ra > 2* PI) {ra -= (float) (2* PI);}
        }
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

    public static int[] flatten(int[][] array) {
//        System.out.println(Arrays.deepToString(array));
        int rows = array.length;
        int cols = array[0].length;
        int[] flattenedArray = new int[rows * cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(array[i], 0, flattenedArray, i * cols, cols);
        return flattenedArray;
    }

}
