package MenuItems;

import java.awt.*;

public class GameOverButton extends Button {
    public GameOverButton(int x, int y, int width, int height, String text, String newGameState) {
        super(x, y, width, height, text, newGameState);
    }

    @Override
    public void draw(Graphics g, int mx, int my, int mb) {
        super.draw(g, mx, my, mb);

    }
}