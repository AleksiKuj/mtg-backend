package com.aleksi.mtg;

import org.SwaggerCodeGenExample.model.CardResponse;
import org.SwaggerCodeGenExample.model.GuessRequest;
import org.SwaggerCodeGenExample.model.Hint;
import org.SwaggerCodeGenExample.model.HintGivenHint;
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
        }
        if(Objects.equals(guessedCard,targetCardName)){
            gameSession.setNumberOfGuesses(gameSession.getNumberOfGuesses()+1);
            gameSession.setGameStatus("WON");
        //todo: add message "won in x guesses"
        }
    }

    private Hint generateHint(int hintNumber, GameSession gameSession){
        Hint hint = new Hint();
        hint.setHintNumber(hintNumber);
        HintGivenHint givenHint = new HintGivenHint();
        CardResponse targetCard = gameSession.getTargetCard();

        switch(hintNumber){
            case 0:
                givenHint.setHintText("stats");
                givenHint.setHintValue(targetCard.getPower() + "/" + targetCard.getToughness());
                break;
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
                //todo: COMPARE TO originalText and choose one. also censor the characters name from text
                givenHint.setHintValue(targetCard.getText());
                break;
            case 5:
                givenHint.setHintText("set");
                givenHint.setHintValue(targetCard.getSetFullName());
                break;
            default:
                givenHint.setHintText("");
                givenHint.setHintValue("");
                break;
        }
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
    public CardResponse mapCardToCardResponse(Card card) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setName(card.getName());
        cardResponse.setManaCost(card.getManaCost());
        cardResponse.setCmc(card.getCmc());
        cardResponse.setColors(card.getColors());
        cardResponse.setSupertypes(card.getSupertypes());
        cardResponse.setTypes(card.getTypes());
        cardResponse.setSubtypes(card.getSubtypes());
        cardResponse.setRarity(card.getRarity());
        cardResponse.setSet(card.getSet());
        cardResponse.setSetFullName(card.getSetName());
        cardResponse.setText(card.getText());
        cardResponse.setPower(card.getPower());
        cardResponse.setToughness(card.getToughness());
        cardResponse.setLayout(card.getLayout());
        cardResponse.setImageUrl(card.getImageUrl());
        cardResponse.setOriginalText(card.getOriginalText());
        cardResponse.setId(card.getId());
        cardResponse.setType(card.getType());
        return cardResponse;
    }
}
