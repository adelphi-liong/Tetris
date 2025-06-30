package com.example.domain;

public class Piece {
    public final char[][] shape;
    public final Position position;
    
    public Piece(char[][] shape, Position position) {
        this.shape = shape;
        this.position = position;
    }
} 