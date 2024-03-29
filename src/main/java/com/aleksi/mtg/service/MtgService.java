package com.aleksi.mtg.service;

import com.aleksi.mtg.model.*;

import com.aleksi.mtg.repository.FullCardRepository;
import com.aleksi.mtg.repository.ShortCardRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.SwaggerCodeGenExample.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MtgService {

    @Autowired
    private MongoTemplate mongoTemplate;
    public final int maxGuesses = 10;
    private final RestTemplate restTemplate;
    private Card storedCard;

    @Autowired
    private ShortCardRepository shortCardRepository;

    @Autowired
    FullCardRepository fullCardRepository;
    @Autowired
    public MtgService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 0 0 * * *") //midnight daily
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Card card = fetchTodaysCard();
        if(card != null){
            System.out.println("NEW CARD: " + card);
        }
        storedCard = card;
    }

    public GameSession getGameSession(HttpSession session) {
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null) {
            gameSession = new GameSession( getCard().getName(), maxGuesses, getCard());
            session.setAttribute("gameSession", gameSession);
        }
        return gameSession;
    }

    public GuessResponse processGuess(GuessRequest request, GameSession gameSession) {
        if (gameSession.isGameOver()) {
            GuessResponse response = new GuessResponse();
            if(gameSession.getGameStatus().equals("WON")){
                response.setGameStatus("WON");
            } else {
                response.setGameStatus("LOST");
            }
            return response;
        }

        guess(gameSession, request);
        if(request.getCardName().equals(gameSession.getTargetCardName())){
            gameSession.setGameStatus("WON");
        } else if(gameSession.getNumberOfGuesses() == maxGuesses){
            gameSession.setGameStatus("LOST");
        }

        GuessResponse response = new GuessResponse();

        List<Card> guesses = gameSession.getGuesses();
        response.setGuesses(guesses);
        response.setAttributeCorrectness(gameSession.getAttributeCorrectness());
        response.setMaxGuesses(gameSession.getMaxGuesses());
        response.setGameStatus(gameSession.getGameStatus());
        response.setNumberOfGuesses(gameSession.getNumberOfGuesses());

        response.setCorrect(request.getCardName().equals(gameSession.getTargetCardName()));

        if(!guesses.isEmpty()){
            response.setLastGuess(guesses.get(guesses.size()-1));
        }

        if(gameSession.isGameOver()){
            response.setTargetCard(getCard());
        }
        return response;
    }

    public void updateSession(HttpSession session, GameSession gameSession) {
        session.setAttribute("gameSession", gameSession);
    }

    public void guess(GameSession gameSession,GuessRequest request){
        Card targetCard = gameSession.getTargetCard();
        Card guessedCard = fetchCardByNameFromApi(request.getCardName());

        if(guessedCard == null){
            return;
        }
       int numberOfGuesses = gameSession.getNumberOfGuesses();
       List<Card> guesses = gameSession.getGuesses();
       gameSession.setNumberOfGuesses(numberOfGuesses+1);

        String powerStatus = compareNumericAttributes(Integer.parseInt(targetCard.getPower()), Integer.parseInt(guessedCard.getPower()));
        guessedCard.setAttributeCorrectness("power", powerStatus);

        String toughnessStatus = compareNumericAttributes(Integer.parseInt(targetCard.getToughness()), Integer.parseInt(guessedCard.getToughness()));
        guessedCard.setAttributeCorrectness("toughness", toughnessStatus);

        String cmcStatus = compareNumericAttributes(targetCard.getCmc(), guessedCard.getCmc());
        guessedCard.setAttributeCorrectness("cmc", cmcStatus);

        String setStatus = compareAttributes(targetCard.getSet(), guessedCard.getSet());
        guessedCard.setAttributeCorrectness("set", setStatus);

        String rarityStatus = compareAttributes(targetCard.getRarity(), guessedCard.getRarity());
        guessedCard.setAttributeCorrectness("rarity", rarityStatus);

        List<String> targetSubtypes = targetCard.getSubtypes();
        List<String> guessedSubtypes = guessedCard.getSubtypes();
        String subtypesStatus = compareStringLists(targetSubtypes, guessedSubtypes);
        guessedCard.setAttributeCorrectness("subtypes", subtypesStatus);

        List<String> targetColors = targetCard.getColors();
        List<String> guessedColors = guessedCard.getColors();
        String colorsStatus = compareStringLists(targetColors, guessedColors);
        guessedCard.setAttributeCorrectness("colors", colorsStatus);

        guesses.add(guessedCard);
    }

    public HintResponse getHint(GameSession session){
        HintResponse response = new HintResponse();
        response.setHint(censorName(getCard().getName(), getCard().getText())+ " \n " + censorName(getCard().getName(), getCard().getFlavor()));
        session.setNumberOfGuesses(session.getNumberOfGuesses()+1);
        response.setNumberOfGuesses(BigDecimal.valueOf(session.getNumberOfGuesses()));
        response.setMaxGuesses(getMaxGuesses());
        return response;
    }
    private String compareStringLists(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        Set<String> set2 = new HashSet<>(list2);

        if (set1.equals(set2)) {
            return "correct";
        } else {
            Set<String> intersection = new HashSet<>(set1);
            intersection.retainAll(set2);
            if (!intersection.isEmpty()) {
                return "partial";
            } else {
                return "wrong";
            }
        }
    }

    private String compareNumericAttributes(int attribute1, int attribute2) {
        if (attribute1 == attribute2) {
            return "correct";
        } else if ( attribute1 > attribute2) {
            return "higher";
        } else {
            return "lower";
        }
    }

    private String compareAttributes(String attribute1, String attribute2) {
        if (attribute1.equals(attribute2)) {
            return "correct";
        } else {
            return "wrong";
        }
    }

    public Date getTodayUtc() {
        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utcTimeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    private Card fetchTodaysCard() {
        Date today = getTodayUtc();
        Optional<Card> cardOptional = fullCardRepository.findByDate(today);
        if (cardOptional.isPresent()) {
            return cardOptional.get();
        } else {
            System.out.println("No card found for today's date.");
            return fetchRandomCardFromApi();
        }
    }
    private Card fetchRandomCardFromApi() {
        ShortCard randomCard = getRandomCard();
        System.out.println(randomCard.getName());
        String apiUrl = "https://api.magicthegathering.io/v1/cards?name=" + randomCard.getName();
        CardList response = restTemplate.getForObject(apiUrl, CardList.class);
        assert response != null;
        return response.getCards().get(0);
    }
    public Card fetchCardByNameFromApi(String cardName) {
        String apiUrl = "https://api.magicthegathering.io/v1/cards?type=legendary+creature&name=" + cardName;
        CardList response = restTemplate.getForObject(apiUrl, CardList.class);
        if (response != null && response.getCards() != null && !response.getCards().isEmpty()) {
            return response.getCards().get(0);
        } else {
            return null; // Or you could throw a new RuntimeException("No cards found for name: " + cardName);
        }
    }

    public Card getCard() {
        return storedCard;
    }
    public BigDecimal getMaxGuesses(){ return BigDecimal.valueOf(maxGuesses);}

    public ShortCard getRandomCard() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sample(1)
        );

        AggregationResults<ShortCard> result = mongoTemplate.aggregate(
                aggregation, "cards", ShortCard.class
        );

        return result.getUniqueMappedResult();
    }

    public SearchCardsResponse getAllCardsResponse() {
        List<ShortCard> allCards = shortCardRepository.findAll();

        List<SearchCardsResponseCardsInner> cardsInnerList = allCards.stream()
                .map(shortCard -> {
                    SearchCardsResponseCardsInner cardsInner = new SearchCardsResponseCardsInner();
                    cardsInner.setId(shortCard.getId());
                    cardsInner.setName(shortCard.getName());
                    return cardsInner;
                })
                .collect(Collectors.toList());

        SearchCardsResponse response = new SearchCardsResponse();
        response.setCards(cardsInnerList);
        return response;
    }

    private static String censorName(String name, String text){
        if(name == null || text == null){
            return "";
        }
        return text.replace(name, "_____");
    }

    public Duration calculateTimeUntilNextMidnight() {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime nextMidnightUtc = nowUtc.toLocalDate().plusDays(1).atStartOfDay(ZoneId.of("UTC"));

        return Duration.between(nowUtc, nextMidnightUtc);
    }
}
