package com.example;

public class GameMechanicsManager {
    public final long dropIntervalMillis;
    
    public GameMechanicsManager(long dropIntervalMillis) {
        this.dropIntervalMillis = dropIntervalMillis;
    }
    
    public boolean shouldAutoDrop(long lastDropTime, long currentTime) {
        return currentTime - lastDropTime >= dropIntervalMillis;
    }
    
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
} 