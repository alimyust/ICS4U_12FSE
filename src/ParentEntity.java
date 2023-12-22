public class ParentEntity {
    protected static int x;
    protected static int y;
    protected int w;
    protected int h;
    public ParentEntity(int x, int y, int w, int h){
        ParentEntity.x = x;
        ParentEntity.y = y;
        this.w = w;
        this.h = h;
    }
}
