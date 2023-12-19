import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Game3D extends BaseFrame{
    private static int WID = MainGame.WID;
    private static int HGT = MainGame.HGT/2;
    private Dungeon dun;
    private Player p2d;
    private RayCaster rayCast;
    public Game3D(Dungeon dun, Player p2d) {
        super("Game3D", 64*18, 64*8);
        this.dun = dun;
        this.p2d = p2d;
        this.rayCast = new RayCaster(p2d,dun);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.draw(g);
        if(rayCast != null)
            rayCast.drawRays3d(g2d);
    }

    @Override
    public void move() {
        super.move();
        p2d.movePlayer(keys);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
//        dun = new Dungeon();
    }

    public static int getWID() {
        return WID;
    }
    public static int getHGT() {
        return HGT;
    }
}
