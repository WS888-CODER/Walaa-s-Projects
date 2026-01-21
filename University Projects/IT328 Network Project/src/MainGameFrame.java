package network_phase2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainGameFrame extends JFrame {
    private JLabel logoLabel;
    private JTextField usernameField;
    private JTextArea textArea;
    private JButton connectButton, playButton, disconnectButton;
    private Client client;
    private JLabel timerLabel; // Timer next to Connect button
    Font customFont;

    public MainGameFrame() {
        setTitle("Find the Difference Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Show the splash screen first
        showSplashScreen();
    }

    /**
     * Method to display the splash screen
     */
    private void showSplashScreen() {
        String imagePath = "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\NumberSpy.png";
        ImageIcon logoIcon = loadImageIcon(imagePath);
        customFont = new Font("Serif", Font.BOLD | Font.ITALIC, 24);

        // Initialize logo label
        logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add the JLabel to a panel
        JPanel splashPanel = new JPanel(new BorderLayout());
        splashPanel.add(logoLabel, BorderLayout.CENTER);

        // Add the splash panel to the frame
        add(splashPanel, BorderLayout.CENTER);
        setVisible(true);
        setLocationRelativeTo(null);

        // Add a mouse listener to move to the username panel
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(splashPanel);
                showUsernamePanel();
                revalidate();
                repaint();
            }
        });

        // Add a ComponentListener to dynamically resize the image when the frame resizes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (logoIcon != null) {
                    // Resize the image to fit the frame size
                    Image scaledImage = logoIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    logoLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
        });

        // Initial image setup
        if (logoIcon != null) {
            Image scaledImage = logoIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            logoLabel.setText("Image not found!");
        }
    }

    /**
     * Method to show the username and connection panel
     */
    private void showUsernamePanel() {
        JPanel topPanel = new JPanel(new FlowLayout());
        usernameField = new JTextField(10);
        connectButton = new JButton("Connect");
        timerLabel = new JLabel("Timer: 0s"); // Initialize timer label
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        topPanel.add(new JLabel("Username:"));
        topPanel.add(usernameField);
        topPanel.add(connectButton);
        topPanel.add(timerLabel); // Add timer label next to Connect button
        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.decode("#002424"));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(customFont);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.getViewport().setBackground(Color.decode("#002424"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        playButton = new JButton("Play");
        playButton.setEnabled(false);
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setEnabled(false);
        bottomPanel.add(playButton);
        bottomPanel.add(disconnectButton);
        add(bottomPanel, BorderLayout.SOUTH);

        connectButton.addActionListener(e -> connectToServer());

        playButton.addActionListener(e -> {
            if (client != null) {
                client.sendPlayRequest(); // Notify the server that the player clicked "Play"
                playButton.setEnabled(false); // Disable the button to prevent multiple clicks
            }
        });

        disconnectButton.addActionListener(e -> quitGame());

        revalidate();
        repaint();
    }

    private ImageIcon loadImageIcon(String path) {
        File file = new File(path);
        if (file.exists()) {
            return new ImageIcon(path);
        } else {
            System.err.println("Error: Image not found at path: " + path);
            return null;
        }
    }

    private void connectToServer() {
        try {
            client = new Client(this);  // Connect to server
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                client.sendUsername(username);
                textArea.append("Connected as " + username + ".\n");
                playButton.setEnabled(true);  // Enable play button after connecting
                disconnectButton.setEnabled(true);
                client.listenToServer();  // Start listening for server messages
            } else {
                textArea.append("Please enter a username.\n");
            }
        } catch (IOException ex) {
            textArea.append("Error: Unable to connect to server.\n");
        }
    }

    public void updateTimer(String timeLeft) {
        SwingUtilities.invokeLater(() -> {
            timerLabel.setText("Timer: " + timeLeft + "s"); // Update timer label
        });
    }

    public void enablePlayButton() {
        SwingUtilities.invokeLater(() -> playButton.setEnabled(true));
    }

    public void openPlayRoom() {
        SwingUtilities.invokeLater(() -> {
            GameFrame playRoom = new GameFrame(client); // Create the playroom GUI
            client.setPlayRoomGUI(playRoom); // Link playroom GUI to the client
            playRoom.setVisible(true); // Display the playroom
            dispose(); // Close the current frame
        });
    }

    private void quitGame() {
        client.quit();
        new MainGameFrame();  // Reopen the lobby for reconnection
        dispose();
    }

    public void appendMessage(String message) {
        textArea.append(message + "\n");
    }

public void updatePlayerList(ArrayList<String> players) {
    SwingUtilities.invokeLater(() -> {
        textArea.setText("Players in the Lobby:\n"); // Updated title
        for (String player : players) {
            textArea.append(player + "\n");
        }
    });
}


    public static void main(String[] args) {
        new MainGameFrame();
    }
}
