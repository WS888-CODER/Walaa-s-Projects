package network_phase2;

import java.awt.BorderLayout;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.*;

public class Server {
    private static final int PORT = 9090;
    private static final int MAX_PLAYERS = 3;
    private static final int TIMER_SECONDS = 10; // Pre-game timer shortened to 10 seconds
    private static final int GAME_DURATION_SECONDS = 1500; // Gameplay timer
    private static final String[] levelAnswers = {"I", "9E79", "9", "9F74", "3899"};
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    public static ArrayList<ClientHandler> playRoom = new ArrayList<>();
        private static Timer gameStartTimer = new Timer();
         public static int currentLevel = 0; // Player's individual level tracker


    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                synchronized (clients) {
                    clients.add(clientHandler);
                }
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    public static void broadcastToLobby(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    public static void broadcastToPlayRoom(String message) {
        synchronized (playRoom) {
            for (ClientHandler player : playRoom) {
                player.sendMessage(message);
            }
        }
    }
    
     public static void updatePlayRoomPlayerList() {
        StringBuilder playerList = new StringBuilder("players:");
        synchronized (playRoom) {
            for (ClientHandler player : playRoom) {
                playerList.append(player.getUsername()).append(",");
            }
        }
        broadcastToPlayRoom(playerList.toString());
    }

public static void updatePlayRoomScores() {
  StringBuilder scores = new StringBuilder("scores:");
synchronized (playRoom) {
    for (ClientHandler player : playRoom) {
        scores.append(player.getUsername())
              .append(": ")
              .append(player.getScore())
              .append(",");
    }
}
// Remove the trailing comma
if (scores.charAt(scores.length() - 1) == ',') {
    scores.setLength(scores.length() - 1);
}
broadcastToPlayRoom(scores.toString());

}



    
    
    
    public static void startGameTimer() {
    if (playRoom.size() > 1) { // Ensure at least 2 players to start the game
        broadcastToPlayRoom("Game will start in " + TIMER_SECONDS + " seconds!");
        Timer startTimer = new Timer();

        startTimer.scheduleAtFixedRate(new TimerTask() {
            int countdown = TIMER_SECONDS;

            @Override
            public void run() {
                if (countdown <= 0) {
                    this.cancel();
                    broadcastToPlayRoom("Game Started!");
                    broadcastToPlayRoom("startGame"); // Signal to start the game
                    startGamePlayTimer(); // Begin gameplay timer after start timer ends
                } else {
                    broadcastToPlayRoom("timer:" + countdown + " seconds remaining...");
                    countdown--;
                }
            }
        }, 0, 1000); // Run every second
    } else {
        broadcastToPlayRoom("Not enough players to start the game.");
    }
}

public static void addClient(ClientHandler clientHandler) {
    synchronized (clients) {
        clients.add(clientHandler);
        updateLobbyPlayerList(); // Update the player list for all clients
    }
}


public static void updateLobbyPlayerList() {
    StringBuilder playerList = new StringBuilder("players:");
    synchronized (clients) {
        for (ClientHandler client : clients) {
            playerList.append(client.getUsername()).append(",");
        }
    }
    broadcastToLobby(playerList.toString());
}


    public static void startGamePlayTimer() {
    Timer gamePlayTimer = new Timer();

    gamePlayTimer.scheduleAtFixedRate(new TimerTask() {
        int countdown = GAME_DURATION_SECONDS;

        @Override
        public void run() {
            if (countdown <= 0) {
                this.cancel();
                broadcastToPlayRoom("Game Over!");
                determineWinner(); // Determine winners or clarify no winners
            } if (currentLevel < levelAnswers.length) {
                startLevelTimer(); // Start a timer for the current level
                broadcastToPlayRoom("Game time left: " + countdown + " seconds...");
                countdown--;
            }
        }
    }, 0, 1000); // Run every second
}


public static void determineWinner() {
    synchronized (playRoom) {
        if (playRoom.isEmpty()) {
            broadcastToPlayRoom("Game Over! No players participated.");
            return;
        }

        ArrayList<ClientHandler> sortedPlayers = new ArrayList<>(playRoom);
        sortedPlayers.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        // Construct the ranking message
        StringBuilder rankingData = new StringBuilder("showRankings:");
        for (int i = 0; i < sortedPlayers.size(); i++) {
            ClientHandler player = sortedPlayers.get(i);
            rankingData.append((i + 1)).append(",") // Position
                       .append(player.getUsername()).append(",") // Player name
                       .append(player.getScore()).append(";");   // Player score
        }

        // Broadcast ranking message to all players
        broadcastToPlayRoom(rankingData.toString());
    }
}


private static Timer levelTimer; // Timer for each level

public static void startLevelTimer() {
    if (levelTimer != null) {
        levelTimer.cancel(); // Cancel any existing timer
    }

    levelTimer = new Timer();
    levelTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            synchronized (playRoom) {
                System.out.println("Time's up for Level " + currentLevel);
                currentLevel++; // Move to the next level

                if (currentLevel >= levelAnswers.length) {
                    System.out.println("All levels completed. Ending the game.");
                    determineWinner();
                } else {
                    broadcastToPlayRoom("Time's up! Moving to the next level.");
                    updatePlayRoomScores();
                    broadcastToPlayRoom("nextLevel:"); // Notify clients to move to the next level
                                        startLevelTimer(); // Restart the timer for the next level

                }
            }
        }
    }, 60000); // Set timer for 60 seconds
}

  public static void handleAnswer(String username, String answer) {
    synchronized (playRoom) {
        for (ClientHandler player : playRoom) {
            if (player.getUsername().equals(username)) {
                boolean correct = false;

                // Use the player's individual currentLevel
                
                System.out.println("Player " + username + " is on level " + currentLevel);

                // Validate the answer
                if (currentLevel < levelAnswers.length && answer.equalsIgnoreCase(levelAnswers[currentLevel])) {
                    correct = true;
                    System.out.println("Player " + username + " answered correctly!");
                    player.addScore(5); // Add points for correct answer
                    currentLevel++; // Move to the next level
                    startLevelTimer();
                      if (currentLevel == levelAnswers.length ) { // Last level
        System.out.println("Player " + username + " has completed all levels.");

        determineWinner();
        return;}
                } else {
                    System.out.println("Player " + username + " answered incorrectly.");
                }

                // Notify all players about the result
                if (correct) {
                    broadcastToPlayRoom("nextLevel:" + username + " answered correctly!");
                } else {
                    broadcastToPlayRoom(username + " answered incorrectly!");
                }

                // Exit loop after processing the correct player
                break;
            }
        }
    }
    updatePlayRoomScores(); // Broadcast updated scores
}



   public static void removeClient(ClientHandler clientHandler) {
    synchronized (clients) {
        clients.remove(clientHandler);
        updateLobbyPlayerList();
    }

    synchronized (playRoom) {
        if (playRoom.remove(clientHandler)) {
            broadcastToPlayRoom(clientHandler.getUsername() + " has left the play room.");
            updatePlayRoomPlayerList();
            updatePlayRoomScores();

            // If only one player is left in the playroom, end the game
            if (playRoom.size() == 1) {
                determineWinner(); // Display rankings and declare the winner
            }
        }
    }
}

        
    
private static class ClientHandler implements Runnable {
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;
        private int score;
     



        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.score = 0;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }

       public void addScore(int points) {
    System.out.println("Adding " + points + " points to " + username + ". Current score: " + score);
    score += points;
    if (score < 0) score = 0; // Prevent negative scores
    System.out.println("Updated score for " + username + ": " + score);
}



        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }

       @Override
public void run() {
    try {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        username = in.readLine(); // Receive username
        if (username == null || username.isEmpty()) {
            socket.close();
            return;
        }

        Server.updateLobbyPlayerList(); // Notify lobby

        String message;
        while ((message = in.readLine()) != null) {
            if (message.startsWith("play")) {
                moveToPlayRoom();
            } else if (message.startsWith("quit")) {
                // Check for the winner if the player quits
                synchronized (Server.playRoom) {
                    Server.removeClient(this); // Remove the player
                    if (Server.playRoom.size() == 1) {
                        Server.determineWinner(); // Declare the winner if one player is left
                    }
                }
                break;
            } else if (message.startsWith("answer:")) {
                String answer = message.replace("answer:", "").trim();
                Server.handleAnswer(username, answer); // Pass the answer to the server
            } else {
                Server.broadcastToLobby(username + ": " + message);
            }
        }
    } catch (IOException e) {
        System.err.println("Connection error with client: " + e.getMessage());
    } finally {
        Server.removeClient(this); // Ensure client is removed from both lists
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}



       private void moveToPlayRoom() {
    synchronized (playRoom) {
        if (playRoom.size() >= MAX_PLAYERS) {
            sendMessage("Play room is full. Please wait for a spot.");
        } else {
            synchronized (clients) {
                clients.remove(this);
            }
            playRoom.add(this);
            broadcastToLobby(username + " has joined the play room.");
            updatePlayRoomPlayerList();
            sendMessage("Welcome to the play room!");

            if (playRoom.size() == 2) { // Start the timer only when two players are present
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
    int countdown = TIMER_SECONDS;

    @Override
    public void run() {
        synchronized (playRoom) {
            if (countdown > 0) {
                broadcastToPlayRoom("timer:" + countdown); // Send timer updates
                countdown--;
            } else {
                this.cancel();
                broadcastToPlayRoom("30 seconds are up. Starting the game...");
                startGamePlayTimer();
            }
        }
    }
}, 0, 1000);

}

            }
        }
    }
}

    }
