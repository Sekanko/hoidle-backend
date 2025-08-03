package hoidlegamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.sekankodev.hoidledata.data_exceptions.CountryNotFoundException;
import pl.sekankodev.hoidledata.data_exceptions.NoDrawResultException;
import pl.sekankodev.hoidledata.model.*;
import pl.sekankodev.hoidledata.repositories.Hoi4CountryRepository;
import pl.sekankodev.hoidledata.repositories.HoidleDailyCountryRepository;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidlegamelogic.exceptions.CheckingGuessFailedException;
import pl.sekankodev.hoidlegamelogic.modelDto.Colors;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;
import pl.sekankodev.hoidlegamelogic.services.GameService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {
    private IRepositoryCatalog db;
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        this.db = Mockito.mock(IRepositoryCatalog.class);
        this.gameService = new GameService(db);

        HoidleDailyCountryRepository HoidleDailyRepo = mock(HoidleDailyCountryRepository.class);
        when(this.db.getHoidleDailyCountryRepository()).thenReturn(HoidleDailyRepo);
        Hoi4CountryRepository Hoi4CountryRepo = mock(Hoi4CountryRepository.class);
        when(this.db.getHoi4CountryRepository()).thenReturn(Hoi4CountryRepo);
    }

    @Test
    public void getOrSetCountryWhenTodaysCountryIsNotSet(){

        when(db.getHoidleDailyCountryRepository().findByDate(LocalDate.now())).thenReturn(null);

        Hoi4Country hoi4Country = new Hoi4Country().setName("Japan");
        when(db.getHoi4CountryRepository().getRandomCountry()).thenReturn(hoi4Country);

        HoidleDailyCountryDTO returnCountry = gameService.getOrSetTodaysCountry();

        verify(db.getHoidleDailyCountryRepository(), times(1)).save(any());
        assertEquals("Japan",returnCountry.getCountryName());
    }

    @Test
    public void getOrSetCountryWhenTodaysCountryIsSet(){
        Hoi4Country hoi4Country = new Hoi4Country().setName("Japan");
        HoidleDailyCountry todaysCountry = new HoidleDailyCountry();
        todaysCountry.setCountry(hoi4Country);

        when(db.getHoidleDailyCountryRepository().findByDate(LocalDate.now())).thenReturn(todaysCountry);

        HoidleDailyCountryDTO returnCountry = gameService.getOrSetTodaysCountry();
        assertEquals("Japan",returnCountry.getCountryName());
    }

    @Test
    public void getOrSetCountryExceptions(){
        when(db.getHoidleDailyCountryRepository().findByDate(LocalDate.now())).thenReturn(null);

        when(db.getHoi4CountryRepository().getRandomCountry()).thenReturn(null);
        assertThrows(NoDrawResultException.class, () -> gameService.getOrSetTodaysCountry());
    }

    @Test
    public void guessedCorrectCountry(){
        Hoi4Country hoi4Country = new Hoi4Country().setName("Japan");
        HoidleDailyCountry todaysCountry = new HoidleDailyCountry();
        todaysCountry.setCountry(hoi4Country);

        when(db.getHoidleDailyCountryRepository().findByDate(LocalDate.now())).thenReturn(todaysCountry);
        Hoi4CountryDTO guess = new Hoi4CountryDTO().setName("Japan");
        var result = gameService.guessResult(guess);
        boolean allGreen = result.stream().allMatch(col -> col == Colors.GREEN);

        assertTrue(allGreen);
    }

    @Test
    public void guessedWrongCountry(){
        Hoi4Country hoi4Country = new Hoi4Country().setName("Japan")
                .setIdeology(Ideology.FASCIST)
                .setContinents(List.of(Continent.OCEANIA, Continent.ASIA))
                .setAccessToTheSea(true)
                .setTrainResearched(true)
                .setFormableNations(List.of("None"))
                .setHistoricalFactions(List.of(Faction.AXIS))
                .setNationalFocusTree(true)
                .setStability(1)
                .setCivilianFactories(20)
                .setUrl("thi is url");
        HoidleDailyCountry todaysCountry = new HoidleDailyCountry();
        todaysCountry.setCountry(hoi4Country);

        when(db.getHoidleDailyCountryRepository().findByDate(LocalDate.now())).thenReturn(todaysCountry);

        when(db.getHoi4CountryRepository().findByName("Japan")).thenReturn(hoi4Country);


        Hoi4CountryDTO guess = new Hoi4CountryDTO()
                .setName("Romania")
                .setIdeology(Ideology.DEMOCRATIC)
                .setContinents(List.of(Continent.EUROPE))
                .setAccessToTheSea(true)
                .setHistoricalFaction(List.of(Faction.ALLIES, Faction.AXIS))
                .setFormableNation(List.of("None"))
                .setNationalFocusTree(true)
                .setResearchedTrain(true)
                .setStability(1)
                .setCivilianFactories(20)
                .setUrl("thi is not correct url");

        List<Colors> expectedResult = List.of(
                Colors.RED,
                Colors.RED,
                Colors.RED,
                Colors.ORANGE,
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN,
                Colors.RED
        );

        List<Colors> result = gameService.guessResult(guess);

        assertEquals(expectedResult, result);
    }

    @Test
    public void guessCountryExceptions(){
        Hoi4Country hoi4Country = new Hoi4Country().setName("Japan")
                .setIdeology(Ideology.FASCIST)
                .setContinents(List.of(Continent.OCEANIA, Continent.ASIA))
                .setAccessToTheSea(true)
                .setTrainResearched(true)
                .setFormableNations(List.of("None"))
                .setHistoricalFactions(List.of(Faction.AXIS))
                .setNationalFocusTree(true);

        Hoi4CountryDTO guess = new Hoi4CountryDTO()
                .setName("Romania")
                .setIdeology(null)
                .setContinents(List.of(Continent.EUROPE))
                .setAccessToTheSea(true)
                .setHistoricalFaction(List.of(Faction.ALLIES, Faction.AXIS))
                .setFormableNation(List.of("None"))
                .setNationalFocusTree(true)
                .setResearchedTrain(true);

        HoidleDailyCountry todaysCountry = new HoidleDailyCountry();
        todaysCountry.setCountry(hoi4Country);

        when(db.getHoidleDailyCountryRepository().findByDate(LocalDate.now())).thenReturn(todaysCountry);

        when(db.getHoi4CountryRepository().findByName("Japan")).thenReturn(null);

        assertThrows(CountryNotFoundException.class, () -> this.gameService.guessResult(guess));

        when(db.getHoi4CountryRepository().findByName("Japan")).thenReturn(hoi4Country);

        assertThrows(CheckingGuessFailedException.class, () -> this.gameService.guessResult(guess));
    }
}
