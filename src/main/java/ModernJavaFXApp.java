import com.formdev.flatlaf.FlatLightLaf;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModernJavaFXApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Set FlatLaf as the look and feel
        FlatLightLaf.setup();

        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #F5F5F5; -fx-padding: 20px;");

        Button button = new Button("Modern Styled Button");
        button.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14px;");

        root.getChildren().add(button);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Modern JavaFX UI with FlatLaf");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
