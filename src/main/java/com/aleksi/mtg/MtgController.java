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

    @PostMapping("/increment")
    public String incrementCounter(HttpSession session) {
        Integer counter = (Integer) session.getAttribute("counter");
        if (counter == null) {
            counter = 0;
        }
        counter++;
        session.setAttribute("counter", counter);
        return "Counter incremented to: " + counter;
    }
    @PostMapping("/start-game")
    public ResponseEntity<String> startGame(HttpSession session) {
        // Initialize a new GameSession
        GameSession gameSession = new GameSession("1","2",3);

        // Store the gameSession in the HttpSession
        session.setAttribute("gameSession", gameSession);
        return ResponseEntity.ok("New game started");
    }

    @PostMapping("/guess")
    public ResponseEntity<GameResponse> guess(@RequestBody GuessRequest request, HttpSession session){
        GameSession gameSession = (GameSession) session.getAttribute("gameSession");
        if (gameSession == null) {
            // Handle the case where there is no game session (e.g., return an error response)
            return ResponseEntity.badRequest().body(new GameResponse());
        }

        if (gameSession.isGameOver()) {
            // Handle the case where the game is already over
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
        System.out.println(gameSession.getNumberOfGuesses());
         //Return the updated game state
        return ResponseEntity.ok(response);
    }
//        GameSessionStateResponse response = new GameSessionStateResponse();
//        response.setGuesses(1);
//        mtgService.guess(request);
//        return ResponseEntity.ok(response);


    //name post parameter
    //if name === card.name win
    //else guesses +1
    //game_status
    //local maxGuesses value to check agaisnt

    @GetMapping("/card")
    public ResponseEntity<CardResponse> getCard(){
        CardResponse response = mtgService.getCard();
        return ResponseEntity.ok(response);
    }


}