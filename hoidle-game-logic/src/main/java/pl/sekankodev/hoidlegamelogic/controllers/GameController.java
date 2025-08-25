package pl.sekankodev.hoidlegamelogic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sekankodev.hoidledata.model.DailyType;
import pl.sekankodev.hoidlegamelogic.modelDto.Colors;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;
import pl.sekankodev.hoidlegamelogic.services.IServiceCatalog;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("game/control/")
@RequiredArgsConstructor
@CrossOrigin
public class GameController {
    private final IServiceCatalog serviceCatalog;

    @GetMapping("dayCountryOfTheDay/{dailyType}")
    public ResponseEntity<HoidleDailyCountryDTO> getCountryOfTheDay(@PathVariable DailyType dailyType) {
        return new ResponseEntity<>(serviceCatalog.getGameService().getOrSetCountry(dailyType), HttpStatus.OK);
    }

    @PostMapping("guessClassic")
    public ResponseEntity<List<Colors>> guessResult(@RequestBody Hoi4CountryDTO guessedCountry) {

        var result = serviceCatalog.getGameService().checkGuessForClassic(guessedCountry);
        return ResponseEntity.ok(result);
    }

    @PostMapping("guessBorder")
    public ResponseEntity<Boolean> guessBorder(@RequestBody Hoi4CountryDTO guessedCountry) {
        return new ResponseEntity<>(serviceCatalog.getGameService().checkGuessForBorders(guessedCountry), HttpStatus.OK);
    }

    @GetMapping("getCountryFields")
    public ResponseEntity<List<String>> getField(){
        return ResponseEntity.ok(
                Arrays.stream(
                        Hoi4CountryDTO.class.getDeclaredFields())
                        .map(Field::getName)
                        .filter(s -> !s.equals("url"))
                        .toList());
    }
}
