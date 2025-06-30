package com.example;

public class TetrisApp {
    public static void main(String[] args) {
        // Configuration
        BoardDimensions dimensions = new BoardDimensions(10, 20);
        long dropIntervalMillis = 1000; // 1 second
        
        // Define piece shapes directly in main
        char[][][] pieceShapes = {
            {{'I', 'I', 'I', 'I'}}, // I piece
            {{'O', 'O'}, {'O', 'O'}}, // O piece
            {{' ', 'T', ' '}, {'T', 'T', 'T'}}, // T piece
            {{' ', 'S', 'S'}, {'S', 'S', ' '}}, // S piece
            {{'Z', 'Z', ' '}, {' ', 'Z', 'Z'}}, // Z piece
            {{'J', ' ', ' '}, {'J', 'J', 'J'}}, // J piece
            {{' ', ' ', 'L'}, {'L', 'L', 'L'}} // L piece
        };
        
        // Factory Types
        BoardGenerator boardGenerator = new BoardGenerator();
        java.util.Random random = new java.util.Random();
        PieceShapeGenerator pieceShapeGenerator = new PieceShapeGenerator(pieceShapes, random);
        
        CollisionDetector collisionDetector = new CollisionDetector();
        LineClearing lineClearing = new LineClearing();
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        BoardUpdater boardUpdater = new BoardUpdater();
        GameMechanicsManager gameMechanicsManager = new GameMechanicsManager(dropIntervalMillis);
        
        PieceMover pieceMover = new PieceMover(collisionDetector, pieceShapeGenerator);
        PieceSpawner pieceSpawner = new PieceSpawner(pieceShapeGenerator, collisionDetector);
        
        GameStateTransitioner gameStateTransitioner = new GameStateTransitioner(
            pieceMover, boardUpdater, lineClearing, scoreCalculator, pieceSpawner
        );
        
        GameEngine gameEngine = new GameEngine(gameStateTransitioner, gameMechanicsManager);
        
        // Adapters
        ConsoleRenderer consoleRenderer = new ConsoleRenderer();
        InputSource inputSource = new UserInputReader();
        GameLoop gameLoop = new GameLoop(gameEngine, consoleRenderer, inputSource);
        
        Board initialBoard = boardGenerator.createEmptyBoard(dimensions);
        Piece initialPiece = pieceSpawner.spawnRandomPiece(initialBoard);
        Score initialScore = new Score(0);
        GameState initialGameState = new GameState(initialBoard, initialPiece, initialScore, false);
        
        // Start Game
        System.out.println("Welcome to Tetris!");
        System.out.println("Press any key to start...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Continue anyway
        }
        
        gameLoop.run(initialGameState);
        
        System.out.println("Thanks for playing!");
    }
} 