package com.example;

import java.io.IOException;

public class InputHandler extends Thread {
    volatile int lastKeyPressed = -1;

    public InputHandler() {
        this.start();
    }

    public void run() {
        try {
            while (true) {
                int key = System.in.read();
                if (key == '4' || key == '6' || key == '5' || key == '8') {
                    lastKeyPressed = key - '0';
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getUserInput() {
        int key = lastKeyPressed;
        lastKeyPressed = -1;
        return key;
    }
}
