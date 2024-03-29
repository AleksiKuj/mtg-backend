package com.aleksi.mtg.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuessResponse {
    private int numberOfGuesses;
    private String gameStatus; //"IN_PROGRESS", "WON", "LOST"
    private Card lastGuess;
    private Card targetCard;
    private  int maxGuesses;
    private List<Card> guesses;
    private Map<String, String> attributeCorrectness = new HashMap<>();

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    private Boolean isCorrect;

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

    public Card getLastGuess() {
        return lastGuess;
    }

    public void setLastGuess(Card lastGuess) {
        this.lastGuess = lastGuess;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public void setMaxGuesses(int maxGuesses) {
        this.maxGuesses = maxGuesses;
    }

    public List<Card> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<Card> guesses) {
        this.guesses = guesses;
    }

    public Map<String, String> getAttributeCorrectness() {
        return attributeCorrectness;
    }

    public Card getTargetCard() {
        return targetCard;
    }

    public void setTargetCard(Card targetCard) {
        this.targetCard = targetCard;
    }

    public void setAttributeCorrectness(Map<String, String> attributeCorrectness) {
        this.attributeCorrectness = attributeCorrectness;
    }
}
