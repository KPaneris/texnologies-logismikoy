import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame implements ActionListener {

    private JLabel userLabel, passwordLabel, messageLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;

    // Store users in memory (for demonstration purposes)
    private static Map<String, String> users = new HashMap<>();

    public LoginPage() {

        setTitle("Login Page");
        setBounds(300, 90, 400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Container setup
        Container container = getContentPane();
        container.setLayout(null);

        // Username Label
        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 30, 100, 30);
        container.add(userLabel);

        // Username TextField
        userTextField = new JTextField();
        userTextField.setBounds(150, 30, 150, 30);
        container.add(userTextField);

        // Password Label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 100, 30);
        container.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 30);
        container.add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 110, 100, 30);
        loginButton.addActionListener(this);
        container.add(loginButton);

        // Create Account Button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(150, 150, 150, 30);
        createAccountButton.addActionListener(e -> new CreateAccountPage());
        container.add(createAccountButton);

        // Message Label
        messageLabel = new JLabel();
        messageLabel.setBounds(50, 190, 300, 30);
        container.add(messageLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = this.userTextField.getText();
        String password = new String(this.passwordField.getPassword());

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Users WHERE username = ? AND password = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // Εάν υπάρχει αποτέλεσμα, ο χρήστης είναι έγκυρος
                this.messageLabel.setForeground(Color.GREEN);
                this.messageLabel.setText("Login successful!");
                this.dispose();
                new MusicPlayer(); // Άνοιξε την εφαρμογή μουσικής ή την κύρια σελίδα
            } else {
                this.messageLabel.setForeground(Color.RED);
                this.messageLabel.setText("Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.messageLabel.setForeground(Color.RED);
            this.messageLabel.setText("Database error.");
        }
    }


    // Inner class for Create Account page
    private class CreateAccountPage extends JFrame implements ActionListener {

        private JLabel userLabel, passwordLabel, confirmPasswordLabel, messageLabel;
        private JTextField userTextField;
        private JPasswordField passwordField, confirmPasswordField;
        private JButton createButton;

        public CreateAccountPage() {
            setTitle("Create Account");
            setBounds(500, 90, 400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(true);

            // Container setup
            Container container = getContentPane();
            container.setLayout(null);

            // Username Label
            userLabel = new JLabel("Username:");
            userLabel.setBounds(50, 30, 100, 30);
            container.add(userLabel);

            // Username TextField
            userTextField = new JTextField();
            userTextField.setBounds(150, 30, 150, 30);
            container.add(userTextField);

            // Password Label
            passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 70, 100, 30);
            container.add(passwordLabel);

            // Password Field
            passwordField = new JPasswordField();
            passwordField.setBounds(150, 70, 150, 30);
            container.add(passwordField);

            // Confirm Password Label
            confirmPasswordLabel = new JLabel("Confirm Password:");
            confirmPasswordLabel.setBounds(20, 110, 130, 30);
            container.add(confirmPasswordLabel);

            // Confirm Password Field
            confirmPasswordField = new JPasswordField();
            confirmPasswordField.setBounds(150, 110, 150, 30);
            container.add(confirmPasswordField);

            // Create Button
            createButton = new JButton("Create Account");
            createButton.setBounds(150, 150, 150, 30);
            createButton.addActionListener(this);
            container.add(createButton);

            // Message Label for feedback
            messageLabel = new JLabel();
            messageLabel.setBounds(50, 190, 300, 30);
            container.add(messageLabel);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if password and confirm password match and if the username is not empty
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username or password cannot be empty.");
            } else if (!password.equals(confirmPassword)) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Passwords do not match.");
            } else if (users.containsKey(username)) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username already exists.");
            } else {
                // Add new user to the user map
                users.put(username, password);
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Account created successfully!");
                dispose(); // Close the create account page
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
