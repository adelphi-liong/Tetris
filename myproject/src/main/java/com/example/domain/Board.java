package com.example.domain;

import java.util.Arrays;

public final class Board {
    public final char[][] cells;
    public final int width, height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(cells[i], ' ');
        }
    }

    private Board(char[][] cells) {
        this.cells = deepCopy(cells);
        this.height = cells.length;
        this.width = cells[0].length;
    }

    public boolean canPlace(Piece piece, Position pos) {
        for (int i = 0; i < piece.size(); i++) {
            for (int j = 0; j < piece.size(); j++) {
                if (piece.hasBlockAt(i, j)) {
                    int boardRow = pos.y + i;
                    int boardCol = pos.x + j;
                    if (boardRow >= height || boardCol < 0 || boardCol >= width || 
                        (boardRow >= 0 && cells[boardRow][boardCol] != ' ')) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Board place(Piece piece, Position pos) {
        char[][] newCells = deepCopy(cells);
        for (int i = 0; i < piece.size(); i++) {
            for (int j = 0; j < piece.size(); j++) {
                if (piece.hasBlockAt(i, j)) {
                    int boardRow = pos.y + i;
                    int boardCol = pos.x + j;
                    if (boardRow >= 0 && boardRow < height && boardCol >= 0 && boardCol < width) {
                        newCells[boardRow][boardCol] = piece.shape[i][j];
                    }
                }
            }
        }
        return new Board(newCells);
    }

    public Board clearFullLines() {
        char[][] newCells = new char[height][width];
        int newRow = height - 1;
        
        for (int row = height - 1; row >= 0; row--) {
            if (!isLineFull(row)) {
                System.arraycopy(cells[row], 0, newCells[newRow], 0, width);
                newRow--;
            }
        }
        
        // Fill remaining rows with empty cells
        for (int row = 0; row <= newRow; row++) {
            Arrays.fill(newCells[row], ' ');
        }
        
        return new Board(newCells);
    }

    public int countFullLines() {
        int count = 0;
        for (int row = 0; row < height; row++) {
            if (isLineFull(row)) count++;
        }
        return count;
    }

    private boolean isLineFull(int row) {
        for (int col = 0; col < width; col++) {
            if (cells[row][col] == ' ') return false;
        }
        return true;
    }

    private static char[][] deepCopy(char[][] original) {
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return width == board.width && height == board.height && Arrays.deepEquals(cells, board.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells) * 31 + width * 31 + height;
    }
} 