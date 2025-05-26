import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game {
    private Stage stage;
    private String playerName;
    private String level;

    private Pane root;
    private Scene scene;
    private PlayerCar playerCar;
    private List<EnemyCar> enemies;
    private AnimationTimer gameLoop;

    private int score = 0;
    private Text scoreText;
    private Text gameOverText;

    private double enemySpawnRate; // milliseconds
    private long lastSpawnTime = 0;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private final int WIDTH = 400;
    private final int HEIGHT = 600;

    private Random random = new Random();

    public Game(Stage stage, String playerName, String level) {
        this.stage = stage;
        this.playerName = playerName;
        this.level = level;

        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        scene = new Scene(root);

        enemies = new ArrayList<>();

        scoreText = new Text(10, 20, "Score: 0");
        scoreText.setFont(Font.font(18));
        scoreText.setFill(Color.BLACK);

        gameOverText = new Text(WIDTH / 2 - 70, HEIGHT / 2, "");
        gameOverText.setFont(Font.font(30));
        gameOverText.setFill(Color.RED);

        root.getChildren().addAll(scoreText, gameOverText);

        // تحديد سرعة spawn بناءً على المستوى
        switch (level.toLowerCase()) {
    case "easy":
        enemySpawnRate = 1500;
        break;
    case "medium":
        enemySpawnRate = 900;
        break;
    case "hard":
        enemySpawnRate = 500;
        break;
    default:
        enemySpawnRate = 1500;
        break;
}


        playerCar = new PlayerCar(WIDTH / 2 - 20, HEIGHT - 100);
        root.getChildren().add(playerCar.getView());

        setupControls();
    }

    

    public void startGame() {
        stage.setScene(scene);
        stage.setTitle("Car Dodging - Player: " + playerName + " - Level: " + level);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void setupControls() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                leftPressed = true;
            } else if (e.getCode() == KeyCode.RIGHT) {
                rightPressed = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                leftPressed = false;
            } else if (e.getCode() == KeyCode.RIGHT) {
                rightPressed = false;
            }
        });
    }

    private void update() {
        if (gameOverText.getText().length() > 0) {
           

            return; // لعبة انتهت
        }

        // تحريك السيارة
        if (leftPressed) playerCar.moveLeft();
        if (rightPressed) playerCar.moveRight(WIDTH);

        long currentTime = System.currentTimeMillis();

        // توليد سيارات أعداء جديدة
        if (currentTime - lastSpawnTime > enemySpawnRate) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }

        // تحريك السيارات الأعداء وتحديث النتيجة
        Iterator<EnemyCar> it = enemies.iterator();
        while (it.hasNext()) {
            EnemyCar enemy = it.next();
            enemy.moveDown();

            // لو السيارة خرجت من الشاشة نحذفها ونزيد النتيجة
            if (enemy.getY() > HEIGHT) {
                root.getChildren().remove(enemy.getView());
                it.remove();
                score++;
                scoreText.setText("Score: " + score);
            }

            // تحقق من الاصطدام
            if (enemy.getBounds().intersects(playerCar.getBounds())) {
                gameOver();
                DatabaseManager.insertScore(playerName, score, level);
                return;
            }
        }
    }

    private void spawnEnemy() {
        double x = random.nextInt(WIDTH - 40);
        EnemyCar enemy = new EnemyCar(x, -60, level);
        enemies.add(enemy);
        root.getChildren().add(enemy.getView());
    }

    private void gameOver() {
        gameOverText.setText("Game Over!\nFinal Score: " + score);
        gameLoop.stop();
    }
}
