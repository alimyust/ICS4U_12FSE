package ParentEntity;


import java.awt.*;
//general parent enntiy
public class ParentEntity {
    public int x;
    public int y;
    public int w;
    public int h;
    public ParentEntity(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw2d(Graphics g,Color color, int r){
        g.setColor(color);
        g.fillRect(x/r,y/r,w/r,h/r);
    }
}
