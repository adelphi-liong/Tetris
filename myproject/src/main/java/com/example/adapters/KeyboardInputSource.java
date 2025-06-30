package com.example.adapters;

import com.example.domain.InputSource;
import com.example.domain.InputCommand;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class KeyboardInputSource implements InputSource {
    private volatile boolean quit = false;
    private final BlockingQueue<InputCommand> inputQueue = new LinkedBlockingQueue<>();
    private final Thread inputThread;

    public KeyboardInputSource() {
        inputThread = new Thread(this::readInputLoop);
        inputThread.setDaemon(true);
        inputThread.start();
    }

    @Override
    public InputCommand getInput() {
        return inputQueue.poll(); // Non-blocking, returns null if no input
    }

    @Override
    public boolean shouldQuit() {
        return quit;
    }

    private void readInputLoop() {
        try {
            while (!quit) {
                if (System.in.available() > 0) {
                    int key = System.in.read();
                    InputCommand command = mapKey(key);
                    if (command != InputCommand.NONE) {
                        inputQueue.offer(command);
                    }
                }
                Thread.sleep(10); // Small delay to prevent excessive CPU usage
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private InputCommand mapKey(int key) {
        switch (key) {
            // Regular keys
            case 'a': case 'A': case '4':
                return InputCommand.MOVE_LEFT;
            case 'd': case 'D': case '6':
                return InputCommand.MOVE_RIGHT;
            case 's': case 'S': case '2':
                return InputCommand.MOVE_DOWN;
            case 'w': case 'W': case '8':
                return InputCommand.ROTATE;
            case ' ':
                return InputCommand.DROP;
            case 'q': case 'Q':
                quit = true;
                return InputCommand.QUIT;
            case 27: // ESC - might be start of arrow key sequence
                try {
                    if (System.in.available() > 0) {
                        int next = System.in.read();
                        if (next == '[' && System.in.available() > 0) {
                            int arrow = System.in.read();
                            switch (arrow) {
                                case 'A': return InputCommand.ROTATE;      // Up arrow
                                case 'B': return InputCommand.MOVE_DOWN;   // Down arrow  
                                case 'C': return InputCommand.MOVE_RIGHT;  // Right arrow
                                case 'D': return InputCommand.MOVE_LEFT;   // Left arrow
                            }
                        }
                    } else {
                        // Just ESC key
                        quit = true;
                        return InputCommand.QUIT;
                    }
                } catch (IOException e) {
                    // Ignore
                }
                break;
            default:
                return InputCommand.NONE;
        }
        return InputCommand.NONE;
    }
} 