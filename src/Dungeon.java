import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Dungeon {
    private final int DSIZE = 128;
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
        generateGrid(0.50);
        automata(20,5,4);
        System.out.println(Arrays.deepToString(map));
    }
    public int[] generateRow(int width, double aliveProbability) {
        int[] row = new int[width];
        Random random = new Random();

        for (int i = 0; i < width; i++) {
            row[i] = (random.nextDouble() < aliveProbability) ? 1 : 0;
        }

        return row;
    }

    public void generateGrid( double aliveProbability) {
        // Use the class-level array directly
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
                    if (spotIsOffGrid(i, j)) {
                        values[index++] = 1; // Assuming out-of-bounds cells are alive
                    } else {
                        values[index++] = map[j][i];
                    }
                }
            }
        }

        return Arrays.copyOf(values, index);
    }

    public int newValueAtPosition(int x, int y, int birthThreshold, int survivalThreshold) {
        int cellValue = map[y][x];
        int[] neighborValues = neighborValues(x, y);
        long aliveNeighbors = Arrays.stream(neighborValues).filter(value -> value == 1).count();

        if (cellValue == 1 && aliveNeighbors >= survivalThreshold) {
            return 1;
        } else if (cellValue == 0 && aliveNeighbors >= birthThreshold) {
            return 1;
        } else {
            return 0;
        }
    }

    public int[][] automataIteration(int birthThreshold, int survivalThreshold) {
        int height = map.length;
        int width = (height > 0) ? map[0].length : 0;

        int[][] newGrid = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newGrid[y][x] = newValueAtPosition(x, y, birthThreshold, survivalThreshold);
            }
        }

        map = newGrid;
        return map;
    }

    public int[][] automata(int iterations, int birthThreshold, int survivalThreshold) {
        for (int i = 0; i < iterations; i++)
            automataIteration(birthThreshold, survivalThreshold);

        return map;
    }

    public void generateDungeon(int birth, int survival, int recurs) {
        for (int i = 0; i < recurs; i++)
            calcNextGeneration(birth, survival);
    }

    public void genFullRandom(double aliveChance) {
        for (int y = 0; y < HGT; y++)
            for (int x = 0; x < WID; x++)
                map[y][x] = (Math.random() < aliveChance) ? ALIVE : DEAD;
    }

    private void calcNextGeneration(int birth, int survival) {
        int[][] tmpMap = map.clone();
        for (int y = 0; y < HGT; y++) {
            for (int x = 0; x < WID; x++) {
                int aliveNbr = calcNeighbourAlive(y, x);
                if (map[y][x] == ALIVE)  // Cell is alive (wall)
                    tmpMap[y][x] = (aliveNbr < survival) ? ALIVE : DEAD; // Overpopulation or underpopulation, cell dies
                else if (map[y][x] == DEAD) // Cell is dead
                    tmpMap[y][x] = (aliveNbr > birth) ? ALIVE : DEAD;
                // Birth, cell becomes alive, otherwise cell dies
            }
        }
        map = tmpMap;
    }

    private void postProcess(int removeLoneThreshold) {
        int[][] tmpMap = map.clone();
        for (int y = 0; y < HGT; y++) {
            for (int x = 0; x < WID; x++) {
                int aliveNbr = calcNeighbourAlive(y, x);
                if (map[y][x] == ALIVE && aliveNbr > removeLoneThreshold)
                    tmpMap[y][x] = DEAD;
            }
        }
        map = tmpMap;
    }

    private int calcNeighbourAlive(int y, int x) {
        int aliveNbr = 0;
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i == y && j == x)
                    continue; // skip current cell
                if (offGrid(i, j)) {
                    aliveNbr += 1;
                    continue;
                }
                if (map[i][j] == ALIVE)
                    aliveNbr += 1;
            }
        }
        return aliveNbr;
    }


    private boolean offGrid(int y, int x) {
        return x < 0 || x >= WID || y < 0 || y >= HGT;
    }

    public void drawDungeon2D(Graphics g) {
        for (int y = 0; y < HGT; y += 1) {
            for (int x = 0; x < WID; x += 1) {
                cellCol = (map[y][x] == DEAD) ? BLACK : WHITE;
                g.setColor(cellCol);
                g.fillRect(x * DSIZE/2, y * DSIZE/2, DSIZE/2, DSIZE/2);
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
