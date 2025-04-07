package com.example;

import java.util.Scanner;

public class Game {
    Board board;
    Leaderboard leaderboard;
    ShapeFactory factory;
    int score;
    int level;
    boolean gameOver;

    public Game(Board board, Leaderboard leaderboard) {
        this.board = board;
        this.leaderboard = leaderboard;
        this.factory = board.factory;
        this.score = 0;
        this.level = 0;
        this.gameOver = false;
    }

    public void start(Scanner scanner) throws InterruptedException {
        while (!gameOver) {
            leaderboard.printLeaderboard();
            Shape shape = factory.createRandomShape();
            if (!board.dropRandomShape(shape)) {
                gameOver = true;
            }
        }

        System.out.println("Game Over! Enter your name: ");
        String name = scanner.nextLine();

        leaderboard.addEntry(name, score);
        leaderboard.printLeaderboard();
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
