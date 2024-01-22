package Map;

import javax.swing.*;
import java.awt.*;

public class DungeonDrawer extends JPanel {

    private Dungeon dungeon;

    public DungeonDrawer(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        dungeon.drawDungeon2D(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dungeon dungeon = new Dungeon(new Point(1, 1), new Point(2, 2), new Point(3, 3), "");
            DungeonDrawer drawer = new DungeonDrawer(dungeon);

            JFrame frame = new JFrame("Map.Dungeon Drawer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(dungeon.getWID() * dungeon.getDSIZE() / dungeon.getdSizeMultiplier(),
                    dungeon.getHGT() * dungeon.getDSIZE() / dungeon.getdSizeMultiplier());
            frame.setContentPane(drawer);
            frame.setVisible(true);
        });
    }
}
