package MenuItems;

import MainGame.Game3D;

import java.awt.*;

public class InvisButton extends Button{
    public InvisButton(int x, int y, int width, int height, String text, String newGameState) {
        super(x, y, width, height, text, newGameState);
    }

    @Override
    public void draw(Graphics g, int mx, int my, int mb) {
        super.draw(g, mx, my, mb);
        g.setColor(Color.BLUE);
        g.fillRect(getX(),getY(),getWidth(),getHeight());
    }

    public void changeGameState(int mx, int my, int mb) {
        super.changeGameState(mx, my, mb);
        if(isClicked(mx,my,mb))
            Game3D.setGameState(getNewGameState());
    }
}
