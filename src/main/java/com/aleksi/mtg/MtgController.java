package com.aleksi.mtg;




import org.SwaggerCodeGenExample.api.CardApi;
import org.SwaggerCodeGenExample.model.CardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MtgController implements CardApi {

    private final MtgService mtgService;

    public MtgController( MtgService mtgService) {
        this.mtgService = mtgService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        mtgService.getCard();
        return mtgService.doHello();
    }

    @GetMapping("/card")
    public ResponseEntity<CardResponse> getCard(){
        CardResponse response = mtgService.getCard();
        return ResponseEntity.ok(response);
    }
}