package MenuItems;

import MainGame.Game3D;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button {
    private final int x, y, width, height;
    private final String text;
    private final Rectangle bounds;
    private final String newGameState;
    private boolean wasButtonPressed = true; // Track the previous mouse button state

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
        boolean isMouseOver = isMouseOver(mx, my);
        // Check if the mouse button is pressed in the current frame and was not pressed in the previous frame
        boolean isButtonClicked = isMouseOver && mb == MouseEvent.BUTTON1 && !wasButtonPressed;
        wasButtonPressed = mb == MouseEvent.BUTTON1;
        return isButtonClicked;
    }
    public void changeGameState(int mx, int my, int mb){
    }

    public String getNewGameState() {
        return newGameState;
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