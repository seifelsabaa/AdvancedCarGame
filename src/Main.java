import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Dodging Game");

        Label label = new Label("Enter Player Name:");
        TextField playerName = new TextField();
        playerName.setPromptText("Player Name");

        Label levelLabel = new Label("Select Level:");
        ComboBox<String> levelBox = new ComboBox<>();
        levelBox.getItems().addAll("Easy", "Medium", "Hard");
        levelBox.setValue("Easy");

        Button startBtn = new Button("Start Game");

        VBox vbox = new VBox(15, label, playerName, levelLabel, levelBox, startBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(400, 300);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        startBtn.setOnAction(e -> {
            String name = playerName.getText().trim();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter player name!");
                alert.showAndWait();
                return;
            }
            String level = levelBox.getValue();
            Game game = new Game(primaryStage, name, level);
            game.startGame();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
