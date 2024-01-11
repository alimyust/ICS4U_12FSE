import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
/*
*  A Dungeon object would include everything that would be contained per level. This includes the actual dungeon and
* what it's made of, but also the enemies, power ups, etc.
* */
public class Dungeon {
    private final int DSIZE = 64;
    private final int dSizeMultiplier = 4;
    private final int WID = MainGame.WID / DSIZE * dSizeMultiplier;
    private final int HGT = MainGame.HGT / DSIZE* dSizeMultiplier;
    private final int ALIVE = 0; // empty space is alive
    private final int DEAD = 1; // dead is wall
    private MapNode[][] map = new MapNode[HGT][WID];
    private static BaseEnemy[] eArr = new BaseEnemy[8];
    private final ArrayList<Point> openSpaces = new ArrayList<>();
    private final Color[][][] blueBatImgArr = {
            MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat0.png"),
            MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat1.png"),
            MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat2.png"),
            MainGame.convertImageTo2DColorArray(MainGame.imgDir + "Enemies/BlueBat/blueBat3.png")
    };
    public Dungeon(Player player) {

        int c;
        do {
            generateGrid(0.50);
            automata(20, 5, 4);
            c = floodFill(HGT / 2/dSizeMultiplier, WID / 2/dSizeMultiplier, -1);
        } while (!((double) c / (double) (HGT * WID) > 0.3)); // 30 percent of map must be explorable
        // Fill everything else
        map = makeBorder(map);
        eArr = Bat.addEnemy(eArr, getOpenSpaces(), blueBatImgArr);
        for (int i = 0; i < HGT; i++)
            for (int j = 0; j < WID; j++)
                map[i][j] = new MapNode((map[i][j].getwCode() == -1) ? ALIVE : 2, 2, 2);
    }

    private int floodFill(int cx, int cy, int mark) {
        if (spotIsOffGrid(cx, cy) || map[cy][cx].getwCode() != ALIVE)
            return 0;
        map[cy][cx] = new MapNode(mark, 2, 2);
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
                map[i][j] = new MapNode(row[j], 2, 2);
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
                newGrid[y][x] = new MapNode(newValueAtPosition(x, y, birthThreshold, survivalThreshold), 2, 2);
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
            map[0][i] = new MapNode(DEAD,DEAD,DEAD);
            map[map.length-1][i] = new MapNode(DEAD,DEAD,DEAD);
        }
        for(int i=0; i < map.length; i++){
            map[i][0] = new MapNode(DEAD,DEAD,DEAD);
            map[i][map.length-1] = new MapNode(DEAD,DEAD,DEAD);
        }
        return map;
    }

    public void drawDungeon2D(Graphics g) {
        int r = Game2D.getDunSizeRatio();
        for (int y = 0; y < HGT; y += 1) {
            for (int x = 0; x < WID; x += 1) {
                g.setColor((map[y][x].getwCode() == 0) ? Color.WHITE :Color.BLACK);
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

    public int getdSizeMultiplier() {
        return dSizeMultiplier;
    }

    public ArrayList<Point> getOpenSpaces() {
        return openSpaces;
    }

    public BaseEnemy[] geteArr() {
        return eArr;
    }
}
