package com.example.adapters;

import com.example.domain.PieceGenerator;
import com.example.domain.Piece;
import com.example.domain.Piece.PieceType;
import java.util.Random;

public final class RandomPieceGenerator implements PieceGenerator {
    public final Random random;
    public final char[][][] shapes;

    public RandomPieceGenerator(Random random, char[][][] shapes) {
        this.random = random;
        this.shapes = shapes;
    }

    @Override
    public Piece next() {
        int index = random.nextInt(shapes.length);
        PieceType type = PieceType.values()[index];
        return new Piece(shapes[index], type);
    }

    // Factory method for standard shapes
    public static RandomPieceGenerator withStandardShapes(Random random) {
        char[][][] standardShapes = {
            // I piece
            {{' ', 'I', ' ', ' '},
             {' ', 'I', ' ', ' '},
             {' ', 'I', ' ', ' '},
             {' ', 'I', ' ', ' '}},
            
            // O piece
            {{'O', 'O'},
             {'O', 'O'}},
            
            // T piece
            {{' ', 'T', ' '},
             {'T', 'T', 'T'},
             {' ', ' ', ' '}},
            
            // S piece
            {{' ', 'S', 'S'},
             {'S', 'S', ' '},
             {' ', ' ', ' '}},
            
            // Z piece
            {{'Z', 'Z', ' '},
             {' ', 'Z', 'Z'},
             {' ', ' ', ' '}},
            
            // J piece
            {{'J', ' ', ' '},
             {'J', 'J', 'J'},
             {' ', ' ', ' '}},
            
            // L piece
            {{' ', ' ', 'L'},
             {'L', 'L', 'L'},
             {' ', ' ', ' '}}
        };
        return new RandomPieceGenerator(random, standardShapes);
    }
} 