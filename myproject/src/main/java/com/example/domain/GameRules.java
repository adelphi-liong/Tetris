package com.example.domain;

public interface GameRules {
    int calculateScore(int linesCleared, int level);
    Position spawnPosition(Board board, Piece piece);
    boolean isGameOver(GameState state);
} 