package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateAccountController {
    private MainApp mainApp;

    @FXML
    private Button create_account_Button_Page;
    @FXML
    private Button cancel_Button;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void handleCreateAccountButton() {
        try {
            mainApp.showLoginPage(); // Επιστροφή στη Login Page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancelButton() {
        try {
            mainApp.showLoginPage(); // Επιστροφή στη Login Page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
