package com.example.domain;

public class CollisionDetector {
    
    public CollisionDetector() {
    }
    
    public boolean wouldCollide(Piece piece, Board board) {
        for (int y = 0; y < piece.shape.length; y++) {
            for (int x = 0; x < piece.shape[y].length; x++) {
                if (piece.shape[y][x] != ' ') {
                    int boardX = piece.position.x + x;
                    int boardY = piece.position.y + y;
                    
                    if (boardX < 0 || boardX >= board.dimensions.width ||
                        boardY < 0 || boardY >= board.dimensions.height) {
                        return true;
                    }
                    
                    if (board.grid[boardY][boardX] != ' ') {
                        return true;
                    }
                }
            }
        }
        return false;
    }
} 