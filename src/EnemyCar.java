import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class EnemyCar {
    private Rectangle rect;
    private final int WIDTH = 40;
    private final int HEIGHT = 60;
    private double speed;

    public EnemyCar(double x, double y, String level) {
        rect = new Rectangle(WIDTH, HEIGHT);
        rect.setFill(Color.RED);
        rect.setX(x);
        rect.setY(y);

        switch (level.toLowerCase()) {
    case "easy":
        speed = 3;
        break;
    case "medium":
        speed = 5;
        break;
    case "hard":
        speed = 8;
        break;
    default:
        speed = 3;
        break;
}

    }

    public void moveDown() {
        rect.setY(rect.getY() + speed);
    }

    public double getY() {
        return rect.getY();
    }

    public Rectangle getView() {
        return rect;
    }

    public Bounds getBounds() {
        return rect.getBoundsInParent();
    }
}
