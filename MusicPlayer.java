import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicPlayer extends JFrame {
    private final JPanel contentPanel;
    private final CardLayout cardLayout;

    private final int buttonHeight = 50; // Height of buttons
    private final int buttonWidth = 50; // Width of buttons

    private JPopupMenu categoriesMenu; // Popup menu for categories
    private JPopupMenu settingsMenu; // Popup menu for settings

    public MusicPlayer() {
        // Application title and settings
        setTitle("Music Player");
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create horizontal toolbar
        JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
        toolbar.setFloatable(false);
        toolbar.setBackground(new Color(45, 45, 45));

        // Create buttons for the toolbar using oval buttons
        JButton categoriesButton = createOvalButton("Categories", "src/photos/menu.png", new Color(177, 135, 35));
        JButton homeButton = createOvalButton("Home", "src/photos/home.png", new Color(243, 236, 236, 255));
        JButton settingsButton = createOvalButton("Settings", "src/photos/setting.png", new Color(33, 150, 243));
        JButton myFavoritesSongsButton = createOvalButton("Favorite Songs", "src/photos/love_songs.png", new Color(255, 0, 0));
        JButton myFavoritesArtistButton = createOvalButton("Favorite Artist", "src/photos/artist.png", new Color(76, 175, 80));
        JButton moodButton = createOvalButton("Mood", "src/photos/mood.png", new Color(255, 87, 34));

        // Create search box with rounded corners
        RoundedSearchBox searchBox = new RoundedSearchBox();
        JButton searchButton = getSearchButton(searchBox);

        // Add buttons and search components to the toolbar with increased spacing
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(homeButton);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(categoriesButton);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(searchButton);
        toolbar.add(Box.createHorizontalStrut(10)); // Reduced space between components
        toolbar.add(searchBox);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(myFavoritesSongsButton);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(myFavoritesArtistButton);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(moodButton);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components
        toolbar.add(settingsButton);
        toolbar.add(Box.createHorizontalStrut(20)); // Increased space between components

        // Add toolbar to the frame
        add(toolbar, BorderLayout.NORTH);

        // Central panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel, BorderLayout.CENTER);

        // Add panels for each category
        contentPanel.add(createHomePanel(), "Home");
        contentPanel.add(createFavoritesSongsPanel(), "My Favorites Songs");
        contentPanel.add(createFavoritesArtistPanel(), "My Favorites Artist");
        contentPanel.add(createMoodPanel(), "Mood");

        // Initialize categories and settings menus
        initializeCategoriesMenu();
        initializeSettingsMenu(settingsButton);

        // Action listeners for changing content
        homeButton.addActionListener(e -> cardLayout.show(contentPanel, "Home"));
        categoriesButton.addActionListener(e -> toggleCategoriesMenu(categoriesButton));
        myFavoritesSongsButton.addActionListener(e -> cardLayout.show(contentPanel, "My Favorites Songs"));
        myFavoritesArtistButton.addActionListener(e -> cardLayout.show(contentPanel, "My Favorites Artist"));
        moodButton.addActionListener(e -> cardLayout.show(contentPanel, "Mood"));

        setVisible(true);
    }

    private void toggleCategoriesMenu(JButton categoriesButton) {
        if (categoriesMenu.isShowing()) {
            categoriesMenu.setVisible(false); // Close the menu if it's already showing
        } else {
            categoriesMenu.show(categoriesButton, 0, categoriesButton.getHeight()); // Show the menu below the button
        }
    }

    private void toggleSettingsMenu(JButton settingsButton) {
        if (settingsMenu.isShowing()) {
            settingsMenu.setVisible(false); // Close the menu if it's already showing
        } else {
            settingsMenu.show(settingsButton, 0, settingsButton.getHeight()); // Show the menu below the button
        }
    }

    // Δημιουργία του popup menu για τις κατηγορίες
    private void initializeCategoriesMenu() {
        categoriesMenu = new JPopupMenu();
        categoriesMenu.setBackground(new Color(100, 100, 100)); // Χρώμα φόντου για το popup menu (γκρι)
        String[] categories = {"Rock", "Pop", "Hip Hop", "Jazz", "Classical"};

        for (String category : categories) {
            JMenuItem item = new JMenuItem(category);
            item.setForeground(Color.WHITE); // Χρώμα γραμμάτων για το μενού (λευκό)
            item.setBackground(new Color(100, 100, 100)); // Χρώμα φόντου για το μενού (γκρι)
            item.addActionListener(e -> {
                System.out.println("Selected category: " + category);
                categoriesMenu.setVisible(false);
            });
            categoriesMenu.add(item);
        }
    }

    private void initializeSettingsMenu(JButton settingsButton) {
        settingsMenu = new JPopupMenu();
        settingsMenu.setBackground(new Color(100, 100, 100)); // Background color for the settings menu
        JMenuItem logOutItem = new JMenuItem("Log Out");
        logOutItem.setForeground(Color.WHITE); // Text color for the menu item
        logOutItem.setBackground(new Color(100, 100, 100)); // Background color for the menu item
        logOutItem.addActionListener(e -> {
            dispose(); // Close main window
            new LoginPage(); // Open the login page
        });
        settingsMenu.add(logOutItem);
        settingsButton.addActionListener(e -> toggleSettingsMenu(settingsButton));
    }

    private JButton getSearchButton(JTextField searchBox) {
        // Create a search button using the OvalButton class
        JButton searchButton = new OvalButton("Search", "src/photos/search.png", new Color(141, 220, 15)); // Use the same color as the toolbar
        searchButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight)); // Use the same dimensions as other buttons
        searchButton.setToolTipText("Search");

        // Add action listener for search button
        searchButton.addActionListener(e -> {
            String searchTerm = searchBox.getText();
            // Implement search functionality here
            System.out.println("Searching for: " + searchTerm);
        });

        return searchButton;
    }

    // Method to create a round button for the toolbar
    private JButton createOvalButton(String tooltip, String iconPath, Color backgroundColor) {
        OvalButton button = new OvalButton(tooltip, iconPath, backgroundColor);
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        return button;
    }

    private class OvalButton extends JButton {
        public OvalButton(String tooltip, String iconPath, Color backgroundColor) {
            super();
            setToolTipText(tooltip);
            setBackground(backgroundColor);
            setOpaque(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setMargin(new Insets(0, 0, 0, 0));

            // Load and scale icon
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));

            // Mouse event effects
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(100, 100, 100));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(backgroundColor);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(new Color(150, 150, 150));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(backgroundColor);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(buttonWidth, buttonHeight);
        }
    }

    // Rounded search box with no corners
    private class RoundedSearchBox extends JTextField {
        public RoundedSearchBox() {
            super(10);
            setPreferredSize(new Dimension(150, buttonHeight)); // Adjust size here
            setOpaque(false); // Make transparent
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Add padding
            setBackground(new Color(70, 70, 70)); // Set background color
            setForeground(Color.WHITE); // Set text color
            setCaretColor(Color.WHITE); // Set caret color
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded rectangle
            super.paintComponent(g);
        }
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        JLabel titleLabel = new JLabel("Welcome to the HOME!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createFavoritesSongsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        JLabel titleLabel = new JLabel("Your Favorite Songs!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createFavoritesArtistPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        JLabel titleLabel = new JLabel("Your Favorite Artists!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createMoodPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        JLabel titleLabel = new JLabel("Mood Based Suggestions!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicPlayer::new);
    }
}
