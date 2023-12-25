import java.awt.*;
import java.util.Arrays;

public class Dungeon {
    private final int DSIZE = 32;
    private final int WID = MainGame.WID / DSIZE;
    private final int HGT = MainGame.HGT / DSIZE;
    private final Color WHITE = new Color(255, 255, 255);
    private final Color BLACK = new Color(0, 0, 0);
    Color cellCol;
    private MapNode[][] map = new MapNode[HGT][WID];
    private final int ALIVE = 0; // empty space is alive
    private final int DEAD = 1; // dead is wall

    public Dungeon() {
        int c;
        do {
            generateGrid(0.50);
            automata(20, 5, 4);
            c = floodFill(HGT / 2, WID / 2, 2);
        } while ((double) c / (double) (HGT * WID) <= 0.005);

        // Fill everything else
        for (int i = 0; i < HGT; i++)
            for (int j = 0; j < WID; j++)
                map[i][j] = new MapNode((map[i][j].getbCode() == 2) ? ALIVE : DEAD);
    }

    private int floodFill(int cx, int cy, int mark) {
        if (spotIsOffGrid(cx, cy) || map[cy][cx].getbCode() != ALIVE)
            return 0;
        map[cy][cx] = new MapNode(mark);
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
        return (x < 0 || x >= WID || y < 0 || y >= HGT);
    }

    public int[] neighborValues(int x, int y) {
        int[] values = new int[8];
        int index = 0;

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, WID - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, HGT - 1); j++) {
                if (i != x || j != y) { // We only care about neighbors
                    if (spotIsOffGrid(i, j)) values[index++] = ALIVE; // Assuming out-of-bounds cells are alive
                    else values[index++] = map[j][i].getbCode();
                }
            }
        }

        return Arrays.copyOf(values, index);
    }

    public int newValueAtPosition(int x, int y, int birthThreshold, int survivalThreshold) {
        int cellValue = map[y][x].getbCode();
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

    public void drawDungeon2D(Graphics g) {
        int r = Game2D.getDunSizeRatio();
        for (int y = 0; y < HGT; y += 1) {
            for (int x = 0; x < WID; x += 1) {
//                cellCol = (map[y][x].getbCode() == DEAD) ? BLACK : map[y][x].getCol();
                g.setColor(map[y][x].getCol());
                g.fillRect(x * DSIZE / r, y * DSIZE / r, DSIZE / r, DSIZE / r);
            }
        }
    }

    public MapNode[][] getMap() {
        return map;
    }

    public int getDSIZE() {
        return DSIZE;
    }
}
