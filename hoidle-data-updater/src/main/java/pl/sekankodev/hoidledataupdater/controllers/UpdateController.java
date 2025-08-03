package pl.sekankodev.hoidledataupdater.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sekankodev.hoidledataupdater.services.UpdateService;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class UpdateController {
    final UpdateService updateService;

    @PostMapping("from/file/csv/{fileName}")
    public ResponseEntity<Void> loadCountriesFromFile(@PathVariable String fileName) {
        updateService.UpdateCountryDatabaseFromCSVFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
