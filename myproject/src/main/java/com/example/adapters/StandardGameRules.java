package com.example.adapters;

import com.example.domain.GameRules;
import com.example.domain.GameState;
import com.example.domain.Board;
import com.example.domain.Piece;
import com.example.domain.Position;

public final class StandardGameRules implements GameRules {

    @Override
    public int calculateScore(int linesCleared, int level) {
        int baseScore = 0;
        switch (linesCleared) {
            case 1: baseScore = 100; break;
            case 2: baseScore = 300; break;
            case 3: baseScore = 500; break;
            case 4: baseScore = 800; break; // Tetris!
        }
        return baseScore * level;
    }

    @Override
    public Position spawnPosition(Board board, Piece piece) {
        return new Position(board.width / 2 - piece.size() / 2, 0);
    }

    @Override
    public boolean isGameOver(GameState state) {
        return state.gameOver;
    }
} 