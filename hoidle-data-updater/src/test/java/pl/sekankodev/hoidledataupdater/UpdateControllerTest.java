package pl.sekankodev.hoidledataupdater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pl.sekankodev.hoidledataupdater.controllers.UpdateController;
import pl.sekankodev.hoidledataupdater.services.UpdateService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

public class UpdateControllerTest {
    private UpdateController updateController;
    private UpdateService updateService;

    @BeforeEach
    public void setup(){
        updateService = Mockito.mock(UpdateService.class);
        updateController = new UpdateController(updateService);
    }

    @Test
    public void loadCountriesFromFileTest(){
        String fileName = "countries.csv";
        var result = updateController.loadCountriesFromFile(fileName);
        verify(updateService, Mockito.times(1)).UpdateCountryDatabaseFromCSVFile(fileName);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }
}
