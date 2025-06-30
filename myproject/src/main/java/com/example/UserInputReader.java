package com.example;

import java.io.IOException;

public class UserInputReader implements InputSource {
    
    public UserInputReader() {
    }
    
    public GameAction readInput() {
        try {
            if (System.in.available() > 0) {
                int input = System.in.read();
                char ch = (char) input;
                
                switch (ch) {
                    case 'a':
                    case 'A':
                        return GameAction.MOVE_LEFT;
                    case 'd':
                    case 'D':
                        return GameAction.MOVE_RIGHT;
                    case 'w':
                    case 'W':
                        return GameAction.ROTATE;
                    case 's':
                    case 'S':
                        return GameAction.MOVE_DOWN;
                    case ' ':
                        return GameAction.HARD_DROP;
                    case 'q':
                    case 'Q':
                        return GameAction.QUIT;
                    default:
                        return GameAction.NO_ACTION;
                }
            }
        } catch (IOException e) {
            // Handle error silently
        }
        return GameAction.NO_ACTION;
    }
} 