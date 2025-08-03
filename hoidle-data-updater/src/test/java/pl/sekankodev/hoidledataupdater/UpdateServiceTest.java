package pl.sekankodev.hoidledataupdater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.repositories.Hoi4CountryRepository;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidledataupdater.contract.Hoi4CountryDTO;
import pl.sekankodev.hoidledataupdater.mappers.CountryMapper;
import pl.sekankodev.hoidledataupdater.parsers.ICSVParser;
import pl.sekankodev.hoidledataupdater.services.UpdateService;
import pl.sekankodev.hoidledataupdater.update_exceptions.NothingWasParsedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UpdateServiceTest {
    private IRepositoryCatalog db;
    private ICSVParser parser;
    private CountryMapper mapper;
    private UpdateService updateService;

    @BeforeEach
    void setup(){
        db = Mockito.mock(IRepositoryCatalog.class);
        parser = Mockito.mock(ICSVParser.class);
        mapper = Mockito.mock(CountryMapper.class);
        updateService = new UpdateService(db,parser,mapper);

        Hoi4CountryRepository hoi4CountryRepo = Mockito.mock(Hoi4CountryRepository.class);
        when(db.getHoi4CountryRepository()).thenReturn(hoi4CountryRepo);

    }

    @Test
    public void updateCountryDatabaseFromCSVFileTest(){
        List<Hoi4CountryDTO> countriesDto = List.of(
                new Hoi4CountryDTO().setName("Poland"),
                new Hoi4CountryDTO().setName("Japan"),
                new Hoi4CountryDTO().setName("Romania")
        );
        when(parser.parseCountriesFromCSV("countries.csv")).thenReturn(countriesDto);

        List<Hoi4Country> countriesAsEntities = List.of(
                new Hoi4Country().setName("Poland").setId(1L),
                new Hoi4Country().setName("Japan"),
                new Hoi4Country().setName("Romania")
        );

        when(db.getHoi4CountryRepository().findByName("Poland")).thenReturn(countriesAsEntities.get(0));

        when(mapper.toEntity(countriesDto.get(0))).thenReturn(countriesAsEntities.get(0));
        when(mapper.toEntity(countriesDto.get(1))).thenReturn(countriesAsEntities.get(1));
        when(mapper.toEntity(countriesDto.get(2))).thenReturn(countriesAsEntities.get(2));


        updateService.UpdateCountryDatabaseFromCSVFile("countries.csv");

        verify(db.getHoi4CountryRepository(),times(3)).findByName(anyString());
        verify(db.getHoi4CountryRepository()).saveAll(countriesAsEntities);
        verify(parser,times(1)).parseCountriesFromCSV(anyString());
    }

    @Test
    public void updateCountryDatabaseFromCSVFileExceptionsTest(){
        List<Hoi4CountryDTO> countriesDto = List.of();
        when(parser.parseCountriesFromCSV("countries.csv")).thenReturn(countriesDto);
        assertThrows(NothingWasParsedException.class, () -> updateService.UpdateCountryDatabaseFromCSVFile("countries.csv"));
    }
}
