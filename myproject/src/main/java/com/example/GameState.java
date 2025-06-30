package com.example;

public class GameState {
    public final Board board;
    public final Piece currentPiece;
    public final Score score;
    public final boolean gameOver;
    
    public GameState(Board board, Piece currentPiece, Score score, boolean gameOver) {
        this.board = board;
        this.currentPiece = currentPiece;
        this.score = score;
        this.gameOver = gameOver;
    }
} 