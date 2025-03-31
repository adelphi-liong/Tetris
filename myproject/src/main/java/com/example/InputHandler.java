package com.example;

import java.io.IOException;

public class InputHandler extends Thread {
    private volatile int lastKeyPressed = -1;  // Stores the last valid key press

    public InputHandler() {
        this.start(); // Start listening for input in a separate thread
    }

    @Override
    public void run() {
        try {
            while (true) {
                int key = System.in.read();
                if (key == '4' || key == '6' || key == '5' || key == '8') {
                    lastKeyPressed = key - '0'; // Convert char to int
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getUserInput() {
        int key = lastKeyPressed;
        lastKeyPressed = -1; // Reset after reading so input isn't repeated
        return key;
    }
}
