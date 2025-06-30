package com.example.domain;

public class ScoreCalculator {
    
    public ScoreCalculator() {
    }
    
    public Score calculateScore(Score currentScore, int linesCleared) {
        int pointsToAdd = linesCleared * 100;
        return new Score(currentScore.points + pointsToAdd);
    }
} 