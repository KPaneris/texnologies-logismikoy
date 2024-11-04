import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame implements ActionListener {

    private final JLabel loginMessageLabel;
    private final JTextField userTextField;
    private final JPasswordField passwordField;
    private final JCheckBox showPasswordCheckbox;

    // Store users in memory (for demonstration purposes)
    private static final Map<String, String> users = new HashMap<>();

    public LoginPage() {
        setTitle("Login Page");
        setBounds(300, 90, 400, 300); // Height accommodates messages
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Container setup
        Container container = getContentPane();
        container.setLayout(null);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 30, 100, 30);
        container.add(userLabel);

        // Username TextField
        userTextField = new JTextField();
        userTextField.setBounds(150, 30, 150, 30);
        container.add(userTextField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 100, 30);
        container.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 30);
        container.add(passwordField);

        // Show Password Checkbox
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(150, 100, 150, 30);
        showPasswordCheckbox.addActionListener(_ -> {
            // If checkbox is selected, show password, else hide it
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        });
        container.add(showPasswordCheckbox);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 130, 100, 30);
        loginButton.addActionListener(this);
        container.add(loginButton);

        // Message Label for login feedback
        loginMessageLabel = new JLabel();
        loginMessageLabel.setBounds(50, 160, 300, 30);
        loginMessageLabel.setForeground(Color.RED); // Default color for error messages
        container.add(loginMessageLabel);

        // Create Account Button (stacked below Login)
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(150, 200, 150, 30); // Positioned below the Login button
        createAccountButton.addActionListener(_ -> {
            dispose(); // Close the login page
            new CreateAccountPage(); // Open Create Account page
        });
        container.add(createAccountButton);

        // Message Label for general feedback (stacked below Create Account button)
        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(50, 240, 300, 30);
        messageLabel.setForeground(Color.RED); // Set default color for error messages to red
        container.add(messageLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        // Clear previous messages
        loginMessageLabel.setText("");

        // Check if the entered credentials are empty
        if (username.isEmpty() || password.isEmpty()) {
            // Show an error message if either field is empty
            loginMessageLabel.setForeground(Color.RED);
            loginMessageLabel.setText("Please fill in both fields."); // Error message for empty fields
        } else if (users.containsKey(username) && users.get(username).equals(password)) {
            loginMessageLabel.setForeground(Color.GREEN);
            loginMessageLabel.setText("Login successful!"); // Success message
            dispose(); // Close login page
            new MusicPlayer(); // Open MusicPlayer if login is successful
        } else {
            loginMessageLabel.setForeground(Color.RED);
            loginMessageLabel.setText("Invalid username or password."); // Error message for invalid login
        }
    }

    // Inner class for Create Account page
    private static class CreateAccountPage extends JFrame implements ActionListener {

        private final JLabel messageLabel;
        private final JTextField userTextField;
        private final JPasswordField passwordField;
        private final JPasswordField confirmPasswordField;
        private final JCheckBox showPasswordCheckbox; // Checkbox to show password

        public CreateAccountPage() {
            setTitle("Create Account");
            setBounds(500, 90, 400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(true);

            // Container setup
            Container container = getContentPane();
            container.setLayout(null);

            // Username Label
            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(50, 30, 100, 30);
            container.add(userLabel);

            // Username TextField
            userTextField = new JTextField();
            userTextField.setBounds(150, 30, 150, 30);
            container.add(userTextField);

            // Password Label
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 70, 100, 30);
            container.add(passwordLabel);

            // Password Field
            passwordField = new JPasswordField();
            passwordField.setBounds(150, 70, 150, 30);
            container.add(passwordField);

            // Confirm Password Label
            JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
            confirmPasswordLabel.setBounds(20, 110, 130, 30);
            container.add(confirmPasswordLabel);

            // Confirm Password Field
            confirmPasswordField = new JPasswordField();
            confirmPasswordField.setBounds(150, 110, 150, 30);
            container.add(confirmPasswordField);

            // Show Password Checkbox
            showPasswordCheckbox = new JCheckBox("Show Password");
            showPasswordCheckbox.setBounds(150, 140, 150, 30);
            showPasswordCheckbox.addActionListener(_ -> {
                // If checkbox is selected, show password, else hide it
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show password
                    confirmPasswordField.setEchoChar((char) 0); // Show confirm password
                } else {
                    passwordField.setEchoChar('*'); // Hide password
                    confirmPasswordField.setEchoChar('*'); // Hide confirm password
                }
            });
            container.add(showPasswordCheckbox);

            // Create Button
            JButton createButton = new JButton("Create Account");
            createButton.setBounds(50, 180, 150, 30);
            createButton.addActionListener(this);
            container.add(createButton);

            // Message Label for create account feedback
            messageLabel = new JLabel();
            messageLabel.setBounds(50, 220, 300, 30);
            messageLabel.setForeground(Color.RED); // Default color for error messages
            container.add(messageLabel);

            // Back Button
            // Create and Back buttons
            JButton backButton = new JButton("Back");
            backButton.setBounds(210, 180, 150, 30);
            backButton.addActionListener(_ -> {
                dispose(); // Close the create account page
                new LoginPage(); // Return to login page
            });
            container.add(backButton);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Clear previous messages
            messageLabel.setText("");

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username or password cannot be empty."); // Display error message
            } else if (!password.equals(confirmPassword)) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Passwords do not match."); // Display error message for password mismatch
            } else if (users.containsKey(username)) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username already exists."); // Display error message for existing username
            } else {
                // Add new user to the user map
                users.put(username, password);
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Account created successfully!"); // Account created successfully
                dispose(); // Close the create account page
                new LoginPage(); // Return to login page after successful registration
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}