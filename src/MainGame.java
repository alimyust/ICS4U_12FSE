public class MainGame {
    private static final Dungeon dun = new Dungeon();
    private static final Player player = new Player(30,40);
    protected static final int WID = 1100;
    protected static final int HGT= 700;

    public static void main(String[] args) {
        new Game3D(dun,player);
        new Game2D(dun,player);
    }
}
