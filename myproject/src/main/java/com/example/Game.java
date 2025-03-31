package com.example;

import java.util.Scanner;

public class Game {
    private Board board;
    private Leaderboard leaderboard;
    private int score;
    private int level;
    private boolean gameOver;

    public Game() {
        this.board = new Board();
        this.leaderboard = new Leaderboard();
        this.score = 0;
        this.level = 0;
        this.gameOver = false;
    }

    public void start() throws InterruptedException {
        // Start the game loop
        while (!gameOver) {
            leaderboard.printLeaderboard();
            if (!board.dropRandomShape()) {
                gameOver = true;
            }
        }

        // Game Over Logic
        System.out.println("Game Over! Enter your name: ");

        // Clear the input buffer (skip any remaining newline characters in the buffer)
        Scanner scanner = new Scanner(System.in);

        // Wait for user input after game over
        String name = scanner.nextLine();  // Get the entire name input

        // Add the name and score to leaderboard
        leaderboard.addEntry(name, score);  

        // Print the final leaderboard
        leaderboard.printLeaderboard();

        // Close the scanner to avoid resource leak
        scanner.close();
    }

    public int getScore() {
        return score;
    }

    public void gainScore() {
        score++;
    }

    public int getLevel() {
        return level;
    }

    public void gainLevel() {
        level++;
    }
}
