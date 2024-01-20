package com.aleksi.mtg;

import org.SwaggerCodeGenExample.model.CardResponse;
import org.SwaggerCodeGenExample.model.GuessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class MtgService {
    private CardResponse dailyCard = new CardResponse();
    private final RestTemplate restTemplate;

    @Autowired
    public MtgService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> doHello(){
        return ResponseEntity.ok().body("Hello");
    }
    private void initializeCard(){
        dailyCard = getCard();
    }

    public void guess(GameSession gameSession,GuessRequest request){
        String targetCardName = getCard().getName();
        if(!Objects.equals(request.getCardName(), targetCardName)){
                gameSession.setNumberOfGuesses(gameSession.getNumberOfGuesses()+1);
        }
        System.out.println(request.getCardName());
    }

    private String generateHint(){
        return "hint :P";
    }


    public CardResponse getCard(){
        String apiUrl = "https://api.magicthegathering.io/v1/cards?name=kasla";
        CardList response = restTemplate.getForObject(apiUrl,CardList.class);
        assert response != null;
        Card testCard = response.getCards().get(0);
        CardResponse cardResponse = mapCardToCardResponse(testCard);
        System.out.println(cardResponse);
        return cardResponse;
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
