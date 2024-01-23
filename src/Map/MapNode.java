package Map;
import MainGame.MainGame;
import java.awt.*;
import static MainGame.MainGame.getImgDir;

public class MapNode {

    private static final Color[][][] allTextures = {
            // Bricks
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/BIGBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/CASTLEBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/CLAYBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/DUNGEONBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/DUNGEONCELL.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/GOOBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/PORCELAINBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/REDBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/ROUNDBRICKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Bricks/SLIMBRICKS.png")
            },
            // Doors
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Doors/CREAKYDOOR.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Doors/OFFICEDOOR.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Doors/SPOOKYDOOR.png")
            },
            // Elements
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/BIGLEAVES.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/LAVA.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/RAPIDS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/SAND.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/SANDMARKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/SNOW.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/TALLGRASS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/TINYLEAVES.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Elements/WATER.png")
            },
            // Industrial
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Industrial/CROSSCUBE.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Industrial/CROSSWALL.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Industrial/METALTILE.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Industrial/PIPES.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Industrial/STORAGE.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Industrial/SUPPORT.png")
            },
            // Rocks
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/DIRT.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/FLATSTONES.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/GOLDROCKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/GRAYROCKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/ICEYROCKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/LAVAROCKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/PATHROCKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Rocks/SLIMROCKS.png")
            },
            // Tech
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/BIGSQUARES.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/DENTWALL.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/DIRTHEXAGONS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/DOUBLELIGHTS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/HEXAGONS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/HIGHTECH.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/HIGHTECHWALL.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/LONGLIGHTS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/STARWALLA.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/STARWALLB.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/TECHWALLA.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/TECHWALLB.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/TINYLIGHTS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Tech/TINYSQUARES.png")
            },
            // Urban
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Urban/BIGWINDOW.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Urban/CHESS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Urban/GARAGE.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Urban/GRAYWALL.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Urban/PAVEMENT.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Urban/WINDOW.png")
            },
            // Wood
            {
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/BIGTRUNK.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/CREAKYWOOD.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/DARKWOOD.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/TRUNKS.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/WOODA.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/WOODB.png"),
                    MainGame.convertImageToColorArray(getImgDir() + "Textures/Wood/WOODTILE.png")
            }
    };

    private int wCode;
    private Color[] wallTexture;
    private Color[] floorTexture;
    private Color[] ceilTexture;
    public MapNode(int wCode) {
        this.wCode = wCode;
    }
    public MapNode(Point wCode, Point fCode, Point cCode) {
        this.wallTexture =allTextures[wCode.x][wCode.y];
        this.floorTexture =allTextures[fCode.x][fCode.y];
        this.ceilTexture =allTextures[cCode.x][cCode.y];
    }

    public Color[] getWallTexture() {
        return wallTexture;
    }

    public Color[] getFloorTexture() {
        return floorTexture;
    }

    public Color[] getCeilTexture() {
        return ceilTexture;
    }

    public int getwCode() {
        return wCode;
    }

    public void setwCode(int wCode) {
        this.wCode = wCode;
    }


}
