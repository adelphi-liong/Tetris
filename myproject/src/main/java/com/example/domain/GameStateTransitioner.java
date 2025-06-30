package com.example.domain;

import com.example.common.GameAction;

public class GameStateTransitioner {
    public final PieceMover pieceMover;
    public final BoardUpdater boardUpdater;
    public final LineClearing lineClearing;
    public final ScoreCalculator scoreCalculator;
    public final PieceSpawner pieceSpawner;
    
    public GameStateTransitioner(PieceMover pieceMover, BoardUpdater boardUpdater, 
                               LineClearing lineClearing, ScoreCalculator scoreCalculator, 
                               PieceSpawner pieceSpawner) {
        this.pieceMover = pieceMover;
        this.boardUpdater = boardUpdater;
        this.lineClearing = lineClearing;
        this.scoreCalculator = scoreCalculator;
        this.pieceSpawner = pieceSpawner;
    }
    
    public GameState processInput(GameState currentState, GameAction action) {
        if (currentState.gameOver) {
            return currentState;
        }
        
        Piece newPiece = currentState.currentPiece;
        
        switch (action) {
            case MOVE_LEFT:
                newPiece = pieceMover.moveLeft(currentState.currentPiece, currentState.board);
                break;
            case MOVE_RIGHT:
                newPiece = pieceMover.moveRight(currentState.currentPiece, currentState.board);
                break;
            case MOVE_DOWN:
                newPiece = pieceMover.moveDown(currentState.currentPiece, currentState.board);
                break;
            case ROTATE:
                newPiece = pieceMover.rotate(currentState.currentPiece, currentState.board);
                break;
            case HARD_DROP:
                newPiece = pieceMover.hardDrop(currentState.currentPiece, currentState.board);
                return placePieceAndSpawnNext(currentState, newPiece);
            case NO_ACTION:
            case QUIT:
                // No action needed, return current state
                break;
        }
        
        return new GameState(currentState.board, newPiece, currentState.score, currentState.gameOver);
    }
    
    public GameState processAutoDrop(GameState currentState) {
        if (currentState.gameOver) {
            return currentState;
        }
        
        Piece droppedPiece = pieceMover.moveDown(currentState.currentPiece, currentState.board);
        
        if (droppedPiece.equals(currentState.currentPiece)) {
            return placePieceAndSpawnNext(currentState, currentState.currentPiece);
        }
        
        return new GameState(currentState.board, droppedPiece, currentState.score, currentState.gameOver);
    }
    
    public GameState placePieceAndSpawnNext(GameState currentState, Piece piece) {
        Board boardWithPiece = boardUpdater.placePiece(currentState.board, piece);
        int linesCleared = lineClearing.countFullLines(boardWithPiece);
        Board boardAfterClear = lineClearing.clearLines(boardWithPiece);
        Score newScore = scoreCalculator.calculateScore(currentState.score, linesCleared);
        
        Piece nextPiece = pieceSpawner.spawnRandomPiece(boardAfterClear);
        boolean gameOver = !pieceSpawner.canSpawnPiece(nextPiece, boardAfterClear);
        
        return new GameState(boardAfterClear, nextPiece, newScore, gameOver);
    }
} 