import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;

public class Game3D extends BaseFrame {
    private static final int WID = 64 * 15;
    private static final int HGT = 64 * 12;
    private RayCaster rayCast;
    private int lvl;
    private static String gameState = "";
    private final ArrayList<Button> titleButtons = new ArrayList<>();

    public Game3D() {
        super("Game3D", WID, HGT);
        this.setLocationRelativeTo(null);
        gameState = "title";
        this.lvl = 0;
        refreshDungeon();
        titleInit();
        this.rayCast = new RayCaster(MainGame.player, MainGame.dun);

    }
    private void titleInit(){
        titleButtons.add(new TitleButtons(30,60,400,20,"Start Game", "game"));
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.draw(g);
        if(Objects.equals(gameState, "title")){
            for(Button but: titleButtons){
                but.draw(g,mx,my);
                but.changeGameState(mx,my,mb);
            }
        }
        if(Objects.equals(gameState, "game")){
            rayCast.drawRays3d(g2d);
            MainGame.dun.geteArr().forEach(e -> e.drawBaseEnemy(g, MainGame.player, HGT, WID, rayCast));
            MainGame.player.shootAnimation(g);
            MainGame.player.drawPlayerGUI(g);
        }
    }

    @Override
    public void move() {
        super.move();
        MainGame.player.movePlayer(keys, MainGame.dun);
        MainGame.player.shootEnemies(keys,MainGame.dun);
        MainGame.player.chooseGun(keys);
        MainGame.dun.geteArr().forEach(e -> e.moveEnemy(MainGame.player,MainGame.dun));
        if(pointDist(MainGame.dun.getDoorPoint(), new Point(MainGame.player.x/64,MainGame.player.y/64)) < 2)
            refreshDungeon();
    }

    public void refreshDungeon() {
        System.out.println("refresh Dungeon");
        switch (lvl) {
            case 0:
                System.out.println("new");
                MainGame.dun = new Dungeon(new Point(7, 0), new Point(7, 6), new Point(7, 6), "");
                for (int i = 0; i < 5; i++) {
                    Point randPoint = MainGame.dun.getRandomOpenPoint();
                    MainGame.dun.applyBlotch(randPoint.x,randPoint.y,(int) (Math.random() * 5 + 2),
                            new Point(3, 3), new Point(7, 6), new Point(7, 6));
                }
                break;
            case 1:
                MainGame.dun = new Dungeon(new Point(3, 1), new Point(5, 2), new Point(5, 2), "");
                break;
            case 2:
                MainGame.dun = new Dungeon(new Point(4, 5), new Point(5, 4), new Point(5, 4), "");
                break;
            default:
                lvl = -1;
        }
        MainGame.player = new Player(512, 512);
        rayCast = new RayCaster(MainGame.player,MainGame.dun);
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

    public static boolean notIntersectingMap(int ax, int ay, MapNode[][] map){
        int mapX = ax/64;
        int mapY = ay/64;
        if (mapX >= map[0].length || mapY >= map.length) return false;
        return map[mapY][mapX].getwCode() == 0;
    }

    public String getGameState() {
        return gameState;
    }

    public static void setGameState(String gameState) {
        Game3D.gameState = gameState;
    }
}
