import java.awt.*;
import java.awt.image.BufferedImage;

public class RayCaster {
    private final Player player;
    private final Dungeon dun;
    private final MapNode[][] map;
    private final int texWidth = 64;
    private final int texHeight = 64;
    private final int[][] texture = new int[8][texWidth * texHeight];
    int screenWidth = Game3D.getWid3d();
    int screenHeight = Game3D.getHgt3d();
    BufferedImage bufferImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
    int[][] buffer = new int[screenHeight][screenWidth];
    private double posX = 22.0;
    private double posY = 11.5;
    private double dirX = -1.0;
    private double dirY = 0.0;
    private double planeX = 0.0;
    private double planeY = 0.66;

    public RayCaster(Player player, Dungeon dun) {
        this.player = player;
        this.dun = dun;
        map = dun.getMap();
        loadTextures();
    }

    private void loadTextures() {
        for (int x = 0; x < texWidth; x++) {
            for (int y = 0; y < texHeight; y++) {
                int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
                // int xcolor = x * 256 / texWidth;
                int ycolor = y * 256 / texHeight;
                int xycolor = y * 128 / texHeight + x * 128 / texWidth;

                texture[0][y * texWidth + x] = 65536 * 254 * (((x != y) && (x != texWidth - y)) ? 1 : 0); // flat red texture with black cross
                texture[1][y * texWidth + x] = xycolor + 256 * xycolor + 65536 * xycolor; // sloped greyscale
                texture[2][y * texWidth + x] = 256 * xycolor + 65536 * xycolor; // sloped yellow gradient
                texture[3][y * texWidth + x] = xorcolor + 256 * xorcolor + 65536 * xorcolor; // xor greyscale
                texture[4][y * texWidth + x] = 256 * xorcolor; // xor green
                texture[5][y * texWidth + x] = 65536 * 192 * (((x % 16 != 0) && (y % 16 != 0)) ? 1 : 0); // red bricks
                texture[6][y * texWidth + x] = 65536 * ycolor; // red gradient
                texture[7][y * texWidth + x] = 128 + 256 * 128 + 65536 * 128; // flat grey texture
            }
        }

    }

    public void drawRays3d(Graphics2D g2) {
        for (int x = 0; x < screenWidth; x++) {
            //calculate ray position and direction
            double cameraX = 2 * x / (double) screenWidth - 1;
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            //which box of the map we're in
            posX = player.x / dun.getDSIZE();
            posY = player.y / dun.getDSIZE();
            int mapX = (int) posX;
            int mapY = (int) posY;

            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            //length of ray from one x or y-side to next x or y-side
            double deltaDistX = (rayDirX == 0) ? 1e30 : Math.abs(1 / rayDirX);
            double deltaDistY = (rayDirY == 0) ? 1e30 : Math.abs(1 / rayDirY);
            double perpWallDist;

            //what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int hit = 0; //was there a wall hit?
            int side = 0; //was a NS or a EW wall hit?

            //calculate step and initial sideDist
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }
            //perform DDA
            while (hit == 0) {
                //jump to next map square, either in x-direction, or in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                if (map[mapX][mapY].getbCode() > 0) hit = 1;
            }

            //Calculate distance of perpendicular ray (Euclidean distance would give fisheye effect!)
            if (side == 0) perpWallDist = (sideDistX - deltaDistX);
            else perpWallDist = (sideDistY - deltaDistY);

            //Calculate height of line to draw on screen
            int lineHeight = (int) (screenHeight / perpWallDist);

            int pitch = 120;

            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + screenHeight / 2 + pitch;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + screenHeight / 2 + pitch;
            if (drawEnd >= screenHeight) drawEnd = screenHeight - 1;

            //texturing calculations
            int texNum = map[mapX][mapY].getbCode() - 1; //1 subtracted from it so that texture 0 can be used!

            //calculate value of wallX
            double wallX; //where exactly the wall was hit
            if (side == 0) wallX = posY + perpWallDist * rayDirY;
            else wallX = posX + perpWallDist * rayDirX;
            wallX -= Math.floor(wallX);

            //x coordinate on the texture
            int texX = (int) (wallX * texWidth);
            if (side == 0 && rayDirX > 0) texX = texWidth - texX - 1;
            if (side == 1 && rayDirY < 0) texX = texWidth - texX - 1;

            // TODO: an integer-only Bresenham or DDA-like algorithm could make the texture coordinate stepping faster
            // How much to increase the texture coordinate per screen pixel
            double step = 1.0 * texHeight / lineHeight;
            // Starting texture coordinate
            for (int y = 0; y < drawStart; y++) {
                buffer[y][x] = 0xFF808080; // color for ceiling (grey in this case)
            }
            double texPos = (drawStart - pitch - screenHeight / 2 + lineHeight / 2) * step;
            for (int y = drawStart; y < drawEnd; y++) {
                // Cast the texture coordinate to integer, and mask with (texHeight - 1) in case of overflow
                int texY = (int) texPos & (texHeight - 1);
                texPos += step;
                int color = texture[texNum][texHeight * texY + texX];
                //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
                if (side == 1) color = (color >> 1) & 8355711;
                buffer[y][x] = color;
            }
            // draw floor
            for (int y = drawEnd; y < screenHeight; y++) {
                buffer[y][x] = 0xFF404040; // color for floor (darker grey in this case)
            }
//            System.out.println(mapX + " , "+ mapY);
//            g2.setColor(Color.green);
//            g2.drawRect(mapX*dun.getDSIZE(), mapY*dun.getDSIZE(), dun.getDSIZE(),dun.getDSIZE());
        }
        drawBuffer(g2, buffer);

    }

    private void drawBuffer(Graphics2D g2, int[][] buffer) {
        bufferImage.setRGB(0, 0, screenWidth, screenHeight, flattenBuffer(buffer), 0, screenWidth);
        g2.drawImage(bufferImage, 0, 0, null);

    }

    private int[] flattenBuffer(int[][] buffer) {
        int[] flattened = new int[screenWidth * screenHeight];
        for (int y = 0; y < screenHeight; y++)
            System.arraycopy(buffer[y], 0, flattened, y * screenWidth, screenWidth);
        return flattened;
    }

    public double dist(double ax, double ay, double bx, double by) {
        return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }

    public void updatePlayerPosition(double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    public void updatePlayerDirection(double dirX, double dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
        double FOV = -Math.PI/2;// Set your desired field of view
        this.planeX = dirY * Math.tan(FOV / 2);
        this.planeY = -dirX * Math.tan(FOV / 2);
    }
}
