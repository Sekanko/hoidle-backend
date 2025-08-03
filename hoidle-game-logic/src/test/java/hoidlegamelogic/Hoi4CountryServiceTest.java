package hoidlegamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.sekankodev.hoidledata.data_exceptions.CountryNotFoundException;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.repositories.Hoi4CountryRepository;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.services.Hoi4CountryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Hoi4CountryServiceTest {
    private IRepositoryCatalog db;
    private Hoi4CountryService hoi4CountryService;

    @BeforeEach
    void setUp() {
        this.db = Mockito.mock(IRepositoryCatalog.class);
        this.hoi4CountryService = new Hoi4CountryService(db);

        Hoi4CountryRepository Hoi4CountryRepo = mock(Hoi4CountryRepository.class);
        when(this.db.getHoi4CountryRepository()).thenReturn(Hoi4CountryRepo);
    }

    @Test
    public void getCountriesTest() {
        List<Hoi4Country> countries = List.of(
                new Hoi4Country().setName("Poland"),
                new Hoi4Country().setName("Japan"),
                new Hoi4Country().setName("Romania")
        );

        List<Hoi4CountryDTO> expectedResult = List.of(
                new Hoi4CountryDTO().setName("Poland"),
                new Hoi4CountryDTO().setName("Japan"),
                new Hoi4CountryDTO().setName("Romania")
        );

        when(db.getHoi4CountryRepository().findAll()).thenReturn(countries);
        var returnList = hoi4CountryService.getHoi4Countries();
        assertEquals(expectedResult, returnList);

    }

    @Test
    public void getCountriesExcepitonTest() {
        when(db.getHoi4CountryRepository().findAll()).thenReturn(List.of());
        assertThrows(CountryNotFoundException.class,() -> hoi4CountryService.getHoi4Countries());
    }

}
