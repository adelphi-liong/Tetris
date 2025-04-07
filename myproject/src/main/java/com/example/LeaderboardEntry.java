package com.example;

public class LeaderboardEntry {
    String name;
    int score;

    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
