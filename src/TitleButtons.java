import java.awt.*;

public class TitleButtons extends Button{

    public TitleButtons(int x, int y, int width, int height, String text, String newGameState) {
        super(x, y, width, height, text, newGameState);

    }

    @Override
    public void draw(Graphics g, int mx, int my) {
        super.draw(g, mx, my);
        if(!isMouseOver(mx,my))
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        else
            g.drawRect(getX(), getY(), getWidth()/2, getHeight());

    }
}
