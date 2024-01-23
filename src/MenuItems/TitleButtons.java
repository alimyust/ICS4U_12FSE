package MenuItems;

import MainGame.Game3D;

import java.awt.*;

public class TitleButtons extends Button {
    private int counter;

    public TitleButtons(int x, int y, int width, int height, String text, String newGameState) {
        super(x, y, width, height, text, newGameState);
        this.counter = width;
    }

    @Override
    public void draw(Graphics g, int mx, int my, int mb) {
        super.draw(g, mx, my, mb);
        int menuShrinkRate = 20;
        counter += !isMouseOver(mx, my) ? menuShrinkRate : -menuShrinkRate;
        if (counter < getWidth() / 2) counter = getWidth() / 2;
        if (counter > getWidth()) counter = getWidth();
        for (int i = 0; i < counter; i++) {
            int red = 100 + counter - i;
            int green = 46 + (counter - i) / 5;
            int blue = 1;

            red = Math.min(255, red);
            green = Math.min(255, green);

            g.setColor(new Color(red, green, blue));
            g.fillRect(getX() + i, getY(), 1, getHeight());
        }
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY(), counter, getHeight());

        g.setFont(new Font("Arial", Font.ITALIC, 20));
        g.drawString(getText(), getX() + 10, getY() + 18);

    }

    @Override
    public void changeGameState(int mx, int my, int mb) {
        super.changeGameState(mx, my, mb);
        if(isClicked(mx,my,mb))
            Game3D.setGameState(getNewGameState());
    }
}
