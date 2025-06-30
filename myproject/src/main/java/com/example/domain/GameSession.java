package com.example.domain;

public final class GameSession {
    public final GameEngine engine;
    public final InputSource input;
    public final GameRenderer renderer;
    public final long frameDelayMs;
    public GameState state;
    public long lastDropTime;

    public GameSession(GameEngine engine, InputSource input, GameRenderer renderer, GameState initialState, long frameDelayMs) {
        this.engine = engine;
        this.input = input;
        this.renderer = renderer;
        this.state = initialState;
        this.frameDelayMs = frameDelayMs;
        this.lastDropTime = System.currentTimeMillis();
    }

    public void run() {
        while (!state.gameOver && !input.shouldQuit()) {
            long currentTime = System.currentTimeMillis();
            
            // Process all available input
            InputCommand command;
            while ((command = input.getInput()) != null) {
                state = engine.processInput(state, command);
            }

            // Auto-drop pieces based on time
            long timeSinceLastDrop = currentTime - lastDropTime;
            if (timeSinceLastDrop >= state.dropSpeed()) {
                state = engine.processInput(state, InputCommand.MOVE_DOWN);
                lastDropTime = currentTime;
            }

            // Render current state
            renderer.render(state);

            // Frame rate control
            try {
                Thread.sleep(frameDelayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        renderer.showGameOver(state.score);
    }

    public GameSession step() {
        long currentTime = System.currentTimeMillis();
        
        // Process all available input
        InputCommand command;
        while ((command = input.getInput()) != null) {
            state = engine.processInput(state, command);
        }

        // Auto-drop pieces based on time
        long timeSinceLastDrop = currentTime - lastDropTime;
        if (timeSinceLastDrop >= state.dropSpeed()) {
            state = engine.processInput(state, InputCommand.MOVE_DOWN);
            lastDropTime = currentTime;
        }

        return this;
    }
} 