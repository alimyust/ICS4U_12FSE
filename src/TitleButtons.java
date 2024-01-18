import java.awt.*;

public class TitleButtons extends Button{
    private int counter;
    public TitleButtons(int x, int y, int width, int height, String text, String newGameState) {
        super(x, y, width, height, text, newGameState);
        this.counter = width;
    }

    @Override
    public void draw(Graphics g, int mx, int my) {
        super.draw(g, mx, my);
        int menuShrinkRate =  10;
        counter += !isMouseOver(mx, my) ? menuShrinkRate : -menuShrinkRate;
        if(counter < getWidth()/2) counter = getWidth()/2;
        if(counter > getWidth()) counter = getWidth();
        for (int i = 0; i < counter/3; i+=1) {
            g.setColor(new Color(100 + i, 14 + i/2, 2));
            g.fillRect(getX(), getY(),  getX()+i, getHeight());
        }
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY(),  counter, getHeight());

        g.setFont(new Font("Arial", Font.ITALIC,20));
        g.drawString(getText(), getX()+10,getY()+18);
    }
}
