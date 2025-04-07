package com.example;

import java.util.Random;

public class ShapeFactory {
    char[][][] shapes = {
            { { 'O', 'O' }, { 'O', 'O' } },
            { { 'I' }, { 'I' }, { 'I' }, { 'I' } },
            { { 'T', 'T', 'T' }, { ' ', 'T', ' ' } },
            { { 'L', ' ' }, { 'L', ' ' }, { 'L', 'L' } },
            { { 'S', 'S', ' ' }, { ' ', 'S', 'S' } },
            { { 'Z', 'Z', ' ' }, { ' ', 'Z', 'Z' } }
    };

    Random random = new Random();

    public Shape createRandomShape() {
        return new Shape(shapes[random.nextInt(shapes.length)], this);
    }

    public Shape createShape(char[][] shapeArray) {
        return new Shape(shapeArray, this);
    }
}
