package com.aleksi.mtg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSession {
    private String targetCardId;
    private String targetCardName;
    private int numberOfGuesses;
    private String gameStatus; //"IN_PROGRESS", "WON", "LOST"
    private String lastGuess;
    private final int maxGuesses;
    private List<Card> guesses;
    private Map<String, String> attributeCorrectness = new HashMap<>();

    public void setAttributeCorrectness(String attribute, String status) {
        attributeCorrectness.put(attribute, status);
    }

    public Map<String, String> getAttributeCorrectness() {
        return attributeCorrectness;
    }
    public Card getTargetCard() {
        return targetCard;
    }

    private final Card targetCard;

    @Override
    public String toString() {
        return "GameSession{" +
                "targetCardId='" + targetCardId + '\'' +
                ", targetCardName='" + targetCardName + '\'' +
                ", numberOfGuesses=" + numberOfGuesses +
                ", gameStatus='" + gameStatus + '\'' +
                ", lastGuess='" + lastGuess + '\'' +
                ", maxGuesses=" + maxGuesses +
                ", guesses=" + guesses +
                ", targetCard=" + targetCard +
                '}';
    }

    public List<Card> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<Card> guesses) {
        this.guesses = guesses;
    }


    public GameSession( String targetCardName, int maxGuesses, Card targetCard) {
        this.targetCardName = targetCardName;
        this.targetCard = targetCard;
        this.numberOfGuesses = 0;
        this.gameStatus = "IN_PROGRESS";
        this.maxGuesses = maxGuesses;
        this.guesses = new ArrayList<>();
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

    public boolean isGameOver() {
        return "WON".equals(gameStatus) || "LOST".equals(gameStatus) || numberOfGuesses >= maxGuesses;
    }

}
