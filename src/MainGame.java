public class MainGame {
    private static final Dungeon dun = new Dungeon();
    protected static final int WID = 64*8;
    protected static final int HGT= 64*8;
    private static final Player player = new Player(WID/2,HGT/2);

    public static void main(String[] args) {
        Game3D g3d = new Game3D(dun,player);
        Game2D g2d = new Game2D(dun,player);
    }
}
