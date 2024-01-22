package MainGame;
import Enemies.BaseEnemy;
import MenuItems.*;
import Map.*;
import MenuItems.Button;
import Player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class Game3D extends BaseFrame {
    private static final int WID = 64 * 15;
    private static final int HGT = 64 * 12;
    private RayCaster rayCast;
    private static int lvl;
    private static String gameState = "";
    private final ArrayList<Button> titleButtons = new ArrayList<>();
    private int score;
    private int deathAnimationCounter;
    private boolean displayMap;

    public Game3D() {
        super("MainGame.Game3D", WID, HGT);
        this.setLocationRelativeTo(null);
        gameState = "title";
        displayMap = false;
        lvl = 0;
        score= 0;
        deathAnimationCounter= 0;
        refreshDungeon();
        this.rayCast = new RayCaster();
        titleInit();

    }
    private void titleInit(){
        int butPlace = 500;
        titleButtons.add(new TitleButtons(30,butPlace,400,20,"Start Game", "game"));
        titleButtons.add(new TitleButtons(30,butPlace+25,400,20,"Endless", "endless"));
        titleButtons.add(new TitleButtons(30,butPlace+50,400,20,"Controls", "controls"));
        titleButtons.add(new TitleButtons(30,butPlace+75,400,20,"Exit", "exit"));

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.draw(g);
        if(Objects.equals(gameState, "title")){
            for(Button but: titleButtons){
                but.draw(g,mx,my, mb);
                but.changeGameState(mx,my,mb);
            }
        }

        if(Objects.equals(gameState, "game") || Objects.equals(gameState, "endless") || Objects.equals(gameState, "gameover")){
            rayCast.drawRays3d(g2d);
            MainGame.dun.geteArr().forEach(e -> e.drawBaseEnemy(g, MainGame.player, HGT, WID, rayCast));
            MainGame.player.shootAnimation(g);
            MainGame.player.drawPlayerGUI(g);
            displayMap(g);
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC,25));
            g.setColor(Color.WHITE);
            g2d.drawString("Score: " + score,10,55);
        }

        if(Objects.equals(gameState, "gameover")){
            deathAnimationCounter += 10;
            deathAnimation(g);
            if(deathAnimationCounter >= WID) {
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, WID, HGT);
                g.setColor(new Color(117, 1, 1));
                g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC,50));
                g2d.drawString("Game Over", 400,300);
                g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC,25));
                if(deathAnimationCounter >= WID+500)
                    g2d.drawString("Score: " + score,430,360);
            }
        }

        if(Objects.equals(gameState, "exit")){
            System.exit(0);
        }
    }

    private void deathAnimation(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,deathAnimationCounter,HGT);
    }

    @Override
    public void move() {
        super.move();
        if(Objects.equals(gameState, "game") || Objects.equals(gameState, "endless")) {
            MainGame.player.movePlayer(keys, MainGame.dun);
            MainGame.player.chooseGun(keys);
            MainGame.player.shootEnemies(keys, MainGame.dun);
            MainGame.dun.geteArr().forEach(e -> e.moveEnemy(MainGame.player, MainGame.dun));
            MainGame.dun.geteArr().removeIf(this::removeEnemy);
            if (pointDist(MainGame.dun.getDoorPoint(), new Point(MainGame.player.x / 64, MainGame.player.y / 64)) < 2) {
                score+= 100;
                refreshDungeon();
            }
        }
    }

    private void displayMap(Graphics g) {
        if(displayMap){
            MainGame.dun.drawDungeon2D(g,200,200,8);
            MainGame.player.drawPlayer(g,200,200,8);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if(e.getKeyCode() == KeyEvent.VK_M &&( Objects.equals(gameState, "game") || Objects.equals(gameState, "endless")))
            displayMap = !displayMap;
    }

    public void refreshDungeon() {
        if (Objects.equals(gameState, "game")) {
            switch (lvl) {
                case 0 -> MainGame.dun = new Dungeon(new Point(7, 0), new Point(7, 6), new Point(7, 6), "");
                case 1 -> MainGame.dun = new Dungeon(new Point(5, 12), new Point(5, 10), new Point(5, 0), "");
                case 2 -> MainGame.dun = new Dungeon(new Point(5, 0), new Point(5, 12), new Point(5, 6), "");
                case 3 -> MainGame.dun = new Dungeon(new Point(5, 13), new Point(5, 12), new Point(5, 6), "");
                case 4 -> MainGame.dun = new Dungeon(new Point(4, 1), new Point(5, 2), new Point(4, 1), "");
                case 5 -> MainGame.dun = new Dungeon(new Point(5, 2), new Point(6, 1), new Point(2, 3), "");
                case 6 -> MainGame.dun = new Dungeon(new Point(7, 6), new Point(7, 3), new Point(7, 0), "");
                case 7 -> MainGame.dun = new Dungeon(new Point(5, 12), new Point(5, 5), new Point(5, 1), "");
                case 8 -> MainGame.dun = new Dungeon(new Point(2, 2), new Point(2, 4), new Point(3, 0), "");
                case 9 -> MainGame.dun = new Dungeon(new Point(3, 4), new Point(5, 2), new Point(5, 2), "");
                case 10 -> gameState = "win";
                default -> lvl = -1;
            }
        }

        int[][] textureInd = {
                {0,1,2,3,4,5,6,7,8,9},
                {0,1,2},
                {0,1,2,3,4,5,6,7,8},
                {0,1,2,3,4,5},
                {0,1,2,3,4,5,6,7},
                {0,1,2,3,4,5,6,7,8,9,10,11,12,13},
                {0,1,2,3,4,5},
                {0,1,2,3,4,5,6},
        };
        if(Objects.equals(gameState, "endless")){
            int textGroup = (int) (Math.random()*textureInd.length);
            Point wall = new Point(textGroup, (int) (Math.random()*textureInd[textGroup].length));
            Point floor= new Point(textGroup, (int) (Math.random()*textureInd[textGroup].length));
            Point ceil = new Point(textGroup, (int) (Math.random()*textureInd[textGroup].length));
            MainGame.dun = new Dungeon(wall,floor,ceil, "");
            System.out.println(wall + ", "+floor+ ", " +ceil);
        }
        MainGame.player = new Player(512, 512);
        rayCast = new RayCaster();
        lvl++;
    }

    private int pointDist(Point doorPoint, Point point) {
        return (int) Math.sqrt((point.x - doorPoint.x) * (point.x - doorPoint.x) +
                (point.y - doorPoint.y) * (point.y - doorPoint.y));

    }

    public static int getWid3d() {
        return WID;
    }

    public static int getHgt3d() {
        return HGT;
    }

    public static int getLvl() {
        return lvl;
    }

    public static boolean notIntersectingMap(int ax, int ay, MapNode[][] map){
        int mapX = ax/64;
        int mapY = ay/64;
        if (mapX >= map[0].length || mapY >= map.length) return false;
        return map[mapY][mapX].getwCode() == 0;
    }

    public String getGameState() {
        return gameState;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static void setGameState(String gameState) {
        Game3D.gameState = gameState;
    }

    private boolean removeEnemy(BaseEnemy e) {
        boolean alive = !e.isAlive();
        if (alive) score += 35;
        return alive;
    }
}
