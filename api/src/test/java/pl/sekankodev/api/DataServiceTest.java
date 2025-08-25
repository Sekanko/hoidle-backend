package pl.sekankodev.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.sekankodev.api.mappers.Hoi4CountryMapper;
import pl.sekankodev.api.mappers.HoidleDailyCountryMapper;
import pl.sekankodev.api.models.HoidleDailyCountryDto;
import pl.sekankodev.api.services.DataService;
import pl.sekankodev.data.models.GameMode;
import pl.sekankodev.data.models.Hoi4Country;
import pl.sekankodev.data.models.HoidleDailyCountry;
import pl.sekankodev.data.repositories.Hoi4CountryRepository;
import pl.sekankodev.data.repositories.HoidleDailyCountryRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DataServiceTest {
    private DataService dataService;
    private HoidleDailyCountryRepository dailyCountryRepository;
    private Hoi4CountryRepository hoi4CountryRepository;
    private HoidleDailyCountryMapper dailyCountryMapper;
    private Hoi4CountryMapper hoi4CountryMapper;


    @BeforeEach
    public void setUp(){
        dailyCountryRepository = Mockito.mock(HoidleDailyCountryRepository.class);
        hoi4CountryRepository = Mockito.mock(Hoi4CountryRepository.class);
        dailyCountryMapper = Mockito.mock(HoidleDailyCountryMapper.class);
        hoi4CountryMapper = Mockito.mock(Hoi4CountryMapper.class);
        dataService = new DataService(hoi4CountryRepository, dailyCountryRepository, dailyCountryMapper, hoi4CountryMapper);
    }

    @Test
    public void getAllHoi4CountriesSuccessfully(){
        var countries = List.of(
                new Hoi4Country().setName("Test Country 1"),
                new Hoi4Country().setName("Test Country 2")
        );
        when(hoi4CountryRepository.findAll()).thenReturn(countries);

        var returnedCountries = dataService.getAllHoi4Countries();
        assertEquals(countries, returnedCountries);
    }

    @Test
    public void getAllHoi4CountriesButThereAreNoCountries(){
        when(hoi4CountryRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(IllegalArgumentException.class,() -> dataService.getAllHoi4Countries());
    }

    @Test
    public void getAllHoi4CountriesButRepositoryReturnedNull(){
        when(hoi4CountryRepository.findAll()).thenReturn(null);
        assertThrows(NullPointerException.class,() -> dataService.getAllHoi4Countries());
    }

    @Test
    public void getExistingHoidleDailyCountry(){
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Warsaw"));
        GameMode gameMode = GameMode.CLASSIC;
        var dailyCountry = createDailyCountry(date, "poland", gameMode);
        var dailyDto = createDailyDto(date,"poland", gameMode);

        when(dailyCountryRepository.findByGameModeAndDate(gameMode,date)).thenReturn(dailyCountry);
        when(dailyCountryMapper.toDto(dailyCountry)).thenReturn(dailyDto);
        var returnedValue = dataService.getOrSetDailyCountryByGameMode(gameMode);

        assertEquals(dailyDto,returnedValue);
    }

    @Test
    public void getNewHoidleDailyCountry(){
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Warsaw"));
        GameMode gameMode = GameMode.CLASSIC;
        var dailyDto = createDailyDto(date,"poland", gameMode);
        var hoi4Country = new Hoi4Country().setName("poland").setUrl("poland.png");

        when(hoi4CountryRepository.findAll()).thenReturn(List.of(hoi4Country));
        when(dailyCountryRepository.findByGameModeAndDate(gameMode, date)).thenReturn(null);
        when(dailyCountryMapper.toDto(any())).thenReturn(dailyDto);

        var returnedValue = dataService.getOrSetDailyCountryByGameMode(gameMode);
        assertEquals(dailyDto, returnedValue);
        verify(dailyCountryRepository, times(1)).save(any());
        verify(hoi4CountryRepository, times(1)).findAll();
    }

    private HoidleDailyCountry createDailyCountry(LocalDate date, String countryName, GameMode gameMode) {
        return new HoidleDailyCountry()
                .setId(1L)
                .setDate(date)
                .setCountry(new Hoi4Country().setName(countryName).setUrl(countryName.toLowerCase() + ".png"))
                .setGameMode(gameMode);
    }

    private HoidleDailyCountryDto createDailyDto(LocalDate date,String countryName, GameMode gameMode) {
        return new HoidleDailyCountryDto()
                .setCountryName(countryName)
                .setDate(date)
                .setUrl(countryName.toLowerCase() + ".png")
                .setGameMode(gameMode);
    }
}

