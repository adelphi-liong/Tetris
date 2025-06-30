package com.example.domain;

public class BoardGenerator {
    
    public BoardGenerator() {
    }
    
    public Board createEmptyBoard(BoardDimensions dimensions) {
        char[][] grid = new char[dimensions.height][dimensions.width];
        for (int y = 0; y < dimensions.height; y++) {
            for (int x = 0; x < dimensions.width; x++) {
                grid[y][x] = ' ';
            }
        }
        return new Board(grid, dimensions);
    }
} 