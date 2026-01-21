package network_phase2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameFrame extends JFrame {
    
    private JTextArea messagesArea, playerListArea, scoreArea;
    private JTextPane wordsDisplayArea;
    private static JTextField answerField;
    private static JButton submitButton, quitButton;
    private JLabel timerLabel;
    private Client client;
    private boolean gameStarted = false; // Track if the game has started


    
    
    //public static List<Client> Players = GameRoom.getPlayers();
    private static JLabel gameImageLabel;
    private static Timer gameTimer;
    public static int currentLevel = 0;
    private static String[] imagePaths;
    private static String[] levelAnswers;
    public static JTextArea scoreTextArea;
    private static AtomicBoolean levelCompleted = new AtomicBoolean(false);
    private static int roomId;
//private GameRoom gameRoom;

    public GameFrame(Client client) {
    this.client = client;
    
   // if (this.gameRoom == null) 
        //throw new IllegalArgumentException("Invalid room ID: " + roomId);
    
        setTitle("Find the Difference Game - Levels");
        setSize(1400, 700);  // Increased frame width and height for better alignment
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.decode("#002424"));

        // Set up game levels (paths and answers to be provided by you)
          imagePaths = new String[]{
                "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\L1.jpg",
                "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\L2.jpg",
                "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\L3.jpg",
                "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\L4.jpg",
                "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\L5.jpg"
        };
        levelAnswers = new String[]{
                "I",
                "9E79",
                "9",
                "9F74",
                "3899"
        };
        
       // GameFrame gameFrame = new GameFrame(currentPlayer, currentPlayer.getCurrentRoomId());
//gameFrame.setVisible(true);

        // Left panel for the game image
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(950, 650));  // Adjusted panel size for larger image display
        leftPanel.setBackground(Color.decode("#002424"));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 0, 0));  // Add padding to move the image slightly to the right and down
        gameImageLabel = new JLabel();
        gameImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameImageLabel.setPreferredSize(new Dimension(950, 650));  // Increased image label size for better visibility
        leftPanel.add(gameImageLabel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Right panel for scores
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 650));  // Adjusted panel size for better alignment and made it longer
        rightPanel.setBackground(Color.decode("#002424"));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 100));  // Add padding to move the text area slightly away from the corner and down
        scoreTextArea = new JTextArea();
        scoreTextArea.setPreferredSize(new Dimension(150, 600));  // Adjusted text area size to be thinner but longer
        scoreTextArea.setEditable(false);
        scoreTextArea.setBackground(Color.WHITE);
        scoreTextArea.setForeground(Color.BLACK);
        scoreTextArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        rightPanel.add(scoreTextArea, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        // Set up the answer panel
        JPanel answerPanel = new JPanel();
        answerPanel.setBackground(Color.decode("#002424"));
        answerField = new JTextField(10);
        submitButton = new JButton("Submit");
        JLabel answerLabel = new JLabel("Your Answer: ");
        answerLabel.setForeground(Color.RED);  // Set "Your Answer" label color to red
        answerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));  // Set "Your Answer" label to bold
        answerPanel.add(answerLabel);
        answerPanel.add(answerField);
        answerPanel.add(submitButton);
        add(answerPanel, BorderLayout.SOUTH);

        quitButton = new JButton("Quit");
quitButton.setPreferredSize(new Dimension(80, 25)); // Set to smaller dimensions
quitButton.addActionListener(e -> quitGame());
answerPanel.add((quitButton));




        // Action listener for the submit button
        submitButton.addActionListener(e -> sendAnswer());
        

        // Add a component listener to make sure it resizes when the frame changes
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Resize the components dynamically when the frame is resized
                Dimension newSize = new Dimension(getWidth() / 2 - 50, getHeight() - 100);
                if (gameImageLabel != null) {
                    gameImageLabel.setPreferredSize(newSize);
                    gameImageLabel.revalidate();
                }
                if (scoreTextArea != null) {
                    scoreTextArea.setPreferredSize(new Dimension(150, 600));  // Keep the score area thinner but longer
                    scoreTextArea.revalidate();
                }
            }
        });

        // Start the first level
        currentLevel = 0;
        startLevel(currentLevel);
    }
    
    public void startGame() {
        timerLabel.setText("Game started!");
        answerField.setEnabled(true);
        submitButton.setEnabled(true);
        gameStarted = true;
        startLevel(currentLevel);
    }

    /**
     * Method to start a specific level
     */
public static void startLevel(int level) {
    levelCompleted.set(false);

    if (level >= imagePaths.length) {
        return; // If all levels are completed, exit
    }

    // Load the level image
    ImageIcon levelImage = resizeImageIcon(loadImageIcon(imagePaths[level]), 950, 650);
    if (levelImage != null) {
        gameImageLabel.setIcon(levelImage);
    } else {
        gameImageLabel.setText("Image not found!");
    }

    // Reset answer field and button
    SwingUtilities.invokeLater(() -> {
        answerField.setEnabled(true); // Enable the answer field
        submitButton.setEnabled(true); // Enable the submit button
    });

    // Set up timer for each level (now 5 minutes per level)
    if (gameTimer != null) {
        gameTimer.stop();
    }
    gameTimer = new Timer(300000, e -> { // 300,000 ms = 5 minutes
        if (!levelCompleted.get()) {
            moveToNextLevel(); // Automatically move to the next level when time is up
        }
    });
    gameTimer.setRepeats(false);
    gameTimer.start();
}

    
    
    private void sendAnswer() {
    String answer = answerField.getText().trim().toLowerCase();

    // Send the answer to the server for validation
    client.sendMessage("answer:" + answer);

    // Clear the input field
    answerField.setText("");
}

    
    
    public void appendMessage(String message) {
        if (message.startsWith("timer:")) {
            updateTimer(message.replace("timer:", ""));
        } else if (message.startsWith("Game time left:")) {
            updateTimer(message.replace("Game time left:", "").trim());
        } else if (message.equals("startGame")) {
            startGame(); // Trigger game start
        } else if (message.equals("Game Over!")) {
            scoreTextArea.append("\nGame Over! Calculating results...\n");
            answerField.setEnabled(false);
            submitButton.setEnabled(false);
        } else if (message.startsWith("Game Over!")) {
            scoreTextArea.append("\n" + message + "\n");
        } else {
            scoreTextArea.append(message + "\n");
        }
        scoreTextArea.setCaretPosition(scoreTextArea.getDocument().getLength());
    }

    private void updateTimer(String timeLeft) {
        timerLabel.setText("Time Remaining: " + timeLeft + " seconds");
    }

    
 
    
    public static void  moveToNextLevel() {
    SwingUtilities.invokeLater(() -> {
        currentLevel++; // Increment the level
        if (currentLevel < imagePaths.length) {
            startLevel(currentLevel); // Load the next level
        answerField.setEnabled(true); // Enable the answer field
            submitButton.setEnabled(true); 
        } else {
            //JOptionPane.showMessageDialog(this, "Game Over! You've completed all levels.");
          //  dispose(); // Close the game frame
        }
    });
}


    /**
     * Loads the image from the specified path
     */
    private  static ImageIcon loadImageIcon(String path) {
        File file = new File(path);
        if (file.exists()) {
            return new ImageIcon(path);
        } else {
            System.err.println("Error: Image not found at path: " + path);
            return null;
        }
    }

    /**
     * Method to resize an ImageIcon to fit the desired dimensions
     */
    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        if (icon == null) {
            return null;
        }
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public void updatePlayerList(ArrayList<String> players) {
        playerListArea.setText("Current Players:\n");
        for (String player : players) {
            playerListArea.append(player + "\n");
        }
    }
 
  public void updateScores(ArrayList<String> scores) {
    SwingUtilities.invokeLater(() -> {
        System.out.println("Updating GUI with Scores: " + scores); // Debug log
        scoreTextArea.setText(""); // Clear previous scores
        scoreTextArea.append("Player Scores:\n");
        for (String score : scores) {
            if (!score.isBlank()) { // Skip empty lines
                scoreTextArea.append(score + ","); // Add each score line to the text area
            }
        }
    });
}




    
public void dispose() {
    // Stop any ongoing game timers
    if (gameTimer != null) {
        gameTimer.stop();
    }
    // Close the frame
    super.dispose();
}

    private void quitGame() {
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            client.quit();
            dispose();
        }
        
        
    }
    }