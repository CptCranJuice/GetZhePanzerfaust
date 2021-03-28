import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Shells {

    private double x, y;
    private int w, h;
    private double theta;
    private double overallV;
    private Rectangle2D hitBox;

    public Shells(double x, double y, double angle){
        this.x = x;
        this.y = y;
        w = 10;
        h = 10;
        overallV = 15;
        this.theta = angle;

        hitBox = new Rectangle2D.Double(x, y, w, h);
    }

    public void draw(Graphics2D g2){
        g2.fillRect((int)x, (int)y, w, h);
    }

    public void move(){
        x += Math.cos(Math.toRadians(theta)) * overallV;
        y += Math.sin(Math.toRadians(theta)) * overallV;

        hitBox.setRect(x, y, w, h); //***this works because its a square***
    }

    public double getX(){
        return x;
    }

    public void setX(double a){
        x = a;
    }

    public double getY(){
        return y;
    }

    public void setY(double a){
        y = a;
    }

    public int getWidth(){
        return w;
    }


    public double getCentreX(){
        return (x + (0.5 * w));
    }

    public double getCentreY(){
        return (y + (0.5 * h));
    }

    public Polygon getHitBox(){
        //return hitBox; //.getBounds2D();

        Polygon hitPoly = new Polygon();

        Point[] corners = new Point[4];
        corners[0] = new Point((int)(this.x), (int)(this.y));
        corners[1] = new Point((int)(this.x)+(int)(this.w), (int)(this.y));
        corners[2] = new Point((int)(this.x)+(int)(this.w), (int)(this.y)+(int)(this.h));
        corners[3] = new Point((int)(this.x), (int)(this.y)+(int)(this.h));

        Point center = new Point((int)(x+w/2), (int)(y+h/2));
        for(int i = 0 ; i < corners.length; i++){
            Point rotP = Rotation.rotate(theta, center, corners[i]);
            corners[i] = rotP;
            hitPoly.addPoint(rotP.x, rotP.y);
        }
        return hitPoly;

    }

}
