package com.example;

public class Shape {
    char[][] shape;
    ShapeFactory factory;

    public Shape(char[][] shape, ShapeFactory factory) {
        this.shape = shape;
        this.factory = factory;
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
        return factory.createShape(rotated);
    }
}
