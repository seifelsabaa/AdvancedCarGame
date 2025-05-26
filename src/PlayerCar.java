import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class PlayerCar {
    private Rectangle rect;
    private final int WIDTH = 40;
    private final int HEIGHT = 60;
    private final int MOVE_STEP = 10;

    public PlayerCar(double x, double y) {
        rect = new Rectangle(WIDTH, HEIGHT);
        rect.setFill(Color.BLUE);
        rect.setX(x);
        rect.setY(y);
    }

    public void moveLeft() {
        if (rect.getX() - MOVE_STEP >= 0) {
            rect.setX(rect.getX() - MOVE_STEP);
        }
    }

    public void moveRight(double sceneWidth) {
        if (rect.getX() + WIDTH + MOVE_STEP <= sceneWidth) {
            rect.setX(rect.getX() + MOVE_STEP);
        }
    }

    public Rectangle getView() {
        return rect;
    }

    public Bounds getBounds() {
        return rect.getBoundsInParent();
    }
}
