package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class CreateAccountController {

    @FXML
    private CheckBox check_pass1_Create_Account;
    @FXML
    private PasswordField text_pass2_Create_Account;
    @FXML
    private AnchorPane FrameAccountApplication;
    @FXML
    private TextField text_user_Create_Account;
    @FXML
    private PasswordField text_pass1_Create_Account;
    @FXML
    private Button create_account_Button_Page;
    @FXML
    private Button cancel_Button;
    @FXML
    private CheckBox check_pass2_Create_Account;
    @FXML
    private Label error_create_account;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void handleCreateAccountButton() {
        String username = text_user_Create_Account.getText();
        String password1 = text_pass1_Create_Account.getText();
        String password2 = text_pass2_Create_Account.getText();

        // Validate input fields
        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            error_create_account.setText("All fields are required!");
            return;
        }

        if (!password1.equals(password2)) {
            error_create_account.setText("Passwords do not match!");
            return;
        }

        // Simulate account creation logic (e.g., saving to database)
        System.out.println("Account created for username: " + username);

        // Redirect to Login Page
        try {
            mainApp.showLoginPage(); // Redirect to login page after account creation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancelButton() {
        // Return to the login page without creating an account
        try {
            mainApp.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
