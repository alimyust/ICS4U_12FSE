public class ParentMovingEntity extends ParentEntity{
    private double vx, vy;
    public ParentMovingEntity(int x, int y,int w, int h,double vx, double vy) {
        super(x, y, w, h);
        this.vx = vx;
        this.vy = vy;
    }
    public void moveEntity(){
        x+= (int) this.vx;
        y+= (int) this.vy;
    }
}
