package com.example;

import java.util.Random;

public class PieceShapeGenerator {
    public final char[][][] pieces;
    public final Random random;
    
    public PieceShapeGenerator(char[][][] pieces, Random random) {
        this.pieces = pieces;
        this.random = random;
    }
    
    public char[][] generateRandomPiece() {
        int randomIndex = random.nextInt(pieces.length);
        return pieces[randomIndex];
    }
    
    public char[][] rotatePiece(char[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        char[][] rotated = new char[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        
        return rotated;
    }
} 