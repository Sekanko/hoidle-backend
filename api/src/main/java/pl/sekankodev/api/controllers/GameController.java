package pl.sekankodev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sekankodev.api.models.ResponseColor;
import pl.sekankodev.api.services.GameService;
import pl.sekankodev.data.models.GameMode;
import pl.sekankodev.data.models.Hoi4Country;

import java.util.Map;

@RequestMapping("/game")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class GameController {
    private final GameService gameService;

    @PostMapping("/guess/classicMode")
    public ResponseEntity<Map<String, ResponseColor>> guessClassic(@RequestBody Hoi4Country country){
        return ResponseEntity.ok(gameService.guessClassic(country));
    }

    @PostMapping("/guess/{gameMode}")
    public ResponseEntity<Boolean> checkGuessAnyGameMode(@PathVariable("gameMode") GameMode gameMode, @RequestBody Hoi4Country country){
        return ResponseEntity.ok(gameService.isGuessedCountryCorrect(country, gameMode));
    }


}
