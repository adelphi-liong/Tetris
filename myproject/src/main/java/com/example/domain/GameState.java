package com.example.domain;

public final class GameState {
    public final Board board;
    public final Piece currentPiece;
    public final Position position;
    public final int score;
    public final int level;
    public final int linesCleared;
    public final boolean gameOver;

    public GameState(Board board, Piece currentPiece, Position position, 
                     int score, int level, int linesCleared, boolean gameOver) {
        this.board = board;
        this.currentPiece = currentPiece;
        this.position = position;
        this.score = score;
        this.level = level;
        this.linesCleared = linesCleared;
        this.gameOver = gameOver;
    }

    public GameState(Board board) {
        this(board, null, null, 0, 1, 0, false);
    }

    // Builder-style methods for immutable updates
    public GameState withBoard(Board board) {
        return new GameState(board, currentPiece, position, score, level, linesCleared, gameOver);
    }

    public GameState withPiece(Piece piece, Position pos) {
        return new GameState(board, piece, pos, score, level, linesCleared, gameOver);
    }

    public GameState withPosition(Position pos) {
        return new GameState(board, currentPiece, pos, score, level, linesCleared, gameOver);
    }

    public GameState withScore(int newScore) {
        return new GameState(board, currentPiece, position, newScore, level, linesCleared, gameOver);
    }

    public GameState withLinesCleared(int newLinesCleared) {
        int newLevel = 1 + (newLinesCleared / 10);
        return new GameState(board, currentPiece, position, score, newLevel, newLinesCleared, gameOver);
    }

    public GameState withGameOver(boolean gameOver) {
        return new GameState(board, currentPiece, position, score, level, linesCleared, gameOver);
    }

    public boolean canMovePiece(Position newPos) {
        return currentPiece != null && board.canPlace(currentPiece, newPos);
    }

    public boolean canRotatePiece() {
        return currentPiece != null && board.canPlace(currentPiece.rotate(), position);
    }

    public int dropSpeed() {
        return Math.max(50, 1000 - (level * 100));
    }

    @Override
    public String toString() {
        return String.format("GameState{score=%d, level=%d, lines=%d, gameOver=%b}", 
                           score, level, linesCleared, gameOver);
    }
} 