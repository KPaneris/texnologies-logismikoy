package org.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    public CheckBox check_box_pass;
    @FXML
    public Button create_account_Login;
    @FXML
    public AnchorPane FrameLogin;


    @FXML
    private TextField TextField_Username;

    @FXML
    private PasswordField text_pass_Login;






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




    // Μέθοδος για να διαχειριστεί την επιλογή του checkbox
    @FXML
    public void handleChekBoxPass() {
        if (check_box_pass.isSelected()) {
            // Αν το checkbox είναι επιλεγμένο, εμφανίζουμε τον κωδικό ως κανονικό κείμενο

            text_pass_Login.setPromptText(text_pass_Login.getText());
            text_pass_Login.setText("");
        } else {
            // Αν δεν είναι επιλεγμένο το checkbox, εμφανίζουμε τον κωδικό ως αστερίσκους

            text_pass_Login.setText(text_pass_Login.getPromptText());
            text_pass_Login.setPromptText("");
        }
    }



}
