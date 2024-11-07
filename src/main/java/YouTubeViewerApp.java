import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class YouTubeViewerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("YouTube Viewer");

        // Create a WebView and load a YouTube URL
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Replace with the URL of the YouTube video you want to watch
        String youtubeUrl = "https://www.youtube.com/embed/VIDEO_ID";  // Replace VIDEO_ID with actual YouTube video ID
        webEngine.load(youtubeUrl);

        BorderPane root = new BorderPane();
        root.setCenter(webView);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
