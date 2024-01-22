package com.aleksi.mtg;

import jakarta.servlet.http.HttpSession;
import org.SwaggerCodeGenExample.api.CardApi;
import org.SwaggerCodeGenExample.model.CardResponse;
import org.SwaggerCodeGenExample.model.GameResponse;
import org.SwaggerCodeGenExample.model.GuessRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MtgController implements CardApi {

    private final MtgService mtgService;

    public MtgController( MtgService mtgService) {
        this.mtgService = mtgService;
    }

    @PostMapping("/guess")
    public ResponseEntity<GameResponse> guess(@RequestBody GuessRequest request, HttpSession session){
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        // Initialize a new GameSession if it doesn't exist
        if (gameSession == null) {
            gameSession = new GameSession("1", mtgService.getCard().getName(), mtgService.maxGuesses, mtgService.getCard());
            session.setAttribute("gameSession", gameSession);
        }

        if (gameSession.isGameOver()) {
            System.out.println("game over");
            // Handle the case where the game is already over
            //todo: return different message for game over
            return ResponseEntity.ok(new GameResponse());
        }

        // Process the user's guess
        mtgService.guess(gameSession, request);

         //Update the game session in the session
        session.setAttribute("gameSession", gameSession);
        GameResponse response = new GameResponse();
        response.setNumberOfGuesses(gameSession.getNumberOfGuesses());
        response.setHintsProvided(gameSession.getHintsProvided());
        response.setGameStatus(gameSession.getGameStatus());
        response.setHintsProvided(gameSession.getHintsProvided());
         //Return the updated game state
        return ResponseEntity.ok(response);
    }

    @GetMapping("/card")
    public ResponseEntity<CardResponse> getCard(){
        CardResponse response = mtgService.getCard();
        return ResponseEntity.ok(response);
    }


}