package MenuItems;

import MainGame.Game3D;
import MainGame.MainGame;

import java.awt.*;
import java.nio.charset.MalformedInputException;

public class GameOverButton extends Button {
    public GameOverButton(int x, int y, int width, int height, String text, String newGameState) {
        super(x, y, width, height, text, newGameState);
    }

    @Override
    public void draw(Graphics g, int mx, int my, int mb) {
        super.draw(g, mx, my, mb);
        if(isMouseOver(mx,my))
            g.drawRect(getX(), getY(),getWidth(),getHeight());
        g.setFont(new Font("Arial", Font.BOLD,40));
        g.drawString(getText(), getX()+20, getY() + 50);
    }

    @Override
    public void changeGameState(int mx, int my, int mb) {
        super.changeGameState(mx, my, mb);
        if(isClicked(mx,my,mb)) // resets game
            MainGame.g3D.game3Dinit();
    }
}