import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class PhotoManager extends JFrame implements ActionListener {

    private final JMenuItem viewAlbumsMenuItem;
    private JMenuItem createGroupMenuItem;

    public PhotoManager() {
        setTitle("Photo Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null); // Center the window on the screen

        JMenuBar menuBar = new JMenuBar();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JMenu fileMenu = new JMenu("File");
        viewAlbumsMenuItem = new JMenuItem("View Albums");
        viewAlbumsMenuItem.addActionListener(this);
        fileMenu.add(viewAlbumsMenuItem);
        menuBar.add(fileMenu);
        createGroupMenuItem = new JMenuItem("Create New Album");
        createGroupMenuItem.addActionListener(this);
        fileMenu.add(createGroupMenuItem);

        setJMenuBar(menuBar);

        // Make the frame visible
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createGroupMenuItem) {
            // Open the CreateGroupFrame
            new CreateGroupFrame();
        } else if (e.getSource() == viewAlbumsMenuItem) {
            // Open the ViewAlbumsFrame
            new ViewAlbumsFrame();
        }
    }
    public static void main(String[] args) {
        new PhotoManager();
    }
}

class ViewAlbumsFrame extends JFrame implements ActionListener {
    private JComboBox<String> albumComboBox;
    private JComboBox<String> metadataComboBox;
    private JTextField metadataValueField;
    private JButton filterButton;
    private List<String> photoPaths;

    public ViewAlbumsFrame() {
        setTitle("View Albums");
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the window on the screen

        // Load and display album names
        loadAlbums();
        setVisible(true);
    }

    private void loadAlbums() {
        try {
            // Read JSON data from file
            String json = new String(Files.readAllBytes(Paths.get("Data.json")));

            // Parse JSON array
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0); // Get the first object in the array

            // Get the "groups" array
            JSONArray groupsArray = jsonObject.getJSONArray("groups");

            // Extract job names from each object in the "groups" array
            ArrayList<String> albumNames = new ArrayList<>();
            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupObject = groupsArray.getJSONObject(i);
                String jobName = groupObject.getString("Job");
                albumNames.add(jobName);
            }

            // Create a combo box to select albums
            albumComboBox = new JComboBox<>(albumNames.toArray(new String[0]));
            albumComboBox.addActionListener(this);

            JPanel panel = new JPanel();
            panel.add(new JLabel("Choose Album: "));
            panel.add(albumComboBox);

            metadataComboBox = new JComboBox<>();
            panel.add(new JLabel("Choose Metadata: "));
            panel.add(metadataComboBox);

            // Create a text field to enter metadata value
            metadataValueField = new JTextField(10);
            panel.add(new JLabel("Enter Metadata Value: "));
            panel.add(metadataValueField);

            // Create a button to trigger filter functionality
            filterButton = new JButton("Filter Photos");
            filterButton.addActionListener(e -> filterPhotos());
            panel.add(filterButton);

            JButton viewAllButton = new JButton("View All Photos");
            viewAllButton.addActionListener(e -> viewAllPhotos());
            panel.add(viewAllButton);

            add(panel);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading albums.");
        }
    }

    private void viewAllPhotos() {
        if (photoPaths != null) {
            new PhotoFrame(photoPaths);
        } else {
            JOptionPane.showMessageDialog(this, "No album selected.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == albumComboBox) {
            String selectedAlbum = (String) albumComboBox.getSelectedItem();

            String json;
            try {
                json = new String(Files.readAllBytes(Paths.get("Data.json")));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading from file.");
                return;
            }

            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0); // Get the first object in the array

            // Get the "groups" array
            JSONArray groupsArray = jsonObject.getJSONArray("groups");

            // Find the group that matches the selected job
            JSONObject selectedGroup = null;
            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject group = groupsArray.getJSONObject(i);
                if (group.getString("Job").equals(selectedAlbum)) {
                    selectedGroup = group;
                    break;
                }
            }

            if (selectedGroup == null) {
                JOptionPane.showMessageDialog(this, "No group found for job: " + selectedAlbum);
                return;
            }

            // Initialize photoPaths here
            photoPaths = getPhotoPathsForAlbum(selectedAlbum);

            // Clear the metadataComboBox
            metadataComboBox.removeAllItems();

            JSONArray metadataArray = selectedGroup.getJSONArray("metadata");
            for (int i = 0; i < metadataArray.length(); i++) {
                JSONObject metadataObject = metadataArray.getJSONObject(i);
                String metadataName = metadataObject.getString("name");
                metadataComboBox.addItem(metadataName);
            }
        }
    }

    private void filterPhotos() {
        String selectedMetadata = (String) metadataComboBox.getSelectedItem();
        String enteredValue = metadataValueField.getText();

        List<String> filteredPhotoPaths = new ArrayList<>();
        for (String photoPath : photoPaths) {
            try {
                String json = new String(Files.readAllBytes(Paths.get("Data.json")));
                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray photosArray = jsonObject.getJSONArray("photos");

                for (int i = 0; i < photosArray.length(); i++) {
                    JSONObject photoObject = photosArray.getJSONObject(i);
                    if (photoObject.getString("photo").equals(photoPath)) {
                        JSONObject metadataObject = photoObject.getJSONObject("metadata");
                        if (metadataObject.getString(selectedMetadata).equals(enteredValue)) {
                            filteredPhotoPaths.add(photoPath);
                            break;
                        }
                    }
                }
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(this, "Error filtering photos.");
            }
        }
        new PhotoFrame(filteredPhotoPaths);
    }
    private List<String> getPhotoPathsForAlbum(String selectedAlbum) {
        List<String> photoPaths = new ArrayList<>();
        try {
            // Read JSON data from file
            String json = new String(Files.readAllBytes(Paths.get("Data.json")));

            // Parse JSON array
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0); // Get the first object in the array

            // Get the "photos" array
            JSONArray photosArray = jsonObject.getJSONArray("photos");

            // Add all photo paths to the list
            for (int i = 0; i < photosArray.length(); i++) {
                JSONObject photoObject = photosArray.getJSONObject(i);
                String job = photoObject.getJSONObject("metadata").getString("Job");
                if (job.equals(selectedAlbum)) {
                    String photoPath = photoObject.getString("photo");
                    photoPaths.add(photoPath);
                }
            }
        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(this, "Error loading photos for album: " + selectedAlbum);
        }
        return photoPaths;
    }
}

class PhotoFrame extends JFrame {
    private JList<ImageIcon> photoList;
    private DefaultListModel<ImageIcon> listModel;
    private List<String> photoPaths;

    public PhotoFrame(List<String> photoPaths) {
        this.photoPaths = photoPaths;
        setTitle("Photos");
        setSize(600, 600);
        setLocationRelativeTo(null); // Center the window on the screen

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a list model and add photo paths to it
        listModel = new DefaultListModel<>();
        for (String photoPath : photoPaths) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(photoPath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            listModel.addElement(imageIcon);
        }

        // Create a list and add it to a scroll pane
        photoList = new JList<>(listModel);
        photoList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setIcon((ImageIcon) value);
                return label;
            }
        });
        photoList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = photoList.locationToIndex(evt.getPoint());
                    JOptionPane.showMessageDialog(null, "You clicked on photo at index: " + index);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(photoList);
        panel.add(scrollPane);

        JButton deleteButton = new JButton("Delete Photo");
        deleteButton.addActionListener(e -> deletePhoto());
        panel.add(deleteButton);

        JButton addButton = new JButton("Add Photo");
        addButton.addActionListener(e -> addPhoto());
        panel.add(addButton);

        JButton addMetadataButton = new JButton("Add Metadata");
        addMetadataButton.addActionListener(e -> addMetadata());
        panel.add(addMetadataButton);

        add(panel);
        setVisible(true);
    }

    private void deletePhoto() {
        int selectedIndex = photoList.getSelectedIndex();
        if (selectedIndex != -1) {
            // Remove photo from list
            ImageIcon photoIcon = listModel.remove(selectedIndex);

            // Remove photo from photoPaths
            String photoPath = photoPaths.remove(selectedIndex);

            // Remove photo from JSON file
            try {
                String json = new String(Files.readAllBytes(Paths.get("Data.json")));
                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray photosArray = jsonObject.getJSONArray("photos");

                for (int i = 0; i < photosArray.length(); i++) {
                    JSONObject photoObject = photosArray.getJSONObject(i);
                    if (photoObject.getString("photo").equals(photoPath)) {
                        photosArray.remove(i);
                        break;
                    }
                }

                jsonObject.put("photos", photosArray);
                jsonArray.put(0, jsonObject);

                try (FileWriter file = new FileWriter("Data.json")) {
                    file.write(jsonArray.toString());
                    JOptionPane.showMessageDialog(null, "Photo deleted successfully.");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error writing to file.");
                }
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(null, "Error deleting photo.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No photo selected.");
        }
    }

    private void addPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String photoPath = fileChooser.getSelectedFile().getPath();

            String job = JOptionPane.showInputDialog("Enter job:");
            JSONObject metadataObject = new JSONObject();
            metadataObject.put("Job", job);

            String input = JOptionPane.showInputDialog("Enter number of metadata:");
            try {
                int metadataCount = Integer.parseInt(input);
                for (int i = 0; i < metadataCount; i++) {
                    String metadataName = JOptionPane.showInputDialog("Enter metadata name:");
                    String metadataValue = JOptionPane.showInputDialog("Enter metadata value:");
                    metadataObject.put(metadataName, metadataValue);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }

            JSONObject newPhoto = new JSONObject();
            newPhoto.put("photo", photoPath);
            newPhoto.put("metadata", metadataObject);

            try {
                String json = new String(Files.readAllBytes(Paths.get("Data.json")));
                JSONArray jsonArray = new JSONArray(json); // Add this line
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray photosArray = jsonObject.getJSONArray("photos");
                photosArray.put(newPhoto);

                try (FileWriter file = new FileWriter("Data.json")) {
                    file.write(jsonArray.toString());
                    JOptionPane.showMessageDialog(null, "Photo added successfully.");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error writing to file.");
                }
            } catch (IOException ex) {
                ex.printStackTrace(); // This will print more detailed information about the exception
                JOptionPane.showMessageDialog(null, "Error reading from file.");
            }
        }
    }

    private void addMetadata() {
        int selectedIndex = photoList.getSelectedIndex();
        if (selectedIndex != -1) {
            String photoPath = photoPaths.get(selectedIndex);

            try {
                String json = new String(Files.readAllBytes(Paths.get("Data.json")));
                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray photosArray = jsonObject.getJSONArray("photos");

                JSONObject photoObject = null;
                for (int i = 0; i < photosArray.length(); i++) {
                    photoObject = photosArray.getJSONObject(i);
                    if (photoObject.getString("photo").equals(photoPath)) {
                        break;
                    }
                }

                if (photoObject != null) {
                    JSONObject metadataObject = photoObject.getJSONObject("metadata");

                    String input = JOptionPane.showInputDialog("Enter number of metadata:");
                    try {
                        int metadataCount = Integer.parseInt(input);
                        for (int i = 0; i < metadataCount; i++) {
                            String metadataName = JOptionPane.showInputDialog("Enter metadata name:");
                            String metadataValue = JOptionPane.showInputDialog("Enter metadata value:");
                            metadataObject.put(metadataName, metadataValue);

                            // Get the job from the photo's metadata
                            String job = metadataObject.getString("Job");

                            // Get the "groups" array
                            JSONArray groupsArray = jsonObject.getJSONArray("groups");

                            // Find the group that matches the job
                            JSONObject groupObject = null;
                            for (int j = 0; j < groupsArray.length(); j++) {
                                JSONObject group = groupsArray.getJSONObject(j);
                                if (group.getString("Job").equals(job)) {
                                    groupObject = group;
                                    break;
                                }
                            }

                            // If the group was found, add the new metadata to it
                            if (groupObject != null) {
                                JSONArray groupMetadataArray = groupObject.getJSONArray("metadata");
                                JSONObject newField = new JSONObject();
                                newField.put("name", metadataName);
                                groupMetadataArray.put(newField);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    }

                    try (FileWriter file = new FileWriter("Data.json")) {
                        file.write(jsonArray.toString());
                        JOptionPane.showMessageDialog(null, "Metadata added successfully.");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "Error writing to file.");
                    }
                }
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(null, "Error adding metadata.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No photo selected.");
        }
    }
}