package com.example.domain;

public class PieceMover {
    public final CollisionDetector collisionDetector;
    public final PieceShapeGenerator pieceShapeGenerator;
    
    public PieceMover(CollisionDetector collisionDetector, PieceShapeGenerator pieceShapeGenerator) {
        this.collisionDetector = collisionDetector;
        this.pieceShapeGenerator = pieceShapeGenerator;
    }
    
    public Piece moveLeft(Piece piece, Board board) {
        Position newPosition = new Position(piece.position.x - 1, piece.position.y);
        Piece newPiece = new Piece(piece.shape, newPosition);
        
        if (collisionDetector.wouldCollide(newPiece, board)) {
            return piece;
        }
        return newPiece;
    }
    
    public Piece moveRight(Piece piece, Board board) {
        Position newPosition = new Position(piece.position.x + 1, piece.position.y);
        Piece newPiece = new Piece(piece.shape, newPosition);
        
        if (collisionDetector.wouldCollide(newPiece, board)) {
            return piece;
        }
        return newPiece;
    }
    
    public Piece moveDown(Piece piece, Board board) {
        Position newPosition = new Position(piece.position.x, piece.position.y + 1);
        Piece newPiece = new Piece(piece.shape, newPosition);
        
        if (collisionDetector.wouldCollide(newPiece, board)) {
            return piece;
        }
        return newPiece;
    }
    
    public Piece rotate(Piece piece, Board board) {
        char[][] rotatedShape = pieceShapeGenerator.rotatePiece(piece.shape);
        Piece rotatedPiece = new Piece(rotatedShape, piece.position);
        
        if (collisionDetector.wouldCollide(rotatedPiece, board)) {
            return piece;
        }
        return rotatedPiece;
    }
    
    public Piece hardDrop(Piece piece, Board board) {
        Piece current = piece;
        Piece next = moveDown(current, board);
        
        while (!current.equals(next)) {
            current = next;
            next = moveDown(current, board);
        }
        
        return current;
    }
    
    public boolean canMoveDown(Piece piece, Board board) {
        return !moveDown(piece, board).equals(piece);
    }
} 