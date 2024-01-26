package com.aleksi.mtg;

import jakarta.servlet.http.HttpSession;
import org.SwaggerCodeGenExample.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class MtgService {

    public final int maxGuesses = 6;
    private final RestTemplate restTemplate;

    @Autowired
    public MtgService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GameSession getGameSession(HttpSession session) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null) {
            gameSession = new GameSession( getCard().getName(), maxGuesses, getCard());
            session.setAttribute("gameSession", gameSession);
            gameSession.getHintsProvided().add(getFirstHint());
        }
        return gameSession;
    }

    public GameResponse processGuess(GuessRequest request, GameSession gameSession) {
        if (gameSession.isGameOver()) {
            System.out.println("game over");
            return new GameResponse();
        }

        guess(gameSession, request);

        GameResponse response = new GameResponse();

        response.setNumberOfGuesses(gameSession.getNumberOfGuesses());
        response.setHintsProvided(gameSession.getHintsProvided());
        response.setGameStatus(gameSession.getGameStatus());
        response.setIsCorrect(Objects.equals(request.getCardName(), gameSession.getTargetCardName()));
        response.setGuess(request.getCardName());
        return response;
    }

    public void updateSession(HttpSession session, GameSession gameSession) {
        session.setAttribute("gameSession", gameSession);
    }

    public void guess(GameSession gameSession,GuessRequest request){
        String targetCardName = gameSession.getTargetCardName();
        String guessedCard = request.getCardName();
        int numberOfGuesses = gameSession.getNumberOfGuesses();
        List<Hint> hintsProvided = gameSession.getHintsProvided();
        gameSession.setNumberOfGuesses(numberOfGuesses+1);

        if(!Objects.equals(guessedCard, targetCardName)){
            Hint generatedHint = generateHint(numberOfGuesses,gameSession);
            hintsProvided.add(generatedHint);
            if(numberOfGuesses == maxGuesses){
                System.out.println(numberOfGuesses + "LOST GAME");
                gameSession.setGameStatus("LOST");
            }
        } else {
            gameSession.setNumberOfGuesses(gameSession.getNumberOfGuesses() + 1);
            gameSession.setGameStatus("WON");
            // Generate and add all hints
            for (int hintNumber = 0; hintNumber <= 5; hintNumber++) {
                Hint generatedHint = generateHint(hintNumber, gameSession);
                hintsProvided.add(generatedHint);
            }
        }
    }

    private Hint generateHint(int hintNumber, GameSession gameSession){
        Hint hint = new Hint();
        hint.setHintNumber(hintNumber+1);
        HintGivenHint givenHint = new HintGivenHint();
        CardResponse targetCard = gameSession.getTargetCard();

        switch(hintNumber+1){
            case 1:
                givenHint.setHintText("colors");
                givenHint.setHintValue(targetCard.getColors().toString());
                break;
            case 2:
                givenHint.setHintText("type");
                givenHint.setHintValue(targetCard.getType());
                break;
            case 3:
                //todo: format manacost correctly
                givenHint.setHintText("manaCost");
                givenHint.setHintValue(targetCard.getManaCost());
                break;
            case 4:
                givenHint.setHintText("cardText");
                givenHint.setHintValue(censorName(targetCard.getName(),targetCard.getText()) + "\n" + censorName(targetCard.getName(),targetCard.getFlavor()));
                break;
            case 5:
                givenHint.setHintText("set");
                givenHint.setHintValue(targetCard.getSetFullName());
                break;
            default:
                givenHint.setHintText("");
                givenHint.setHintValue("");
                gameSession.setGameStatus("LOST");
                break;
        }
        hint.setGivenHint(givenHint);
        return hint;
    }

    public Hint getFirstHint(){
        Hint hint = new Hint();
        hint.setHintNumber(0);
        HintGivenHint givenHint = new HintGivenHint();

        givenHint.setHintText("stats");
        givenHint.setHintValue(getCard().getPower() + "/" + getCard().getToughness());
        hint.setGivenHint(givenHint);
        return hint;
    }
    public CardResponse getCard(){
        String apiUrl = "https://api.magicthegathering.io/v1/cards?name=kasla";
        CardList response = restTemplate.getForObject(apiUrl,CardList.class);
        assert response != null;
        Card testCard = response.getCards().get(0);
        return mapCardToCardResponse(testCard);
    }

    private static String censorName(String name, String text){
        if(name == null || text == null){
            return "";
        }
        return text.replace(name, "_____");
    }
    public CardResponse mapCardToCardResponse(Card card) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setName(card.getName());
        cardResponse.setManaCost(card.getManaCost());
        cardResponse.setColors(card.getColors());
        cardResponse.setRarity(card.getRarity());
        cardResponse.setSet(card.getSet());
        cardResponse.setSetFullName(card.getSetName());
        cardResponse.setText(card.getText());
        cardResponse.setPower(card.getPower());
        cardResponse.setToughness(card.getToughness());
        cardResponse.setImageUrl(card.getImageUrl());
        cardResponse.setType(card.getType());
        if(card.getFlavor() != null){ cardResponse.setFlavor((card.getFlavor()));}
        return cardResponse;
    }
}
