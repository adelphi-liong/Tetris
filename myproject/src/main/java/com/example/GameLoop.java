package com.example;

public class GameLoop {
    public final GameEngine gameEngine;
    public final ConsoleRenderer consoleRenderer;
    public final InputSource inputSource;
    
    public GameLoop(GameEngine gameEngine, ConsoleRenderer consoleRenderer, InputSource inputSource) {
        this.gameEngine = gameEngine;
        this.consoleRenderer = consoleRenderer;
        this.inputSource = inputSource;
    }
    
    public void run(GameState initialState) {
        GameState currentState = initialState;
        long lastDropTime = gameEngine.getCurrentTime();
        boolean running = true;
        
        consoleRenderer.render(currentState);
        
        while (running && !currentState.gameOver) {
            GameAction action = inputSource.readInput();
            
            if (action == GameAction.QUIT) {
                running = false;
                break;
            }
            
            if (action != GameAction.NO_ACTION) {
                currentState = gameEngine.processInput(currentState, action);
                consoleRenderer.render(currentState);
            }
            
            if (gameEngine.shouldAutoDrop(lastDropTime)) {
                currentState = gameEngine.processAutoDrop(currentState);
                lastDropTime = gameEngine.getCurrentTime();
                consoleRenderer.render(currentState);
            }
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        if (currentState.gameOver) {
            consoleRenderer.render(currentState);
            waitForQuit();
        }
    }
    
    public void waitForQuit() {
        while (true) {
            GameAction action = inputSource.readInput();
            if (action == GameAction.QUIT) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
} 