import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Tank {

    private double x, y;
    private int width, height;
    private boolean moveForward, moveBack, traverseLeft, traverseRight;
    private int angle, overallV;
//    private Point[] corners = new Point[4];
    private Rectangle2D.Double hitBox;
    private Image sprite;

    public Tank(int x, int y, int angle, Image sprite) {
        this.x = x;
        this.y = y;
        width = 50;
        height = 25;
        moveForward = false;
        moveBack = false;
        this.angle = angle; //0 for right 180 for left, 270 for up
        overallV = 1;
        hitBox = new Rectangle2D.Double(x, y, width, height);
        this.sprite = sprite;
    }

    public void draw(Graphics2D g2){

        g2.translate(getCentreX(), getCentreY());
        g2.rotate(+Math.toRadians(angle));

//        g2.fillRect(-width/2, -height/2, width, height);
        g2.drawImage(sprite, -width/2, -height/2, null);
//        g2.drawImage(sprite, (int)x, (int)y, width, height, null);

        g2.rotate(-(Math.toRadians(angle)));
        g2.translate(-(getCentreX()), -(getCentreY()));

    }



    public void move() {

        if (moveForward) {
            x += Math.cos(Math.toRadians(angle)) * overallV;
            y += Math.sin(Math.toRadians(angle)) * overallV;
        }
        if (moveBack){
            x -= Math.cos(Math.toRadians(angle)) * overallV; //"+=" and "angle+180" also works
            y -= Math.sin(Math.toRadians(angle)) * overallV;
        }
        if (traverseLeft){
            angle -= 3;
        }
        if (traverseRight){
            angle += 3;
        }
    }

    public void pressedP1(int keyCode){
        if(keyCode == KeyEvent.VK_W){
            moveForward = true;
        }
        else if(keyCode == KeyEvent.VK_S){
            moveBack = true;
        }
        else if(keyCode == KeyEvent.VK_A){
            traverseLeft = true;
        }
        else if(keyCode == KeyEvent.VK_D){
            traverseRight = true;
        }
    }

    public void releasedP1(int keyCode){
        if(keyCode == KeyEvent.VK_W){
            moveForward = false;
        }
        else if(keyCode == KeyEvent.VK_S){
            moveBack = false;
        }
        else if(keyCode == KeyEvent.VK_A){
            traverseLeft = false;
        }
        else if(keyCode == KeyEvent.VK_D){
            traverseRight = false;
        }
    }

    public void pressedP2(int keyCode){
        if(keyCode == KeyEvent.VK_UP){
            moveForward = true;
        }
        else if(keyCode == KeyEvent.VK_DOWN){
            moveBack = true;
        }
        else if(keyCode == KeyEvent.VK_LEFT){
            traverseLeft = true;
        }
        else if(keyCode == KeyEvent.VK_RIGHT){
            traverseRight = true;
        }
    }

    public void releasedP2(int keyCode){
        if(keyCode == KeyEvent.VK_UP){
            moveForward = false;
        }
        else if(keyCode == KeyEvent.VK_DOWN){
            moveBack = false;
        }
        else if(keyCode == KeyEvent.VK_LEFT){
            traverseLeft = false;
        }
        else if(keyCode == KeyEvent.VK_RIGHT){
            traverseRight = false;
        }
    }


    public double getAngle(){
        return angle;
    }

    public double getCentreX(){
        return (x + (0.5 * width));
    }

    public double getCentreY(){
        return (y + (0.5 * height));
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }



    public double getX(){
        return x;
    }

    public void setX(double a){
        x = a;
    }

    public double getY(){ return y; }

    public void setY(double a){ y = a; }


    public double getVx(){
        return Math.cos(Math.toRadians(angle)) * overallV;
    }

    public double getVy(){
        return Math.sin(Math.toRadians(angle)) * overallV;
    }

    public void setMoveForward(){
        moveForward = false;
    }

    public void setMoveBack(){
        moveBack = false;
    }


    public Polygon getHitBox(){
        //return hitBox; //.getBounds2D();

        Polygon hitPoly = new Polygon();

        Point[] corners = new Point[4];
        corners[0] = new Point((int)(this.x), (int)(this.y));
        corners[1] = new Point((int)(this.x)+(int)(this.width), (int)(this.y));
        corners[2] = new Point((int)(this.x)+(int)(this.width), (int)(this.y)+(int)(this.height));
        corners[3] = new Point((int)(this.x), (int)(this.y)+(int)(this.height));

        Point center = new Point((int)(x+width/2), (int)(y+height/2));
        for(int i = 0 ; i < corners.length; i++){
            Point rotP = Rotation.rotate(angle, center, corners[i]);
            corners[i] = rotP;
            hitPoly.addPoint(rotP.x, rotP.y);
        }
        return hitPoly;

    }

}
