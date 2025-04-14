package com.example;

public class Board {
    final int WIDTH = 12;
    final int HEIGHT = 12;
    char[][] board;
    InputHandler inputHandler;
    int score = 0;
    ShapeFactory factory;

    public Board(InputHandler inputHandler, ShapeFactory factory) {
        this.board = new char[HEIGHT][WIDTH];
        this.inputHandler = inputHandler;
        this.factory = factory;
        initializeBoard();
    }

    void initializeBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) ? '#' : ' ';
            }
        }
    }

    public boolean dropRandomShape(Shape shape) throws InterruptedException {
        int x = 5, y = 1;

        if (!canMoveDown(shape, x, y)) {
            return false;
        }

        while (true) {
            int input = inputHandler.getUserInput();
            if (input == 4 && canMove(shape, x - 1, y))
                x--;
            else if (input == 6 && canMove(shape, x + 1, y))
                x++;
            else if (input == 5 && canMoveDown(shape, x, y - 1))
                y++;
            else if (input == 8) {
                Shape rotatedShape = shape.rotateClockwise();
                if (canMove(rotatedShape, x, y))
                    shape = rotatedShape;
            }

            if (!canMoveDown(shape, x, y)) {
                placeShape(shape, x, y);
                break;
            }

            y++;
            printBoard(shape, x, y);
            Thread.sleep(600);
        }

        return true;
    }

    boolean canMove(Shape shape, int x, int y) {
        for (int i = 0; i < shape.getShape().length; i++) {
            for (int j = 0; j < shape.getShape()[i].length; j++) {
                if (shape.getShape()[i][j] != ' ' && board[y + i][x + j] != ' ')
                    return false;
            }
        }
        return true;
    }

    boolean canMoveDown(Shape shape, int x, int y) {
        return canMove(shape, x, y + 1);
    }

    void placeShape(Shape shape, int x, int y) {
        for (int i = 0; i < shape.getShape().length; i++) {
            for (int j = 0; j < shape.getShape()[i].length; j++) {
                if (shape.getShape()[i][j] != ' ') {
                    board[y + i][x + j] = shape.getShape()[i][j];
                }
            }
        }
        clearFullLines();
        printBoard(null, -1, -1);
    }

    void clearFullLines() {
        for (int i = HEIGHT - 2; i > 0; i--) {
            boolean fullLine = true;
            for (int j = 1; j < WIDTH - 1; j++) {
                if (board[i][j] == ' ') {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int k = i; k > 1; k--) {
                    System.arraycopy(board[k - 1], 1, board[k], 1, WIDTH - 2);
                }
                for (int j = 1; j < WIDTH - 1; j++)
                    board[1][j] = ' ';
                score++;
            }
        }
    }

    void printBoard(Shape shape, int x, int y) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boolean shapePrinted = false;
                if (shape != null && i >= y && i < y + shape.getShape().length && j >= x
                        && j < x + shape.getShape()[0].length) {
                    if (shape.getShape()[i - y][j - x] != ' ') {
                        System.out.print(shape.getShape()[i - y][j - x]);
                        shapePrinted = true;
                    }
                }
                if (!shapePrinted)
                    System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("Score: " + score);
    }
}