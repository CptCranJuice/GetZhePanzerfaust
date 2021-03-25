import java.awt.*;

public class Walls {

    private int x, y, w, h;

    public Walls(int x, int y, int w, int h) { //x, y is starting point, w or h can be 1 and the other is length of wall
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Graphics2D g2){
        g2.fillRect(x, y , w, h);
    }


    public Rectangle getHitBox(){
        return new Rectangle(x, y, w, h);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getW(){
        return w;
    }

    public int getH(){
        return h;
    }
}
