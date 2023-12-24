import java.awt.*;
import java.util.Arrays;

public class Dungeon {
    private final int DSIZE = 16;
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
        int c;
        do {
            generateGrid(0.50);
            automata(20, 5, 4);
            c = floodFill(HGT/2, WID/2, 2);
        } while ((double)c / (double)(HGT * WID) <= 0.005);
        //determines the minimum size in percent of alive cells compared to entire map

        // Fill everything else
        for (int i = 0; i < HGT; i++)
            for (int j = 0; j < WID; j++)
                if (map[i][j] != 2)
                    map[i][j] = 0;
    }

    private int floodFill(int cx, int cy, int mark) {
        if (spotIsOffGrid(cx, cy) || map[cy][cx] != 1)
            return 0;
        map[cy][cx] = mark;
        int count = 1;
        count += floodFill(cx + 1, cy, mark)+
                 floodFill(cx - 1, cy, mark)+
                 floodFill(cx, cy + 1, mark)+
                 floodFill(cx, cy - 1, mark);
        return count;
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
                g.fillRect(x * DSIZE, y * DSIZE, DSIZE, DSIZE);
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getDSIZE() {
        return DSIZE;
    }
}
