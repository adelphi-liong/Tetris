package com.example;

public class Board {
    public final char[][] grid;
    public final BoardDimensions dimensions;
    
    public Board(char[][] grid, BoardDimensions dimensions) {
        this.grid = grid;
        this.dimensions = dimensions;
    }
} 