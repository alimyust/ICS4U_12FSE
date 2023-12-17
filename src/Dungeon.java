    import java.awt.*;

    public class Dungeon {
        private final int DSIZE = 32;
        //dungeon size. Smaller values make larger dungeons
        private final int WID = MainGame.WID/DSIZE;
        //same divisor keeps it proportional to total size of display
        private final int HGT = MainGame.HGT/DSIZE;
        private final Color WHITE = new Color(255, 255, 255);
        private final Color BLACK = new Color(0, 0, 0);
        Color cellCol;
        private int[][] map = new int[HGT][WID];
        public Dungeon(){
            System.out.println(HGT + " , " + WID);
            System.out.println("gen");
            genFullRandom(0.55);
            generateDungeon(6,7,20);
            postProcess( 7);
//            generateDungeon(4,7,4);

        }

        public void drawDungeon2D(Graphics g){
            for(int y = 0; y < HGT; y+=1) {
                for (int x = 0; x < WID; x += 1) {
                    cellCol = (map[y][x] == 1)? WHITE: BLACK;
                    g.setColor(cellCol);
                    g.fillRect(x*DSIZE,y*DSIZE,DSIZE,DSIZE);
                }
            }
        }
        public void genFullRandom(double aliveChance){
            for(int y=0; y < HGT; y++)
                for(int x=0; x < WID; x++)
                    map[y][x] = (Math.random() < aliveChance) ? 1 : 0;
        }
        public void generateDungeon(int birth, int survival, int recurs){
            for(int i=0; i < recurs; i++)
                calcNextGeneration(birth, survival);
        }
        private void calcNextGeneration(int birth, int survival){
            int[][] tmpMap = new int[HGT][WID];
            for(int y=0; y < HGT; y++) {
                for (int x = 0; x < WID; x++) {
                    int aliveNbr = calcNeighbourAlive(y,x);
                    if (map[y][x] == 1)  // Cell is alive
                        tmpMap[y][x] = (aliveNbr > survival)?0:1; // Overpopulation or underpopulation, cell dies
                     else  // Cell is dead
                        tmpMap[y][x] = (aliveNbr == birth)? 1:0;
                            // Birth, cell becomes alive, otherwise cell dies
                }
            }
            map = tmpMap;
        }

        private void postProcess(int removeLoneThreshold){
            int[][] tmpMap = map.clone();
            for(int y=0; y < HGT; y++) {
                for (int x = 0; x < WID; x++) {
                    int aliveNbr = calcNeighbourAlive(y,x);
                    if(map[y][x] == 0 && aliveNbr >removeLoneThreshold)
                        tmpMap[y][x] = 1;
                }
            }
            map = tmpMap;
        }
        private int calcNeighbourAlive(int y, int x){
            int alive = 0;
            for(int i=y-1; i <= y+1; i++){
                for(int j=x-1; j <= x+1; j++){
                    if (i == y && j == x)
                        continue; // skip current cell
                    if ((offGrid(i, j))) {
                        alive += 1;
                        continue;
                    }
                    if (map[i][j] == 1) alive += 1;
                }
            }
            return alive;
        }

        private boolean offGrid(int y, int x){
            return x < 0 || x >= WID   || y < 0 || y >= HGT;
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
