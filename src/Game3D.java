import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Game3D extends BaseFrame {
    private static final int wid3d = 64 * 18;
    private static final int hgt3d = 64 * 12;
    private final Dungeon dun;
    private final Player player;
    private final RayCaster rayCast;

    public Game3D(Dungeon dun, Player p2d) {
        super("Game3D", wid3d, hgt3d);
        this.setLocationRelativeTo(null);
        this.dun = dun;
        this.player = p2d;
        this.rayCast = new RayCaster(p2d, dun);
    }

    public static int getWid3d() {
        return wid3d;
    }

    public static int getHgt3d() {
        return hgt3d;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.draw(g);
        if (rayCast != null)
            rayCast.drawRays3d(g2d);
        repaint();  // Ensure that the frame is repainted after drawing the rays
    }

    @Override
    public void move() {
        super.move();
        player.movePlayer(keys);
        rayCast.updatePlayerPosition(player.x, player.y);
        rayCast.updatePlayerDirection(Math.cos(player.getAngle()), Math.sin(player.getAngle()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
//        dun = new Dungeon();
    }


}
