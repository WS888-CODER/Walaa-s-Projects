package network_phase2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class Client implements Runnable {
   
    private ArrayList<Client> clients;
    //private ArrayList<GameRoom> gameRooms;
    private String username;
    private boolean inGameRoom = false;
    private int score = 0; // New field to track the player's score
    private static final int MAX_PLAYERS_IN_ROOM = 3;
    private int currentStage = 0;
    private int currentRoomId = -1; // -1 indicates not in any room
    
      private static final String SERVER_IP = "localhost";  // Replace with your IP
    private static final int SERVER_PORT = 9090;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private MainGameFrame gui;  // Reference to the waiting room GUI
    private GameFrame playRoomGUI;    // Reference to the play room GUI
    private String lastResponse;

public void setCurrentRoomId(int roomId) {
    this.currentRoomId = roomId;
}

public int getCurrentRoomId() {
    return currentRoomId;
}


    @Override
    public void run() {}
    
    public Client(MainGameFrame gui) throws IOException {
        try {
            this.socket = new Socket(SERVER_IP, SERVER_PORT);  // Connect to your server
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.gui = gui;  // Assign the GUI object to be able to update the messagesArea
        } catch (IOException e) {
            gui.appendMessage("Error: Could not connect to server.");
        }
    }
    
     public void sendUsername(String username) {
        out.println(username);  // Send username to server
    }
     
     public void sendPlayRequest() {
        out.println("play");  // Send "play" request to the server
        // Wait for a response from the server
       
    }
     
     public String getPlayResponse() {
        return lastResponse; // Return the last response received from the server
    }

    public void sendMessage(String message) {
        out.println(message);  // Send chat message (or answer) to the server
    }

    public void quit() {
        out.println("quit");  // Notify the server that the player is quitting
        close();  // Close the connection
    }
    
   public void listenToServer() {
    new Thread(() -> {
        try {
            String response;
            while ((response = in.readLine()) != null) {
     if (response.startsWith("scores:")) {
    System.out.println("Received Scores Response: " + response); // Debug log
    String[] scoresArray = response.replace("scores:", "").trim().split("\n");
    // Remove any extra spaces around each score
    for (int i = 0; i < scoresArray.length; i++) {
        scoresArray[i] = scoresArray[i].trim();
    }
    playRoomGUI.updateScores(new ArrayList<>(List.of(scoresArray))); // Update GUI
}


else if (response.startsWith("players:")) {
                    String[] players = response.replace("players:", "").split(",");
                    gui.updatePlayerList(new ArrayList<>(List.of(players)));
                } else if (response.startsWith("timer:")) {
                    gui.updateTimer(response.replace("timer:", "").trim());
                } else if (response.startsWith("30 seconds are up.")) {
                    gui.appendMessage("Starting the game...");
                    gui.openPlayRoom();
                } else if (response.startsWith("nextLevel:")) {
                    // Notify players about correct answer
                    playRoomGUI.moveToNextLevel(); // Move all players to the next level
                } else if (response.equals("closeGameFrame")) {
                    playRoomGUI.dispose(); // Close the game frame
                } else if (response.startsWith("showRankings:")) {
                    showRankingFrame(response.replace("showRankings:", ""));}
                    else {
                    gui.appendMessage(response);}
            }
        } catch (IOException e) {
            gui.appendMessage("Connection lost.");
        }
    }).start();
}



private void showRankingFrame(String rankingsData) {
    SwingUtilities.invokeLater(() -> {
        if (playRoomGUI != null) {
            playRoomGUI.dispose(); // Close the levels frame
        }

        JFrame rankingFrame = new JFrame("Game Over - Rankings");
        rankingFrame.setSize(1400, 900);
        rankingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rankingFrame.setLayout(null);
        rankingFrame.getContentPane().setBackground(Color.decode("#002424"));

        // Title Label
        JLabel titleLabel = new JLabel("Game Rankings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(500, 10, 400, 30);
        rankingFrame.add(titleLabel);

        // Load and add the stage photo
        String imagePath = "C:\\Users\\saifw\\OneDrive\\Desktop\\Projects\\IT328 Network Project\\NetWork_lastEdit\\Network_Phase2\\Network_Phase2\\src\\images\\stage2.jpg"; // Adjust path
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(1200, 400, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(100, 300, 1200, 400); // Position the photo
        rankingFrame.add(imageLabel);

        // Create and configure three text areas for rankings (only for 3 players)
        JTextArea textArea1 = new JTextArea();
        JTextArea textArea2 = new JTextArea();
        JTextArea textArea3 = new JTextArea();

        JTextArea[] textAreas = {textArea1, textArea2, textArea3}; // Only 3 text areas
        int[][] positions = {
            {640, 200, 150, 50}, // 1st place
            {910, 250, 150, 50}, // 2nd place
            {300, 250, 150, 50}  // 3rd place
        };

        for (int i = 0; i < textAreas.length; i++) {
            JTextArea textArea = textAreas[i];
            textArea.setBackground(Color.decode("#002424"));
            textArea.setForeground(Color.WHITE);
            textArea.setFont(new Font("SansSerif", Font.BOLD, 16));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            rankingFrame.add(textArea);
            textArea.setBounds(positions[i][0], positions[i][1], positions[i][2], positions[i][3]);
        }

        // Populate rankings into text areas
        String[] playersData = rankingsData.split(";");
        for (int i = 0; i < playersData.length; i++) {
            if (i < textAreas.length) { // Limit to 3 players
                String[] playerInfo = playersData[i].split(",");
                textAreas[i].setText(playerInfo[0] + ". " + playerInfo[1] + "\n" + playerInfo[2] + " points");
            }
        }

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(650, 750, 100, 30);
        closeButton.addActionListener(e -> rankingFrame.dispose());
        rankingFrame.add(closeButton);

        rankingFrame.setLocationRelativeTo(null);
        rankingFrame.setVisible(true);
    });
}

    public void setPlayRoomGUI(GameFrame playRoomGUI) {
        this.playRoomGUI = playRoomGUI;  // Set the play room GUI when the player enters the play room
         out.println("requestPlayerList");  // Send a request to get the player list
         out.println("requestScores");    
    }
    
     private void updatePlayRoomPlayerList(String response) {
        String[] players = response.replace("players:", "").split(",");
        playRoomGUI.updatePlayerList(new ArrayList<>(List.of(players)));
    }

  private void updatePlayRoomScores(String response) {
    String[] scores = response.replace("scores:", "").trim().split(","); // Replace "scores:" and split by comma
    playRoomGUI.updateScores(new ArrayList<>(List.of(scores))); // Pass scores to GUI
}




    public void close() {
    try {
        // Notify the server before closing
        if (out != null) {
            out.println("quit");
        }
        // Close the streams and socket
        in.close();
        socket.close();
    } catch (IOException e) {
        gui.appendMessage("Error closing connection.");
    }
}

    
}