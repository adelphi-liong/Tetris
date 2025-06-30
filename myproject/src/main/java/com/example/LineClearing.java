package com.example;

public class LineClearing {
    
    public LineClearing() {
    }
    
    public Board clearLines(Board board) {
        char[][] newGrid = new char[board.dimensions.height][board.dimensions.width];
        int newRowIndex = board.dimensions.height - 1;
        
        for (int y = board.dimensions.height - 1; y >= 0; y--) {
            if (!isLineFull(board, y)) {
                for (int x = 0; x < board.dimensions.width; x++) {
                    newGrid[newRowIndex][x] = board.grid[y][x];
                }
                newRowIndex--;
            }
        }
        
        while (newRowIndex >= 0) {
            for (int x = 0; x < board.dimensions.width; x++) {
                newGrid[newRowIndex][x] = ' ';
            }
            newRowIndex--;
        }
        
        return new Board(newGrid, board.dimensions);
    }
    
    public int countFullLines(Board board) {
        int count = 0;
        for (int y = 0; y < board.dimensions.height; y++) {
            if (isLineFull(board, y)) {
                count++;
            }
        }
        return count;
    }
    
    public boolean isLineFull(Board board, int row) {
        for (int x = 0; x < board.dimensions.width; x++) {
            if (board.grid[row][x] == ' ') {
                return false;
            }
        }
        return true;
    }
} 