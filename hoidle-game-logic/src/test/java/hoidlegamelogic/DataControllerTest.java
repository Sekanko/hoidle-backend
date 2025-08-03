package hoidlegamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pl.sekankodev.hoidlegamelogic.controllers.DataController;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.services.Hoi4CountryService;
import pl.sekankodev.hoidlegamelogic.services.IServiceCatalog;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DataControllerTest {
    private DataController dataController;
    private IServiceCatalog serviceCatalog;

    @BeforeEach
    void setUp() {
        serviceCatalog = Mockito.mock(IServiceCatalog.class);
        dataController = new DataController(serviceCatalog);

        Hoi4CountryService hoi4CountryService = Mockito.mock(Hoi4CountryService.class);
        when(serviceCatalog.getHoi4CountryService()).thenReturn(hoi4CountryService);
    }

    @Test
    void getAllCountriesTest() {
        List<Hoi4CountryDTO> countries = List.of(
                new Hoi4CountryDTO().setName("United States"),
                new Hoi4CountryDTO().setName("United Kingdom")
        );
        when(serviceCatalog.getHoi4CountryService().getHoi4Countries()).thenReturn(countries);
        var result = dataController.getAllCountries();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(countries, result.getBody());
    }
}
