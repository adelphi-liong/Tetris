package com.example.domain;

public final class GameEngine {
    public final PieceGenerator pieceGenerator;
    public final GameRules rules;

    public GameEngine(PieceGenerator pieceGenerator, GameRules rules) {
        this.pieceGenerator = pieceGenerator;
        this.rules = rules;
    }

    public GameState processInput(GameState state, InputCommand command) {
        // If no current piece, spawn one
        if (state.currentPiece == null && !state.gameOver) {
            state = spawnPiece(state);
        }

        if (state.gameOver) return state;

        switch (command) {
            case MOVE_LEFT:
                return tryMove(state, state.position.left());
            case MOVE_RIGHT:
                return tryMove(state, state.position.right());
            case MOVE_DOWN:
                return tryMove(state, state.position.down());
            case ROTATE:
                return tryRotate(state);
            case DROP:
                return dropPiece(state);
            case QUIT:
                return state.withGameOver(true);
            default:
                return state;
        }
    }

    public GameState tryMove(GameState state, Position newPos) {
        if (state.canMovePiece(newPos)) {
            return state.withPosition(newPos);
        } else if (newPos.y > state.position.y) {
            // Piece hit bottom, place it
            return placePiece(state);
        }
        return state;
    }

    public GameState tryRotate(GameState state) {
        if (state.canRotatePiece()) {
            return state.withPiece(state.currentPiece.rotate(), state.position);
        }
        return state;
    }

    public GameState dropPiece(GameState state) {
        Position pos = state.position;
        while (state.canMovePiece(pos.down())) {
            pos = pos.down();
        }
        return placePiece(state.withPosition(pos));
    }

    public GameState placePiece(GameState state) {
        Board newBoard = state.board.place(state.currentPiece, state.position);
        int linesCleared = newBoard.countFullLines();
        newBoard = newBoard.clearFullLines();
        
        int scoreIncrease = rules.calculateScore(linesCleared, state.level);
        
        GameState newState = state
            .withBoard(newBoard)
            .withPiece(null, null)
            .withScore(state.score + scoreIncrease)
            .withLinesCleared(state.linesCleared + linesCleared);

        return spawnPiece(newState);
    }

    public GameState spawnPiece(GameState state) {
        Piece newPiece = pieceGenerator.next();
        Position spawnPos = rules.spawnPosition(state.board, newPiece);
        
        if (!state.board.canPlace(newPiece, spawnPos)) {
            return state.withGameOver(true);
        }
        
        return state.withPiece(newPiece, spawnPos);
    }
} 