package pl.sekankodev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sekankodev.api.dtos.HoidleDailyCountryDto;
import pl.sekankodev.api.services.DataService;
import pl.sekankodev.data.models.GameMode;
import pl.sekankodev.data.models.Hoi4Country;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    @GetMapping("/countries")
    public ResponseEntity<List<Hoi4Country>> getHoi4Countries(){
        return ResponseEntity.ok(dataService.getAllHoi4Countries());
    }

    @GetMapping("/countryFields")
    public ResponseEntity<List<String>> getHoi4countryFields(){
        return ResponseEntity.ok(dataService.getFieldOfHoi4Country());
    }

    @GetMapping("/todayCountry/{gameMode}")
    public ResponseEntity<HoidleDailyCountryDto> getDailyCountry(@PathVariable GameMode gameMode){
        return ResponseEntity.ok(dataService.getOrSetDailyCountryByGameMode(gameMode));
    }

}
