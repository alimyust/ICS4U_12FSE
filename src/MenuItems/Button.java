package MenuItems;

import MainGame.Game3D;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button {
    private final int x, y, width, height;
    private final String text;
    private final Rectangle bounds;
    private final String newGameState;
    public Button(int x, int y, int width, int height, String text, String newGameState) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.newGameState = newGameState;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g, int mx, int my, int mb) {
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return bounds.contains(mouseX, mouseY);
    }

    public boolean isClicked(int mx, int my, int mb) {
        return isMouseOver(mx, my) && mb == MouseEvent.BUTTON1;
    }
    public void changeGameState(int mx, int my, int mb){
        if(isClicked(mx,my,mb))
            Game3D.setGameState(newGameState);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
    public String getText(){return text;}

    public int getHeight() {
        return height;
    }
}