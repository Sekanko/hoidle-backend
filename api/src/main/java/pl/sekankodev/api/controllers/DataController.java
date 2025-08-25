package pl.sekankodev.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sekankodev.api.services.DataService;
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

    @GetMapping("/dailyBorderUrl")
    public ResponseEntity<String> getDailyBorderUrl(){
        return ResponseEntity.ok(dataService.getDailyBorderUrl());
    }

    @PostMapping("/internalUpdate")
    public ResponseEntity<Void> internalDataUpdate(){
        dataService.updateFromCsv();
        return ResponseEntity.noContent().build();
    }


}
