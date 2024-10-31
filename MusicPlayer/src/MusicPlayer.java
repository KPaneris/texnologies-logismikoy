import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MusicPlayer extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MusicPlayer() {
        setTitle("Music Player");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Δημιουργία toolbar με κουμπιά
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(30, 30, 30)); // Σκούρο χρώμα για το toolbar

        JButton homeButton = new JButton("Home");
        JButton myFavoritesButton = new JButton("My Favorites");
        JButton profileButton = new JButton("Profile");
        JButton hottestButton = new JButton("Hottest");
        JButton logOutButton = new JButton("Log Out");

        // Ορισμός χρωμάτων για τα κουμπιά
        homeButton.setBackground(new Color(70, 130, 180));
        homeButton.setForeground(Color.WHITE);
        myFavoritesButton.setBackground(new Color(70, 130, 180));
        myFavoritesButton.setForeground(Color.WHITE);
        profileButton.setBackground(new Color(70, 130, 180));
        profileButton.setForeground(Color.WHITE);
        hottestButton.setBackground(new Color(70, 130, 180));
        hottestButton.setForeground(Color.WHITE);
        logOutButton.setBackground(new Color(220, 50, 50)); // Κόκκινο χρώμα για το logout
        logOutButton.setForeground(Color.WHITE);

        // Προσθήκη κουμπιών στο toolbar
        toolBar.add(homeButton);
        toolBar.add(myFavoritesButton);
        toolBar.add(profileButton);
        toolBar.add(hottestButton);
        toolBar.add(logOutButton);

        add(toolBar, BorderLayout.NORTH);

        // Δημιουργία κεντρικού panel με CardLayout για εναλλαγή περιεχομένου
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel, BorderLayout.CENTER);

        // Προσθήκη panels για κάθε κατηγορία
        contentPanel.add(createHomePanel(), "Home");
        contentPanel.add(createFavoritesPanel(), "My Favorites");
        contentPanel.add(createProfilePanel(), "Profile");
        contentPanel.add(createHottestPanel(), "Hottest");

        // Ακροατές δράσης για κάθε κουμπί
        homeButton.addActionListener(e -> cardLayout.show(contentPanel, "Home"));
        myFavoritesButton.addActionListener(e -> cardLayout.show(contentPanel, "My Favorites"));
        profileButton.addActionListener(e -> cardLayout.show(contentPanel, "Profile"));
        hottestButton.addActionListener(e -> cardLayout.show(contentPanel, "Hottest"));

        // Logout κουμπί
        logOutButton.addActionListener(e -> {
            dispose();
            new LoginPage(); // Εμφάνιση της σελίδας login
        });

        setVisible(true);
    }

    // Δημιουργία panel για την κατηγορία Home με πληροφορίες και φόντο
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50)); // Σκούρο φόντο
        panel.setLayout(new BorderLayout());

        // Ρύθμιση εικόνας φόντου
        JLabel backgroundLabel = new JLabel(new ImageIcon("path/to/home_background.jpg")); // Αλλαγή στο μονοπάτι της εικόνας
        panel.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to the Music Player!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center; color: white;'>"
                + "This app allows you to manage and listen to your favorite music tracks.<br>"
                + "You can create your own playlists, explore trending songs, and much more!<br>"
                + "Discover a variety of artists and genres at your fingertips.<br>"
                + "Enjoy your musical journey!</div></html>", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        backgroundLabel.add(infoLabel, BorderLayout.CENTER);

        return panel;
    }

    // Δημιουργία panel για την κατηγορία My Favorites με φόντο
    private JPanel createFavoritesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50)); // Σκούρο φόντο
        panel.setLayout(new BorderLayout());

        // Ρύθμιση εικόνας φόντου
        JLabel backgroundLabel = new JLabel(new ImageIcon("path/to/favorites_background.jpg")); // Αλλαγή στο μονοπάτι της εικόνας
        panel.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your Favorite Tracks!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center; color: white;'>"
                + "Here you can find all your favorite tracks.<br>"
                + "Easily manage your playlists and enjoy your music!</div></html>", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        backgroundLabel.add(infoLabel, BorderLayout.CENTER);

        return panel;
    }

    // Δημιουργία panel για την κατηγορία Profile με φόντο
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50)); // Σκούρο φόντο
        panel.setLayout(new BorderLayout());

        // Ρύθμιση εικόνας φόντου
        JLabel backgroundLabel = new JLabel(new ImageIcon("path/to/profile_background.jpg")); // Αλλαγή στο μονοπάτι της εικόνας
        panel.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your Profile Info!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center; color: white;'>"
                + "Manage your account settings here.<br>"
                + "Update your information and preferences.</div></html>", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        backgroundLabel.add(infoLabel, BorderLayout.CENTER);

        return panel;
    }

    // Δημιουργία panel για την κατηγορία Hottest με φόντο
    private JPanel createHottestPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50)); // Σκούρο φόντο
        panel.setLayout(new BorderLayout());

        // Ρύθμιση εικόνας φόντου
        JLabel backgroundLabel = new JLabel(new ImageIcon("path/to/hottest_background.jpg")); // Αλλαγή στο μονοπάτι της εικόνας
        panel.add(backgroundLabel, BorderLayout.CENTER);
        backgroundLabel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Today's Hottest Hits!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center; color: white;'>"
                + "Check out the trending hits that everyone is listening to.<br>"
                + "Stay updated with the latest music trends!</div></html>", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        backgroundLabel.add(infoLabel, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {

        new MusicPlayer();
        //new LoginPage(); // Αρχική εμφάνιση της σελίδας login
    }
}
