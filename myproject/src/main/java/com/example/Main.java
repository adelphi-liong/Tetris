package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InputHandler inputHandler = new InputHandler();
        ShapeFactory shapeFactory = new ShapeFactory();
        Leaderboard leaderboard = new Leaderboard();
        Board board = new Board(inputHandler, shapeFactory);
        Game game = new Game(board, leaderboard);
        Scanner scanner = new Scanner(System.in);

        game.start(scanner);

        scanner.close();
    }
}
