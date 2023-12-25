import java.awt.*;

public class MapNode {
    private final int bCode;
    private final Color col;
    private final Color[] rainbowColors = {
            new Color(255, 255, 255),     // WHITE
            new Color(0, 0, 0),     // BLACK
            new Color(255, 0, 0),     // Red
            new Color(255, 165, 0),   // Orange
            new Color(255, 255, 0),   // Yellow
            new Color(0, 255, 0),     // Green
            new Color(0, 0, 255),     // Blue
            new Color(75, 0, 130),    // Indigo
            new Color(128, 0, 128),   // Violet
            new Color(148, 0, 211)    // Purple
    };
    public MapNode(int bCode) {
        this.bCode = bCode;
        if(bCode >= rainbowColors.length) bCode = rainbowColors.length-1;
        this.col = rainbowColors[bCode];
    }

    public int getbCode() {
        return bCode;
    }

    public Color getCol() {
        return col;
    }
}
