package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private AnchorPane FrameLogin;

    @FXML
    private TextField TextField_Username;

    @FXML
    private PasswordField text_pass_Login;

    @FXML
    private Button Login_Button;

    @FXML
    private Button create_account_Login;

    @FXML
    private CheckBox check_pass_Login;

    @FXML
    private Label error_login;

    private MainApp mainApp;

    private Map<String, String> users = new HashMap<>();
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void handleLoginButton() {
        // Retrieve username and password
        String username = TextField_Username.getText();
        String password = text_pass_Login.getText();

        // Debugging: Print to console
        System.out.println("Entered Username: " + username);
        System.out.println("Entered Password: " + password);

        // Validate credentials
        if (isValidCredentials(username, password)) {
            try {
                mainApp.showMusicPlayerPage(); // Navigate to Music Player Page
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            error_login.setText("Invalid username or password!");
        }
    }

    @FXML
    public void handleCreateAccountButton() {
        try {
            mainApp.showCreateAccountPage(); // Navigate to Create Account Page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        // Add some users for testing (username, password)
        users.put("user", "pass");
        users.put("1", "1");
    }

    private boolean isValidCredentials(String username, String password) {
        // Check if the username exists and the password matches
        return users.containsKey(username) && users.get(username).equals(password);
    }
}