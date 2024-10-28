import javax.swing.*;

public class MusicPlayer extends JFrame {

    public MusicPlayer() {
        setTitle("Music Player");
        setSize(2000, 1400); // Frame position and size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add any additional components here for the music player interface
        JLabel welcomeLabel = new JLabel("Welcome to the Music Player!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage(); // Display login page first
    }
}