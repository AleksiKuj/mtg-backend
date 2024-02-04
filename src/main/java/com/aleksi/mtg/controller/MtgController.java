package com.aleksi.mtg.controller;

import com.aleksi.mtg.model.GameSession;
import com.aleksi.mtg.model.GuessResponse;
import com.aleksi.mtg.service.MtgService;
import jakarta.servlet.http.HttpSession;
import org.SwaggerCodeGenExample.api.SearchCardsApi;
import org.SwaggerCodeGenExample.model.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@EnableMongoRepositories
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MtgController implements SearchCardsApi {
    private final MtgService mtgService;

    public MtgController( MtgService mtgService) {
        this.mtgService = mtgService;
    }

    @PostMapping("/guess2")
    public ResponseEntity<GuessResponse> guess(@RequestBody GuessRequest request, HttpSession session){
        GameSession gameSession = mtgService.getGameSession(session);

        GuessResponse response = mtgService.processGuess(request, gameSession);
        mtgService.updateSession(session, gameSession);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hint")
    public ResponseEntity<HintResponse> getHint(HttpSession session) {
        GameSession gameSession = mtgService.getGameSession(session);
        mtgService.updateSession(session, gameSession);
        HintResponse response = mtgService.getHint(gameSession);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/initialize")
    public ResponseEntity<InitializeResponse> initialize(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        SearchCardsResponse searchCardsResponse = mtgService.getAllCardsResponse();

        InitializeResponse initializeResponse = new InitializeResponse();
        initializeResponse.setSearchCardsResponse(searchCardsResponse);
        initializeResponse.setMaxGuesses(mtgService.getMaxGuesses());
        return ResponseEntity.ok(initializeResponse);
    }

}