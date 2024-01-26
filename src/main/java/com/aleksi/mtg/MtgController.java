package com.aleksi.mtg;

import jakarta.servlet.http.HttpSession;
import org.SwaggerCodeGenExample.api.CardApi;
import org.SwaggerCodeGenExample.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableMongoRepositories
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MtgController implements CardApi {

    private final MtgService mtgService;

    @Autowired
    private ShortCardRepository shortCardRepository;
    public MtgController( MtgService mtgService) {
        this.mtgService = mtgService;
    }

    @PostMapping("/guess")
    public ResponseEntity<GameResponse> guess(@RequestBody GuessRequest request, HttpSession session){
        GameSession gameSession = mtgService.getGameSession(session);
        GameResponse response = mtgService.processGuess(request, gameSession);
        mtgService.updateSession(session, gameSession);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/firstHint")
    public ResponseEntity<Hint> getFirstHint(){
        Hint response = mtgService.getFirstHint();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search-cards")
    public ResponseEntity<SearchCardsResponse> searchCards(@RequestParam String name) {
        PageRequest pageRequest = PageRequest.of(0,20); //limit to 20 results
        List<SearchCardsResponseContentInner> cardPage = shortCardRepository.findByNameContainingIgnoreCase(name,pageRequest);
        SearchCardsResponse response = new SearchCardsResponse();
        response.setContent(cardPage);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/card")
    public ResponseEntity<CardResponse> getCard(){
        CardResponse response = mtgService.getCard();
        return ResponseEntity.ok(response);
    }


}