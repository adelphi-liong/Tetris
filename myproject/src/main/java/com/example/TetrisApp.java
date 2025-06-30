package com.example;

import com.example.domain.*;
import com.example.adapters.*;

public class TetrisApp {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         TETRIS GAME          ║");
        System.out.println("║                              ║");
        System.out.println("║  Controls:                   ║");
        System.out.println("║  A/D or ←/→ - Move           ║");
        System.out.println("║  W or ↑ - Rotate             ║");
        System.out.println("║  S or ↓ - Drop faster        ║");
        System.out.println("║  Space - Hard drop           ║");
        System.out.println("║  Q or ESC - Quit             ║");
        System.out.println("║                              ║");
        System.out.println("║   Press ENTER to start...    ║");
        System.out.println("╚══════════════════════════════╝");
        
        waitForInput();
        
        // Clear screen and hide cursor
        System.out.print("\033[2J\033[H\033[?25l");
        
        // Create dependencies
        PieceGenerator pieceGenerator = new RandomPieceGenerator();
        GameRules rules = new StandardGameRules();
        InputSource input = new KeyboardInputSource();
        GameRenderer renderer = new ConsoleRenderer();

        // Create game engine
        GameEngine engine = new GameEngine(pieceGenerator, rules);

        // Create initial game state
        Board board = new Board(10, 20);
        GameState initialState = new GameState(board);

        // Create and run game session
        GameSession session = new GameSession(engine, input, renderer, initialState);
        
        try {
            session.run();
        } finally {
            // Show cursor again
            System.out.print("\033[?25h");
        }
    }

    private static void waitForInput() {
        try {
            System.in.read();
            // Clear any remaining input
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignore
        }
    }
} 