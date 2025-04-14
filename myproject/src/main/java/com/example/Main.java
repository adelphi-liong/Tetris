package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        
        Scanner scanner = new Scanner(System.in);

        InputHandler inputHandler = new InputHandler();
        ShapeFactory shapeFactory = new ShapeFactory();
        Leaderboard leaderboard = new Leaderboard();
        Board board = new Board(inputHandler, shapeFactory);
        Game game = new Game(board, leaderboard, inputHandler);
        


        game.start(scanner);

        scanner.close();
    }
}
