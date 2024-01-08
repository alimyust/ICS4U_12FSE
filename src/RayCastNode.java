import java.awt.*;

public class RayCastNode {
    private final int x;
    private final int y;
    private final int dist;
    private final Color color;

    public RayCastNode(int x, int y,int dist, Color color) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.color = color;
    }
    public void drawRCNode(Graphics g){
        g.setColor(color);
        g.drawLine(x,y,x,y);
    }
}
