package ParentEntity;

import MainGame.Game2D;

import java.awt.*;

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

    public ParentEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw2d(Graphics g,Color color){
        int r = Game2D.getDunSizeRatio();
        g.setColor(color);
        g.fillRect(x/r,y/r,w/r,h/r);
    }
}
