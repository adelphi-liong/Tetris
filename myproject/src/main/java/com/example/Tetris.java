package com.example;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Tetris {
    private static final int WIDTH = 12;
    private static final int HEIGHT = 22;
    private static AtomicInteger userInput = new AtomicInteger(-1); // Store user input
    private static int score = 0;
    private static int level = 0;
    private static int defaultSpeed = 600;
    private static int speed = 0;

    // Get score
    public static int getScore()
    {
        return score;
    }

    // To add score
    public static void gainScore()
    {
        score++;
    }

    // Get level
    public static  int getLevel()
    {
        return level;
    }

    // To add level/difficulty
    public static void gainLevel()
    {
        level++;
    }

    // Get speed
    public static int getSpeed()
    {
        changeSpeed();
        return speed;
    }

    // Change speed
    public static void changeSpeed()
    {
        speed = defaultSpeed-(level * 25);
    }

    public static void main(String[] args) throws InterruptedException {
        char[][] board = new char[HEIGHT][WIDTH];

        // Initialize board with borders
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) {
                    board[i][j] = '#';
                } else {
                    board[i][j] = ' ';
                }
            }
        }

        // Start the input handler in a separate thread
        Thread inputThread = new Thread(new InputHandler());
        inputThread.start();

        // Continuously drop random shapes until the game ends
        while (true) {
            if (!dropRandomShape(board)) {
                break; // Game ends if a shape reaches the top
            }
        }
    }

    public static boolean dropRandomShape(char[][] board) throws InterruptedException {
        char[][] shape = getRandomShape();
        int x = 5;
        int y = 1;

        // Check if the shape immediately collides with the top of the board
        if (!canMoveDown(board, shape, x, y)) {
            return false; // Game over
        }

        while (true) {
            // Read user input
            int input = userInput.getAndSet(-1); // Reset input after reading
            if (input == 4 && canMove(board, shape, x - 1, y)) { // Move Left
                x--;
            } else if (input == 6 && canMove(board, shape, x + 1, y)) { // Move Right
                x++;
            } else if (input == 5 && canMoveDown(board, shape, x, y - 1)) { // Move Down
                y++;
            }

            // If shape can't move down anymore, place it
            if (!canMoveDown(board, shape, x, y)) {
                placeShape(board, shape, x, y);
                break;
            }

            y++; // Move down
            printBoard(board, shape, x, y);
            Thread.sleep(getSpeed()); // Normal falling speed
        }
        return true;
    }

    public static boolean canMove(char[][] board, char[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != ' ' && board[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean canMoveDown(char[][] board, char[][] shape, int x, int y) {
        return canMove(board, shape, x, y + 1);
    }

    public static void clearFullLines(char[][] board) {
        for (int i = HEIGHT - 2; i > 0; i--) { // Ignore the top and bottom borders
            boolean fullLine = true;
            
            for (int j = 1; j < WIDTH - 1; j++) { // Ignore left and right borders
                if (board[i][j] == ' ') {
                    fullLine = false;
                    break;
                }
            }
    
            if (fullLine) {
                // Shift all rows above down
                for (int k = i; k > 1; k--) {
                    System.arraycopy(board[k - 1], 1, board[k], 1, WIDTH - 2);
                }
                
                // Clear the top row after shifting
                for (int j = 1; j < WIDTH - 1; j++) {
                    board[1][j] = ' ';
                }
                
                // Increase score for each cleared line
                gainScore();
                gainLevel();
                
                // Recheck the same row since it now contains the row above
                i++;
            }
        }
    }
    
    public static void placeShape(char[][] board, char[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != ' ') {
                    board[y + i][x + j] = shape[i][j];
                }
            }
        }
        
        // Check and clear full lines
        clearFullLines(board);
    
        printBoard(board, null, -1, -1);
    }
    
    
    public static void printBoard(char[][] board, char[][] shape, int x, int y) {
        System.out.print("\033[H\033[2J"); // Clear console (ANSI escape sequence)
        System.out.flush();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boolean shapePrinted = false;
                if (shape != null && i >= y && i < y + shape.length && j >= x && j < x + shape[0].length) {
                    if (shape[i - y][j - x] != ' ') {
                        System.out.print(shape[i - y][j - x]);
                        shapePrinted = true;
                    }
                }
                if (!shapePrinted) {
                    System.out.print(board[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Score: " + getScore());
        System.out.println("Level: " + getLevel());
        System.out.println("Speed: " + getSpeed());
    }

    public static char[][] getRandomShape() {
        Random random = new Random();
        char[][][] shapes = { getSquareShape(), getIShape(), getTShape(), getLShape(), getSShape(), getZShape() };
        return shapes[random.nextInt(shapes.length)];
    }

    public static char[][] getSquareShape() {
        return new char[][] { { 'O', 'O' }, { 'O', 'O' } };
    }

    public static char[][] getIShape() {
        return new char[][] { { 'I' }, { 'I' }, { 'I' }, { 'I' } };
    }

    public static char[][] getTShape() {
        return new char[][] { { 'T', 'T', 'T' }, { ' ', 'T', ' ' }, { ' ', 'T', ' ' } };
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

    // Thread to handle user input
    static class InputHandler implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int key = System.in.read();
                    if (key == '4' || key == '6' || key == '5') {
                        userInput.set(key - '0'); // Store input
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
