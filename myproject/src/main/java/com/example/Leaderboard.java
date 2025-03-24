package com.example;

import java.io.*;
import java.util.*;

public class Leaderboard {
    private static final String FILE_NAME = "leaderboard.dat";
    private List<LeaderboardEntry> entries = new ArrayList<>();

    public Leaderboard() {
        loadLeaderboard();
    }

    public void addEntry(String name, int score) {
        entries.add(new LeaderboardEntry(name, score));
        entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore())); // Sort by highest score
        if (entries.size() > 10)
            entries = entries.subList(0, 10); // Keep top 10
        saveLeaderboard();
    }

    public void saveLeaderboard() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(entries);
        } catch (IOException e) {
            System.out.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    public void loadLeaderboard() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            entries = (List<LeaderboardEntry>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous leaderboard found. Creating a new one.");
        }
    }

    public void printLeaderboard() {
        System.out.println("===== LEADERBOARD =====");
        for (int i = 0; i < entries.size(); i++) {
            System.out.println((i + 1) + ". " + entries.get(i));
        }
    }
}
