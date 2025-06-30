package com.example.domain;

public interface GameRenderer {
    void render(GameState state);
    void showGameOver(int finalScore);
} 