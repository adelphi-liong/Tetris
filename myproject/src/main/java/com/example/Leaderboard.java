package com.example;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(String name, int score) {
        entries.add(new LeaderboardEntry(name, score));
    }

    public void printLeaderboard() {
        System.out.println("Final Leaderboard:");
        entries.sort((e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));  // Sort by score descending
        for (LeaderboardEntry entry : entries) {
            System.out.println(entry.getName() + " - " + entry.getScore());
        }
    }
}