package com.example;

import java.util.Random;

public class App {
    public static void main(String[] args) throws InterruptedException {
        int width = 12;
        int height = 22;
        char[][] board = new char[height][width];

        // Initialize board with borders
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    board[i][j] = '#';
                } else {
                    board[i][j] = ' ';
                }
            }
        }

        // Continuously drop random shapes until the game ends
        while (true) {
            if (!dropRandomShape(board)) {
                break; // Game ends if a shape reaches the top
            }
        }
    }

    public static boolean dropRandomShape(char[][] board) throws InterruptedException {
        char[][] shape = getRandomShape();
        int x = 5; // Starting X position (column)
        int y = 1; // Starting Y position (row)

        // Check if the shape immediately collides with the top of the board
        if (!canMoveDown(board, shape, x, y)) {
            return false; // The game ends if the shape cannot move down
        }

        while (true) {
            if (!canMoveDown(board, shape, x, y)) {
                placeShape(board, shape, x, y);
                break; // Stop the current shape
            }
            y++; // Move shape down
            printBoard(board, shape, x, y);
            Thread.sleep(500); // Delay for visualization
        }
        return true; // Shape successfully placed, return true for continuing the game
    }

    public static boolean canMoveDown(char[][] board, char[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != ' ' && (y + i + 1 >= board.length || board[y + i + 1][x + j] != ' ')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeShape(char[][] board, char[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != ' ') {
                    board[y + i][x + j] = shape[i][j];
                }
            }
        }
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
    }

    public static char[][] getRandomShape() {
        Random random = new Random();
        char[][][] shapes = { getSquareShape(), getIShape(), getTShape(), getLShape(), getSShape(), getZShape() };
        return shapes[random.nextInt(shapes.length)];
    }

    // Define Tetris shapes
    public static char[][] getSquareShape() {
        return new char[][] {
                { 'O', 'O' },
                { 'O', 'O' }
        };
    }

    public static char[][] getIShape() {
        return new char[][] {
                { 'I' },
                { 'I' },
                { 'I' },
                { 'I' }
        };
    }

    public static char[][] getTShape() {
        return new char[][] {
                { 'T', 'T', 'T' },
                { ' ', 'T', ' ' },
                { ' ', 'T', ' ' }
        };
    }

    public static char[][] getLShape() {
        return new char[][] {
                { 'L', ' ' },
                { 'L', ' ' },
                { 'L', 'L' }
        };
    }

    public static char[][] getSShape() {
        return new char[][] {
                { 'S', 'S', ' ' },
                { ' ', 'S', 'S' }
        };
    }

    public static char[][] getZShape() {
        return new char[][] {
                { 'Z', 'Z', ' ' },
                { ' ', 'Z', 'Z' }
        };
    }
}
