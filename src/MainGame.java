public class MainGame {
    private static final Dungeon dun = new Dungeon();
    private static final Player player = new Player(30,40);
    protected static final int WID = 64*12;
    protected static final int HGT= 64*12;

    public static void main(String[] args) {
        new Game3D(dun,player);
        new Game2D(dun,player);
    }
}
