import java.awt.*;

public class Rotation {

    public static Point rotate(double angle, Point center, Point coords) {
        angle *= -1;
        angle = Math.toRadians(angle);
        double x = (coords.x - center.x) * Math.cos(angle) - (coords.y - center.y) * Math.sin(angle);
        double y = (coords.x - center.x) * Math.sin(angle) + (coords.y - center.y) * Math.cos(angle);
        return new Point((int) (x + center.x * 1), (int) (y + center.y * 1));
    }


}
