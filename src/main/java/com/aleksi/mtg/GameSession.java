package com.aleksi.mtg;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private String targetCardId; // Unique identifier for the MTG card
    private String targetCardName; // Name of the target card
    private int numberOfGuesses;
    private List<String> hintsProvided;
    private String gameStatus; // e.g., "IN_PROGRESS", "WON", "LOST"
    private String lastGuess;
    private final int maxGuesses;

    public GameSession(String targetCardId, String targetCardName, int maxGuesses) {
        this.targetCardId = targetCardId;
        this.targetCardName = targetCardName;
        this.numberOfGuesses = 0;
        this.hintsProvided = new ArrayList<>();
        this.gameStatus = "IN_PROGRESS";
        this.maxGuesses = maxGuesses;
    }

    // Getters and setters
    public String getTargetCardId() {
        return targetCardId;
    }

    public String getTargetCardName() {
        return targetCardName;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public void setNumberOfGuesses(int numberOfGuesses) {
        this.numberOfGuesses = numberOfGuesses;
    }

    public List<String> getHintsProvided() {
        return hintsProvided;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getLastGuess() {
        return lastGuess;
    }

    public void setLastGuess(String lastGuess) {
        this.lastGuess = lastGuess;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public void addHint(String hint) {
        hintsProvided.add(hint);
    }

    public boolean isGameOver() {
        return "WON".equals(gameStatus) || "LOST".equals(gameStatus) || numberOfGuesses >= maxGuesses;
    }

    // Additional methods for game logic...
}
