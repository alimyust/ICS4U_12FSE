package Map;
import MainGame.MainGame;
import java.awt.*;

public class MapNode {
    private static final Color[][][] allTextures = {
            // Bricks
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/BIGBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/CASTLEBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/CLAYBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/DUNGEONBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/DUNGEONCELL.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/GOOBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/PORCELAINBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/REDBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/ROUNDBRICKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Bricks/SLIMBRICKS.png")
            },
            // Doors
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Doors/CREAKYDOOR.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Doors/OFFICEDOOR.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Doors/SPOOKYDOOR.png")
            },
            // Elements
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/BIGLEAVES.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/LAVA.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/RAPIDS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/SAND.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/SANDMARKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/SNOW.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/TALLGRASS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/TINYLEAVES.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Elements/WATER.png")
            },
            // Industrial
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Industrial/CROSSCUBE.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Industrial/CROSSWALL.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Industrial/METALTILE.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Industrial/PIPES.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Industrial/STORAGE.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Industrial/SUPPORT.png")
            },
            // Rocks
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/DIRT.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/FLATSTONES.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/GOLDROCKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/GRAYROCKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/ICEYROCKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/LAVAROCKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/PATHROCKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Rocks/SLIMROCKS.png")
            },
            // Tech
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/BIGSQUARES.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/DENTWALL.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/DIRTHEXAGONS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/DOUBLELIGHTS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/HEXAGONS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/HIGHTECH.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/HIGHTECHWALL.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/LONGLIGHTS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/STARWALLA.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/STARWALLB.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/TECHWALLA.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/TECHWALLB.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/TINYLIGHTS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Tech/TINYSQUARES.png")
            },
            // Urban
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Urban/BIGWINDOW.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Urban/CHESS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Urban/GARAGE.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Urban/GRAYWALL.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Urban/PAVEMENT.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Urban/WINDOW.png")
            },
            // Wood
            {
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/BIGTRUNK.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/CREAKYWOOD.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/DARKWOOD.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/TRUNKS.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/WOODA.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/WOODB.png"),
                    MainGame.convertImageToColorArray(MainGame.imgDir + "Textures/Wood/WOODTILE.png")
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
