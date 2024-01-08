
public class MapNode {
    private final int wCode;
    private final int fCode;
    private final int cCode;

    int[] texture;
    public MapNode(int wCode, int fCode, int cCode) {
        this.wCode = wCode;
        this.fCode = 1;
        this.cCode =0;
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
