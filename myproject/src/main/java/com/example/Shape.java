package com.example;

import java.util.Random;

public class Shape {
    private char[][] shape;

    public Shape(char[][] shape) {
        this.shape = shape;
    }

    public char[][] getShape() {
        return shape;
    }

    public Shape rotateClockwise() {
        int rows = shape.length, cols = shape[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return new Shape(rotated);
    }

    public static Shape getRandomShape() {
        char[][][] shapes = { 
            {{'O', 'O'}, {'O', 'O'}}, 
            {{'I'}, {'I'}, {'I'}, {'I'}}, 
            {{'T', 'T', 'T'}, {' ', 'T', ' '}}, 
            {{'L', ' '}, {'L', ' '}, {'L', 'L'}}, 
            {{'S', 'S', ' '}, {' ', 'S', 'S'}}, 
            {{'Z', 'Z', ' '}, {' ', 'Z', 'Z'}} 
        };
        return new Shape(shapes[new Random().nextInt(shapes.length)]);
    }
}