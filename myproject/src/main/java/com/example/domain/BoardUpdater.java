package com.example.domain;

public class BoardUpdater {
    
    public BoardUpdater() {
    }
    
    public Board placePiece(Board board, Piece piece) {
        char[][] newGrid = new char[board.dimensions.height][board.dimensions.width];
        
        for (int y = 0; y < board.dimensions.height; y++) {
            for (int x = 0; x < board.dimensions.width; x++) {
                newGrid[y][x] = board.grid[y][x];
            }
        }
        
        for (int y = 0; y < piece.shape.length; y++) {
            for (int x = 0; x < piece.shape[y].length; x++) {
                if (piece.shape[y][x] != ' ') {
                    int boardX = piece.position.x + x;
                    int boardY = piece.position.y + y;
                    
                    if (boardX >= 0 && boardX < board.dimensions.width &&
                        boardY >= 0 && boardY < board.dimensions.height) {
                        newGrid[boardY][boardX] = piece.shape[y][x];
                    }
                }
            }
        }
        
        return new Board(newGrid, board.dimensions);
    }
} 