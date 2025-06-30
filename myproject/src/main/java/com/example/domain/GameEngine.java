package com.example.domain;

import com.example.common.GameAction;

public class GameEngine {
    public final GameStateTransitioner gameStateTransitioner;
    public final GameMechanicsManager gameMechanicsManager;
    
    public GameEngine(GameStateTransitioner gameStateTransitioner, GameMechanicsManager gameMechanicsManager) {
        this.gameStateTransitioner = gameStateTransitioner;
        this.gameMechanicsManager = gameMechanicsManager;
    }
    
    public GameState processInput(GameState currentState, GameAction action) {
        return gameStateTransitioner.processInput(currentState, action);
    }
    
    public GameState processAutoDrop(GameState currentState) {
        return gameStateTransitioner.processAutoDrop(currentState);
    }
    
    public boolean shouldAutoDrop(long lastDropTime) {
        long currentTime = gameMechanicsManager.getCurrentTime();
        return gameMechanicsManager.shouldAutoDrop(lastDropTime, currentTime);
    }
    
    public long getCurrentTime() {
        return gameMechanicsManager.getCurrentTime();
    }
} 