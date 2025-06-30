package com.example;

public class ConsoleRenderer {
    
    public ConsoleRenderer() {
    }
    
    public void render(GameState gameState) {
        clearScreen();
        renderGameWithPiece(gameState);
        renderScore(gameState.score);
        if (gameState.gameOver) {
            renderGameOver();
        }
        renderControls();
    }
    
    public void renderGameWithPiece(GameState gameState) {
        char[][] displayGrid = new char[gameState.board.dimensions.height][gameState.board.dimensions.width];
        
        for (int y = 0; y < gameState.board.dimensions.height; y++) {
            for (int x = 0; x < gameState.board.dimensions.width; x++) {
                displayGrid[y][x] = gameState.board.grid[y][x];
            }
        }
        
        if (!gameState.gameOver && gameState.currentPiece != null) {
            for (int y = 0; y < gameState.currentPiece.shape.length; y++) {
                for (int x = 0; x < gameState.currentPiece.shape[y].length; x++) {
                    if (gameState.currentPiece.shape[y][x] != ' ') {
                        int boardX = gameState.currentPiece.position.x + x;
                        int boardY = gameState.currentPiece.position.y + y;
                        
                        if (boardX >= 0 && boardX < gameState.board.dimensions.width &&
                            boardY >= 0 && boardY < gameState.board.dimensions.height) {
                            displayGrid[boardY][boardX] = gameState.currentPiece.shape[y][x];
                        }
                    }
                }
            }
        }
        
        System.out.println("+" + "-".repeat(gameState.board.dimensions.width) + "+");
        for (int y = 0; y < gameState.board.dimensions.height; y++) {
            System.out.print("|");
            for (int x = 0; x < gameState.board.dimensions.width; x++) {
                System.out.print(displayGrid[y][x]);
            }
            System.out.println("|");
        }
        System.out.println("+" + "-".repeat(gameState.board.dimensions.width) + "+");
    }
    
    public void renderScore(Score score) {
        System.out.println("Score: " + score.points);
    }
    
    public void renderGameOver() {
        System.out.println("GAME OVER!");
        System.out.println("Press Q to quit");
    }
    
    public void renderControls() {
        System.out.println("Controls: A/D=Move, W=Rotate, S=Drop, Space=Hard Drop, Q=Quit");
    }
    
    public void clearScreen() {
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
} 