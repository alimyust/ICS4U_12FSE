import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapNode {
    private final int wCode;
    private final int fCode;
    private final int cCode;

    int[] texture;
    public MapNode(int wCode, int fCode, int cCode) {
        this.wCode = wCode;
        this.fCode = (Math.random() > 0.5)? 0:2;
        this.cCode = cCode;
    }

    public int getwCode() {
        return wCode;
    }

    public int getfCode() {
        return fCode;
    }

    public int getcCode() {
        return cCode;
    }

}
