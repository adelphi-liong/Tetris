package com.example;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Tetris {
    private static final int WIDTH = 12;
    private static final int HEIGHT = 22;
    private static AtomicInteger userInput = new AtomicInteger(-1);
    private static int score = 0;
    private static int level = 0;
    private static int defaultSpeed = 600;
    private static int speed = 0;

    public static int getScore() { 
        return score; 
    }
    public static void gainScore() { 
        score++; 
    }
    public static int getLevel() { 
        return level; }
    public static void gainLevel() { 
        level++; 
    }
    public static int getSpeed() { 
        changeSpeed(); 
        return speed; 
    }
    public static void changeSpeed() {
        speed = defaultSpeed - (level * 25); 
    }

    public static void main(String[] args) throws InterruptedException {
        char[][] board = new char[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) ? '#' : ' ';
            }
        }

        Thread inputThread = new Thread(new InputHandler());
        inputThread.start();

        while (true) {
            if (!dropRandomShape(board)) break;
        }
    }

    public static boolean dropRandomShape(char[][] board) throws InterruptedException {
        char[][] shape = getRandomShape();
        int x = 5, y = 1;

        if (!canMoveDown(board, shape, x, y)) return false;

        while (true) {
            int input = userInput.getAndSet(-1);
            if (input == 4 && canMove(board, shape, x - 1, y)) x--;
            else if (input == 6 && canMove(board, shape, x + 1, y)) x++;
            else if (input == 5 && canMoveDown(board, shape, x, y - 1)) y++;
            else if (input == 8) {
                char[][] rotatedShape = rotateShapeClockwise(shape);
                if (canMove(board, rotatedShape, x, y)) shape = rotatedShape;
            }

            if (!canMoveDown(board, shape, x, y)) {
                placeShape(board, shape, x, y);
                break;
            }
            y++;
            printBoard(board, shape, x, y);
            Thread.sleep(getSpeed());
        }
        return true;
    }

    public static boolean canMove(char[][] board, char[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != ' ' && board[y + i][x + j] != ' ') return false;
            }
        }
        return true;
    }

    public static boolean canMoveDown(char[][] board, char[][] shape, int x, int y) {
        return canMove(board, shape, x, y + 1);
    }

    public static void placeShape(char[][] board, char[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != ' ') board[y + i][x + j] = shape[i][j];
            }
        }
        clearFullLines(board);
        printBoard(board, null, -1, -1);
    }

    public static void clearFullLines(char[][] board) {
        for (int i = HEIGHT - 2; i > 0; i--) {
            boolean fullLine = true;
            for (int j = 1; j < WIDTH - 1; j++) {
                if (board[i][j] == ' ') { fullLine = false; break; }
            }
            if (fullLine) {
                for (int k = i; k > 1; k--) {
                    System.arraycopy(board[k - 1], 1, board[k], 1, WIDTH - 2);
                }
                for (int j = 1; j < WIDTH - 1; j++) board[1][j] = ' ';
                gainScore(); gainLevel();
                i++;
            }
        }
    }

    public static void printBoard(char[][] board, char[][] shape, int x, int y) {
        System.out.print("\033[H\033[2J"); System.out.flush();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boolean shapePrinted = false;
                if (shape != null && i >= y && i < y + shape.length && j >= x && j < x + shape[0].length) {
                    if (shape[i - y][j - x] != ' ') {
                        System.out.print(shape[i - y][j - x]);
                        shapePrinted = true;
                    }
                }
                if (!shapePrinted) System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("Score: " + getScore() + " Level: " + getLevel() + " Speed: " + getSpeed());
    }

    public static char[][] rotateShapeClockwise(char[][] shape) {
        int rows = shape.length, cols = shape[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }

    public static char[][] getRandomShape() {
        char[][][] shapes = { getSquareShape(), getIShape(), getTShape(), getLShape(), getSShape(), getZShape() };
        return shapes[new Random().nextInt(shapes.length)];
    }

    public static char[][] getSquareShape() {
        return new char[][] { { 'O', 'O' }, { 'O', 'O' } }; 
    }
    public static char[][] getIShape() { 
        return new char[][] { { 'I' }, { 'I' }, { 'I' }, { 'I' } }; 
    }
    public static char[][] getTShape() {
        return new char[][] { { 'T', 'T', 'T' }, { ' ', 'T', ' ' } }; 
    }
    public static char[][] getLShape() { 
        return new char[][] { { 'L', ' ' }, { 'L', ' ' }, { 'L', 'L' } }; 
    }
    public static char[][] getSShape() {
         return new char[][] { { 'S', 'S', ' ' }, { ' ', 'S', 'S' } }; 
    }
    public static char[][] getZShape() { 
        return new char[][] { { 'Z', 'Z', ' ' }, { ' ', 'Z', 'Z' } }; 
    }

    static class InputHandler implements Runnable {
        @Override
        public void run() {
            try { 
                while (true) { 
                    int key = System.in.read(); if (key == '4' || key == '6' || key == '5' || key == '8') userInput.set(key - '0'); 
                } } catch (IOException e) { 
                    e.printStackTrace(); 
                }
        }
    }
}
