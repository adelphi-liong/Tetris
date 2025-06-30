package com.example.domain;

import java.util.Arrays;

public final class Piece {
    public final char[][] shape;
    public final PieceType type;

    public enum PieceType { I, O, T, S, Z, J, L }

    public Piece(char[][] shape, PieceType type) {
        this.shape = deepCopy(shape);
        this.type = type;
    }

    public Piece rotate() {
        int n = shape.length;
        char[][] rotated = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = shape[i][j];
            }
        }
        return new Piece(rotated, type);
    }

    public boolean hasBlockAt(int row, int col) {
        return row >= 0 && row < shape.length && 
               col >= 0 && col < shape[0].length && 
               shape[row][col] != ' ';
    }

    public int size() {
        return shape.length;
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
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return Arrays.deepEquals(shape, piece.shape) && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(shape) * 31 + type.hashCode();
    }
} 