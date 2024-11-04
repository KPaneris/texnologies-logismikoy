import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame implements ActionListener {

    private final JTextField userTextField;
    private final JPasswordField passwordField;
    private final JCheckBox showPasswordCheckbox;

    public LoginPage() {
        setTitle("Login Page");
        setBounds(300, 90, 400, 250);
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

        // Show Password Checkbox (next to password field)
        showPasswordCheckbox = new JCheckBox("Show");
        showPasswordCheckbox.setBounds(310, 70, 60, 30);
        showPasswordCheckbox.addActionListener(_ -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        });
        container.add(showPasswordCheckbox);

        // Login Button
        JButton loginButton = new JButton("Login"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded background
                super.paintComponent(g); // Draw the text and other components
                g2.dispose();
            }
        };
        loginButton.setBounds(85, 130, 100, 35);
        loginButton.setBorder(new RoundedBorder(20,Color.BLACK)); // Apply rounded corners
        loginButton.setBackground(Color.LIGHT_GRAY); // Set desired background color
        loginButton.setContentAreaFilled(false); // Disable default background
        loginButton.setFocusPainted(false); // Optional: Remove focus outline
        loginButton.addActionListener(this);
        container.add(loginButton);

        // Create Account Button
        JButton createAccountButton = new JButton("Create Account"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded background
                super.paintComponent(g); // Draw the text and other components
                g2.dispose();
            }
        };
        createAccountButton.setBounds(205, 130, 150, 35);
        createAccountButton.setBorder(new RoundedBorder(20,Color.BLACK)); // Apply rounded corners
        createAccountButton.addActionListener(_ -> {
            dispose();
            new CreateAccountPage();
        });
        createAccountButton.setBackground(Color.LIGHT_GRAY); // Set desired background color
        createAccountButton.setContentAreaFilled(false); // Disable default background
        createAccountButton.setFocusPainted(false); // Optional: Remove focus outline
        container.add(createAccountButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Users WHERE username = ? AND password = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dispose();
                new MusicPlayer(); // Opens MusicPlayer
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inner class for Create Account page
    private static class CreateAccountPage extends JFrame implements ActionListener {

        private final JTextField userTextField;
        private final JPasswordField passwordField;
        private final JPasswordField confirmPasswordField;
        private final JCheckBox showPasswordCheckbox;

        public CreateAccountPage() {
            setTitle("Create Account");
            setBounds(500, 90, 400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(true);

            Container container = getContentPane();
            container.setLayout(null);

            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(50, 30, 100, 30);
            container.add(userLabel);

            // Username TextField
            userTextField = new JTextField();
            userTextField.setBounds(150, 30, 150, 30);
            container.add(userTextField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 70, 100, 30);
            container.add(passwordLabel);

            // Password Field
            passwordField = new JPasswordField();
            passwordField.setBounds(150, 70, 150, 30);
            container.add(passwordField);

            JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
            confirmPasswordLabel.setBounds(20, 110, 130, 30);
            container.add(confirmPasswordLabel);

            // Confirm Password Field
            confirmPasswordField = new JPasswordField();
            confirmPasswordField.setBounds(150, 110, 150, 30);
            container.add(confirmPasswordField);

            showPasswordCheckbox = new JCheckBox("Show");
            showPasswordCheckbox.setBounds(310, 70, 60, 30);
            showPasswordCheckbox.addActionListener(_ -> {
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                    confirmPasswordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                    confirmPasswordField.setEchoChar('*');
                }
            });
            container.add(showPasswordCheckbox);

            JButton createButton = new JButton("Create Account"){
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded background
                    super.paintComponent(g); // Draw the text and other components
                    g2.dispose();
                }
            };
            createButton.setBounds(65, 180, 150, 35);
            createButton.setBorder(new RoundedBorder(20,Color.BLACK));// Apply rounded corners
            createButton.setBackground(Color.LIGHT_GRAY); // Set desired background color
            createButton.setContentAreaFilled(false); // Disable default background
            createButton.setFocusPainted(false); // Optional: Remove focus outline
            createButton.addActionListener(this);
            container.add(createButton);

            JButton backButton = new JButton("Back"){
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded background
                    super.paintComponent(g); // Draw the text and other components
                    g2.dispose();
                }
            };
            backButton.setBounds(230, 180, 100, 35);
            backButton.setBorder(new RoundedBorder(20,Color.BLACK)); // Apply rounded corners
            backButton.setBackground(Color.LIGHT_GRAY); // Set desired background color
            backButton.setContentAreaFilled(false); // Disable default background
            backButton.setFocusPainted(false); // Optional: Remove focus outline
            backButton.addActionListener(_ -> {
                dispose();
                new LoginPage();
            });
            container.add(backButton);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username or password cannot be empty.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.",
                        "Password Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement checkUser = connection.prepareStatement(
                             "SELECT * FROM Users WHERE username = ?")) {

                    checkUser.setString(1, username);
                    ResultSet resultSet = checkUser.executeQuery();

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(this, "Username already exists.",
                                "Registration Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try (PreparedStatement insertUser = connection.prepareStatement(
                                "INSERT INTO Users (username, password) VALUES (?, ?)")) {
                            insertUser.setString(1, username);
                            insertUser.setString(2, password);
                            insertUser.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Account created successfully!",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new LoginPage();
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database error.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
