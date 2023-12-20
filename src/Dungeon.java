import java.awt.*;
import java.util.Arrays;

public class Dungeon {
    private final int DSIZE = 8;
    //dungeon size. Smaller values make larger dungeons
    private final int WID = MainGame.WID / DSIZE;
    //same divisor keeps it proportional to total size of display
    private final int HGT = MainGame.HGT / DSIZE;
    private final Color WHITE = new Color(255, 255, 255);
    private final Color BLACK = new Color(0, 0, 0);
    Color cellCol;
    private int[][] map = new int[HGT][WID];
    private final int ALIVE = 1;
    private final int DEAD = 0; // dead is wall

    public Dungeon() {
//        map = automata(WID,HGT,0.55,5,4,4);
        generateGrid(0.53);
        automata(20, 5, 4);
        System.out.println(Arrays.deepToString(map));
        int[][] newMap = new int[WID][HGT];
        floodFill(300,300,newMap);
    }
    private void floodFill(int cx, int cy, int[][] newMap){
        if(newMap[cx][cy] != -1) // Has been visited
            return;
        if(map[cy][cx] == 0)// Is a wall
        {
            newMap[cy][cx] = 0;
            return;
        }
        floodFill(cx+1,cy+1,newMap);
        floodFill(cx+1,cy-1,newMap);
        floodFill(cx-1,cy+1,newMap);
        floodFill(cx-1,cy-1,newMap);

    }

    public int[] generateRow(int width, double aliveProbability) {
        int[] row = new int[width];
        for (int i = 0; i < width; i++)
            row[i] = (Math.random() < aliveProbability) ? 1 : 0;
        return row;
    }

    public void generateGrid(double aliveProbability) {
        for (int i = 0; i < HGT; i++)
            map[i] = generateRow(WID, aliveProbability);
    }

    public boolean spotIsOffGrid(int x, int y) {
        int height = map.length;
        int width = (height > 0) ? map[0].length : 0;
        return (x < 0 || x >= width || y < 0 || y >= height);
    }

    public int[] neighborValues(int x, int y) {
        int[] values = new int[8];
        int index = 0;

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, map[0].length - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, map.length - 1); j++) {
                if (i != x || j != y) { // We only care about neighbors
                    if (spotIsOffGrid(i, j)) values[index++] = 1; // Assuming out-of-bounds cells are alive
                    else values[index++] = map[j][i];
                }
            }
        }

        return Arrays.copyOf(values, index);
    }

    public int newValueAtPosition(int x, int y, int birthThreshold, int survivalThreshold) {
        int cellValue = map[y][x];
        int[] neighborValues = neighborValues(x, y);
        long aliveNeighbors = Arrays.stream(neighborValues).filter(value -> value == 1).count();
        if (cellValue == 1 && aliveNeighbors >= survivalThreshold)
            return 1;
        if (cellValue == 0 && aliveNeighbors >= birthThreshold)
            return 1;
        return 0;
    }

    public void automataIteration(int birthThreshold, int survivalThreshold) {
        int height = map.length;
        int width = (height > 0) ? map[0].length : 0;
        int[][] newGrid = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newGrid[y][x] = newValueAtPosition(x, y, birthThreshold, survivalThreshold);
            }
        }

        map = newGrid;
    }

    public void automata(int iterations, int birthThreshold, int survivalThreshold) {
        for (int i = 0; i < iterations; i++)
            automataIteration(birthThreshold, survivalThreshold);
    }

    public void drawDungeon2D(Graphics g) {
        for (int y = 0; y < HGT; y += 1) {
            for (int x = 0; x < WID; x += 1) {
                cellCol = (map[y][x] == DEAD) ? BLACK : WHITE;
                g.setColor(cellCol);
                g.fillRect(x * DSIZE / 2, y * DSIZE / 2, DSIZE / 2, DSIZE / 2);
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getDSIZE() {
        return DSIZE;
    }

    public int getWID() {
        return WID;
    }

    public int getHGT() {
        return HGT;
    }
}
