import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

import static java.lang.Math.PI;

public class RayCaster{
    private final Player player;
    private final Dungeon dun;
    private final double resolution = 5; //(10 is max before it's too high resolution for the display size)
    private final double DR = 0.0174533/resolution;
    //changes what the degree value is so that more rays are drawn without actually increasing the fov
    private final int WID = Game3D.getWid3d();
    private final int HGT = Game3D.getHgt3d();
    private final int mapS;
    private final int mapX;
    private final int mapY;
    private final MapNode[] map;
    private final int texWidth = 64;
    private final int texHeight = 64;
    private final int[][] texture = new int[8][texWidth * texHeight];
    private int side; // Variable to determine whether the ray hit a north-south or east-west wall side
    public RayCaster(Player player, Dungeon dun){
        this.player = player;
        this.dun = dun;
        mapX = dun.getMap()[0].length;
        mapY = dun.getMap().length;
        mapS = mapX * mapY;
        map = flatten(dun.getMap());
        loadTextures();
    }
        private void loadTextures(){
            for (int x = 0; x < texWidth; x++) {
                for (int y = 0; y < texHeight; y++) {
                    int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
                    // int xcolor = x * 256 / texWidth;
                    int ycolor = y * 256 / texHeight;
                    int xycolor = y * 128 / texHeight + x * 128 / texWidth;

                    texture[0][y * texWidth + x] = 65536 * 254 * (((x != y) && (x != texWidth - y))? 1:0); // flat red texture with black cross
                    texture[1][y * texWidth + x] = xycolor + 256 * xycolor + 65536 * xycolor; // sloped greyscale
                    texture[2][y * texWidth + x] = 256 * xycolor + 65536 * xycolor; // sloped yellow gradient
                    texture[3][y * texWidth + x] = xorcolor + 256 * xorcolor + 65536 * xorcolor; // xor greyscale
                    texture[4][y * texWidth + x] = 256 * xorcolor; // xor green
                    texture[5][y * texWidth + x] = 65536 * 192 * (((x % 16 != 0) && (y % 16 != 0))? 1:0); // red bricks
                    texture[6][y * texWidth + x] = 65536 * ycolor; // red gradient
                    texture[7][y * texWidth + x] = 128 + 256 * 128 + 65536 * 128; // flat grey texture
                }
            }

    }
    public void drawRays3d(Graphics2D g2)
    {
        int tSize = dun.getDSIZE();
        int renderDist = 64; //amount of walls rendered when looking around
        int distScale = 32; //how far away things look
        int px = player.x;
        int py = player.y;
        double pa = player.getAngle();
        int mx, my, mp = 0, dof, texNum = 0;
        float rx = 0, ry = 0, ra, xo = 0, yo = 0, distT = 0;
        int fov = (int) (30 * resolution);
        ra = (float) (pa-DR*fov);
        if(ra < 0) { ra += 2 * PI; }
        if(ra > 2* PI) {ra -= 2* PI;}

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
                if(mp > 0 && mp < mapX*mapY && map[mp].getbCode()!=0) {hx = rx; hy = ry; distH = (float)dist(px,py,hx,hy); dof = renderDist;}
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
                if(mp > 0 && mp < mapX*mapY && map[mp].getbCode()!=0) {vx = rx; vy = ry; distV = (float)dist(px,py,vx,vy); dof = renderDist;}
                else  {rx += xo; ry += yo; dof += 1;}
            }
            if(distV < distH) {
                rx = vx;
                ry = vy;
                distT = distV;
                Color c1 = new Color(155, 13, 13);
                g2.setColor(c1);
                side = 1;
            }
            if(distV > distH) {
                rx = hx;
                ry = hy;
                distT = distH;
                Color c2 = new Color(91, 4, 4);
                g2.setColor(c2);
                side = 0;
            }
            player.setPlayerRay((int) rx, (int) ry);

            // Walls
            float ca = (float)pa - ra;
            if(ca < 0) {ca += 2* PI;}
            if(ca >  2* PI) {ca -= 2* PI;}
            distT = (float) (distT *Math.cos(ca));
            float lineH = (distT != 0) ? (mapS * HGT) / distT / distScale : 0;
            float lineO = Math.max(0, HGT / 2 - lineH / 2);
            if (lineH > HGT)
                lineH = HGT;
            if (lineO<0)
                lineO = 0;
//            System.out.println(lineH + ", " + lineO);
            int depth = (int) (10 * 2.0 / resolution);
            g2.setStroke(new BasicStroke(depth));
//            g2.draw(new Line2D.Float(r*depth, lineO, r*depth,lineH+lineO));


            // Inside the for loop, after setting the color based on distV and distH
            mp = Math.max(mp, 0);
            mp = Math.min(mp, map.length - 1);
            texNum = map[mp].getbCode(); // 1 subtracted from it so that texture 0 can be used
            double wallX;
            if (side == 0)
                wallX = py + distT * ry;
            else
                wallX = px + distT * rx;
            wallX -= Math.floor(wallX);
            int texX = (int) (wallX * texWidth);

            double step = 1.0 * texHeight / lineH;
            double texPos = (lineO - HGT / 2 + lineH / 2) * step;

            for (int y = (int) lineO; y < lineH + lineO; y++) {
                int texY = (int) texPos & (texHeight - 1);
                texPos += step;
                int color = texture[6][texHeight * texY + texX];
                // Make color darker for y-sides: R, G, and B byte each divided through two with a "shift" and an "and"
                if (side == 1)
                    color = (color >> 1) & 8355711;
                if(y == lineH + lineO - 1)
                    System.out.println(color);
                g2.setColor(new Color(color));
                g2.drawLine(r * depth, y, r * depth, y);
            }


            ra += (float) DR; if(ra < 0) { ra += (float) (2 * PI);} if(ra > 2* PI) {ra -= (float) (2* PI);}
        }
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
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

}
