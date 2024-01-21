package com.aleksi.mtg;

import org.SwaggerCodeGenExample.model.CardResponse;
import org.SwaggerCodeGenExample.model.GuessRequest;
import org.SwaggerCodeGenExample.model.Hint;
import org.SwaggerCodeGenExample.model.HintGivenHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MtgService {

    public final int maxGuesses = 5;
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
            Hint generatedHint = generateHint(numberOfGuesses);
            hintsProvided.add(generatedHint);
            if(numberOfGuesses == maxGuesses){
                System.out.println(numberOfGuesses + "LOST GAME");
                gameSession.setGameStatus("LOST");
            }
        }
        if(Objects.equals(guessedCard,targetCardName)){
            gameSession.setNumberOfGuesses(gameSession.getNumberOfGuesses()+1);
            gameSession.setGameStatus("WON");

        }
        System.out.println(request.getCardName());
    }

    private Hint generateHint(int hintNumber){
        Hint hint = new Hint();
        hint.setHintNumber(hintNumber);
        HintGivenHint givenHint = new HintGivenHint();
        givenHint.setHintText("hintText" + hintNumber);
        givenHint.setHintValue("hintValue" + hintNumber);
        hint.setGivenHint(givenHint);

        //add hint based on number of guesses
        //get stats from gameSession.getTargetCard()
        //for example targetCard().getToughness and power
        // hintText = stats
        //hintValue = toughness "/" power
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
        return cardResponse;
    }
}
