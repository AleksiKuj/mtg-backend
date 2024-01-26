package com.aleksi.mtg;

import jakarta.servlet.http.HttpSession;
import org.SwaggerCodeGenExample.api.CardApi;
import org.SwaggerCodeGenExample.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MtgController implements CardApi {

    private final MtgService mtgService;

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
    @GetMapping("/card")
    public ResponseEntity<CardResponse> getCard(){
        CardResponse response = mtgService.getCard();
        return ResponseEntity.ok(response);
    }


}