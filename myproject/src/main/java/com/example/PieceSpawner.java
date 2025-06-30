package com.example;

public class PieceSpawner {
    public final PieceShapeGenerator pieceShapeGenerator;
    public final CollisionDetector collisionDetector;
    
    public PieceSpawner(PieceShapeGenerator pieceShapeGenerator, CollisionDetector collisionDetector) {
        this.pieceShapeGenerator = pieceShapeGenerator;
        this.collisionDetector = collisionDetector;
    }
    
    public Piece spawnRandomPiece(Board board) {
        char[][] selectedShape = pieceShapeGenerator.generateRandomPiece();
        
        int spawnX = board.dimensions.width / 2 - selectedShape[0].length / 2;
        int spawnY = 0;
        
        return new Piece(selectedShape, new Position(spawnX, spawnY));
    }
    
    public boolean canSpawnPiece(Piece piece, Board board) {
        return !collisionDetector.wouldCollide(piece, board);
    }
} 