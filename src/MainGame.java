public class MainGame {
    private static final Dungeon dun = new Dungeon();
    protected static final int WID = 64*16;
    protected static final int HGT= 64*16;
    private static final Player player = new Player(WID/4,HGT/4);

    public static void main(String[] args) {
        new Game3D(dun,player);
        new Game2D(dun,player);
    }
}
