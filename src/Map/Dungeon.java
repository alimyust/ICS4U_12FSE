package Map;

import Enemies.BaseEnemy;
import Player.Player;
import MainGame.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
*  A Map.Dungeon object would include everything that would be contained per level. This includes the actual dungeon and
* what it's made of, but also the enemies, power ups, etc.
* */
public class Dungeon {
    private final int DSIZE = 64;
    private final int dSizeMultiplier = 4;
    private final int WID = MainGame.getWID() / DSIZE * dSizeMultiplier;
    private final int HGT = MainGame.getHGT() / DSIZE * dSizeMultiplier;
    private final int ALIVE = 0; // empty space is alive
    private final int DEAD = 1; // dead is wall
    private MapNode[][] map = new MapNode[HGT][WID];
    private static ArrayList<BaseEnemy> eArr;
    private final ArrayList<Point> openSpaces;
    private final Point wallCode;
    private final Point floorCode;
    private final Point ceilCode;
    private Point doorPoint = new Point(0,0); //gateway to next level
    public Dungeon(Point wallCode, Point floorCode, Point ceilCode, String pattern) {
        this.wallCode = wallCode;
        this.floorCode = floorCode;
        this.ceilCode = ceilCode;
        openSpaces = new ArrayList<>();
        eArr = new ArrayList<>();
        makeDungeon();
        while(true)
        {
            boolean breakOut = false;
            for (Point openPoint : openSpaces) {
                if (dist(openPoint.x, openPoint.y, 10, 10) > 50) {
                    doorPoint = openPoint;
                    breakOut = true;
                }
            }
            if(breakOut) break;
            makeDungeon();
        }
        addEnemies();
        map[doorPoint.y][doorPoint.x] = new MapNode(new Point(1,1), floorCode, ceilCode);
        map[doorPoint.y][doorPoint.x].setwCode(5);
    }
    public int dist(int ax, int ay, int bx, int by) {
        return (int) Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    }
    private void makeDungeon(){
        int c;
        do {
            openSpaces.clear();
            generateGrid(0.50);
            automata(20, 5, 4);
            map = makeBorder(map);
            c = floodFill(8,8, -1);
        } while (!((double) c / (double) (HGT * WID) > 0.3)); // 30 percent of map must be explorable
        for (int i = 0; i < HGT; i++) {
            for (int j = 0; j < WID; j++) {
                int wCodetmp = map[i][j].getwCode();
                if(wCodetmp == 0) wCodetmp = 2;
                if(wCodetmp == -1) wCodetmp = 0;
                map[i][j] = new MapNode(wallCode, floorCode, ceilCode);
                map[i][j].setwCode(wCodetmp);
            }
        }
    }
    private void addEnemies(){
        eArr= BaseEnemy.addEnemy(eArr, getOpenSpaces(), 1 + Game3D.getLvl()/2, BaseEnemy.GOBLINSLINGER);
        eArr= BaseEnemy.addEnemy(eArr, getOpenSpaces(), 3+ Game3D.getLvl()/2, BaseEnemy.SKELETON);
        eArr= BaseEnemy.addEnemy(eArr, getOpenSpaces(), 1 + Game3D.getLvl()/4, BaseEnemy.GOBLINBERSERKER);

    } // java.awt.Point[x=6,y=3], java.awt.Point[x=6,y=3], java.awt.Point[x=6,y=1]

    public void applyBlotch(int centerX, int centerY, int radius,  Point wallCode,Point floorCode, Point ceilCode) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int newX = centerX + i;
                int newY = centerY + j;
                if (newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length) {
                    if (i * i + j * j <= radius * radius) {
                        int wCode = map[newY][newX].getwCode();
                        map[newY][newX] = new MapNode(wallCode, floorCode, ceilCode);
                        map[newY][newX].setwCode(wCode);
                    }
                }
            }
        }
    }
    private int floodFill(int cx, int cy, int mark) {
        if (spotIsOffGrid(cx, cy) || map[cy][cx].getwCode() != ALIVE)
            return 0;
        map[cy][cx] = new MapNode(mark);
        openSpaces.add(new Point(cx,cy));
        int count = 1;
        count += floodFill(cx + 1, cy, mark) +
                floodFill(cx - 1, cy, mark) +
                floodFill(cx, cy + 1, mark) +
                floodFill(cx, cy - 1, mark);
        return count;
    }

    public int[] generateRow(int width, double aliveProbability) {
        int[] row = new int[width];
        for (int i = 0; i < width; i++)
            row[i] = (Math.random() < aliveProbability) ? ALIVE : DEAD;
        return row;
    }

    public void generateGrid(double aliveProbability) {
        for (int i = 0; i < HGT; i++) {
            int[] row = generateRow(WID, aliveProbability);
            for (int j = 0; j < WID; j++) {
                map[i][j] = new MapNode(row[j]);
            }
        }
    }

    public boolean spotIsOffGrid(int x, int y) {
        return (x < 0 || x > WID || y < 0 || y > HGT);
    }

    public int[] neighborValues(int x, int y) {
        int[] values = new int[8];
        int index = 0;

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, WID - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, HGT - 1); j++) {
                if (i != x || j != y) { // We only care about neighbors
                    if (spotIsOffGrid(i, j)) values[index++] = ALIVE; // Assuming out-of-bounds cells are alive
                    else values[index++] = map[j][i].getwCode();
                }
            }
        }

        return Arrays.copyOf(values, index);
    }

    public int newValueAtPosition(int x, int y, int birthThreshold, int survivalThreshold) {
        int cellValue = map[y][x].getwCode();
        int[] neighborValues = neighborValues(x, y);
        long aliveNeighbors = Arrays.stream(neighborValues).filter(value -> value == ALIVE).count();
        if (cellValue == ALIVE && aliveNeighbors >= survivalThreshold)
            return ALIVE;
        if (cellValue == DEAD && aliveNeighbors >= birthThreshold)
            return ALIVE;
        return DEAD;
    }

    public void automataIteration(int birthThreshold, int survivalThreshold) {
        MapNode[][] newGrid = new MapNode[HGT][WID];
        for (int y = 0; y < HGT; y++) {
            for (int x = 0; x < WID; x++) {
                newGrid[y][x] = new MapNode(newValueAtPosition(x, y, birthThreshold, survivalThreshold));
            }
        }

        map = newGrid;
    }

    public void automata(int iterations, int birthThreshold, int survivalThreshold) {
        for (int i = 0; i < iterations; i++)
            automataIteration(birthThreshold, survivalThreshold);
    }
    public MapNode[][] makeBorder(MapNode[][] map){
        for(int i=0; i < map[0].length; i++){
            map[0][i] = new MapNode(DEAD);
            map[map.length-1][i] = new MapNode(DEAD);
        }
        for(int i=0; i < map.length; i++){
            map[i][0] = new MapNode(DEAD);
            map[i][map.length-1] = new MapNode(DEAD);
        }
        return map;
    }

    public void drawDungeon2D(Graphics g, int xOff, int yOff, int rVal) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));
        int minimapRange = 30; // total amount of grid squares that can be seen
        // Calculate the player's position in the grid
        int playerGridX = MainGame.player.x / DSIZE;
        int playerGridY = MainGame.player.y / DSIZE;

        // Calculate the bounds for the displayed area
        int displayX = playerGridX - minimapRange/2; ;//Math.max(0, playerGridX - minimapRange/2);  // Display 10 blocks, 5 on each side of the player
        int displayY = playerGridY - minimapRange/2;//Math.max(0, playerGridY - minimapRange/2);
        int xRange = playerGridX +minimapRange;//Math.min(playerGridX +minimapRange, WID-1);
        int yRange = playerGridY +minimapRange;//Math.min(playerGridY +minimapRange, HGT-1);
        // Draw a white background
        for (int y = displayY; y <  yRange; y++) {
            for (int x = displayX; x <  xRange; x++) {
                int wallCode = map[Math.max(0, Math.min(y, HGT - 1))][Math.max(0, Math.min(x, WID - 1))].getwCode();
                g.setColor(switch (wallCode){ // ^ Everything out of bounds should be a wall, so this doesn't lose any data
                    case 0 -> Color.WHITE;
                    case 5 -> new Color(34, 158, 225, 255);
                    default -> Color.BLACK;
                });
                g.fillRect(xOff + (x - displayX) * DSIZE / rVal, yOff + (y - displayY) * DSIZE / rVal, DSIZE / rVal, DSIZE / rVal);
                g.setColor(Color.ORANGE);
                g.fillOval(xOff +(minimapRange/2)* DSIZE / rVal-4,yOff +(minimapRange/2)* DSIZE / rVal-4,8,8);
            }
        }

    }



    public MapNode[][] getMap() {
        return map;
    }

    public int getDSIZE() {
        return DSIZE;
    }

    public int getdSizeMultiplier() {
        return dSizeMultiplier;
    }

    public int getWID() {
        return WID;
    }

    public int getHGT() {
        return HGT;
    }

    public Point getDoorPoint() {
        return doorPoint;
    }

    public ArrayList<Point> getOpenSpaces() {
        return openSpaces;
    }
    public Point getRandomOpenPoint(){
        return openSpaces.get((int) (Math.random() * (openSpaces.size()-1)));
    }

    public ArrayList<BaseEnemy> geteArr() {
        return eArr;
    }
}
