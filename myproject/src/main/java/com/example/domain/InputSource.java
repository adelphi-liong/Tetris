package com.example.domain;

public interface InputSource {
    InputCommand getInput();
    boolean shouldQuit();
} 