package pl.sekankodev.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.sekankodev.api.models.HoidleDailyCountryDto;
import pl.sekankodev.api.models.ResponseColor;
import pl.sekankodev.api.services.DataService;
import pl.sekankodev.api.services.GameService;
import pl.sekankodev.data.models.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameServiceTest {
    private GameService gameService;
    private DataService dataService;
    private List<Hoi4Country> exampleCountries;
    private List<String> fieldNames;

    @BeforeEach
    public void setUp(){
        dataService = Mockito.mock(DataService.class);
        gameService = new GameService(dataService);
        exampleCountries = List.of(
                new Hoi4Country()
                        .setId(1L)
                        .setName("Poland")
                        .setNationalFocusTree(true)
                        .setAccessToTheSea(true)
                        .setTrainResearched(true)
                        .setCivilianFactories(20)
                        .setFormableNations(List.of("Polish-Lithuania Common-wealth"))
                        .setContinents(List.of(Continent.EUROPE))
                        .setIdeology(Ideology.NON_ALIGNED)
                        .setUrl("poland.png")
                        .setHistoricalFactions(List.of(Faction.ALLIES))
                        .setStability(50),
                new Hoi4Country()
                        .setId(2L)
                        .setName("Japan")
                        .setNationalFocusTree(true)
                        .setAccessToTheSea(true)
                        .setTrainResearched(true)
                        .setCivilianFactories(30)
                        .setFormableNations(List.of("None"))
                        .setContinents(List.of(Continent.ASIA, Continent.OCEANIA))
                        .setIdeology(Ideology.FASCIST)
                        .setUrl("japan.png")
                        .setHistoricalFactions(List.of(Faction.AXIS))
                        .setStability(80),
                new Hoi4Country()
                        .setId(1L)
                        .setName("Soviet Union")
                        .setNationalFocusTree(true)
                        .setAccessToTheSea(true)
                        .setTrainResearched(true)
                        .setCivilianFactories(100)
                        .setFormableNations(List.of("None"))
                        .setContinents(List.of(Continent.EUROPE, Continent.ASIA))
                        .setIdeology(Ideology.COMMUNIST)
                        .setUrl("soviet.png")
                        .setHistoricalFactions(List.of(Faction.ALLIES))
                        .setStability(10)
        );

        fieldNames = Arrays.stream(Hoi4Country.class.getDeclaredFields()).toList().stream().map(Field::getName).toList();
    }

    @Test
    public void guessCorrectCountry(){
        when(dataService.getOrSetDailyCountryByGameMode(GameMode.CLASSIC))
                .thenReturn(new HoidleDailyCountryDto().setCountryName("Poland"));
        var result = gameService.guessClassic(exampleCountries.get(0));
        assertNotNull(result);
        assertTrue(result.values().stream().allMatch(color -> color.equals(ResponseColor.GREEN)));
        assertFalse(result.containsKey("id") || result.containsKey("url"));
    }

    @Test
    public void guessIncorrectCountry(){
        when(dataService.getOrSetDailyCountryByGameMode(GameMode.CLASSIC))
                .thenReturn(new HoidleDailyCountryDto().setCountryName("Soviet Union"));

        when(dataService.getHoi4CountryByName("Soviet Union")).thenReturn(exampleCountries.get(2));

        var result = gameService.guessClassic(exampleCountries.get(0));

        assertNotNull(result);
        assertEquals(ResponseColor.ORANGE, result.get("continents"));
        assertEquals(ResponseColor.GREEN, result.get("historicalFactions"));
        assertEquals(ResponseColor.LOWER_RED, result.get("stability"));
        assertEquals(ResponseColor.UPPER_RED, result.get("civilianFactories"));
        assertFalse(result.containsKey("id") || result.containsKey("url"));

    }

    @Test
    public void someOfHoi4CountryFieldAreNull(){
        when(dataService.getOrSetDailyCountryByGameMode(GameMode.CLASSIC))
                .thenReturn(new HoidleDailyCountryDto().setCountryName("Soviet Union"));

        when(dataService.getHoi4CountryByName("Soviet Union")).thenReturn(exampleCountries.get(2).setName(null));
        assertThrows(NullPointerException.class, () -> gameService.guessClassic(exampleCountries.get(0)));

        when(dataService.getHoi4CountryByName("Soviet Union")).thenReturn(exampleCountries.get(2).setName("Soviet Union"));
        assertThrows(NullPointerException.class, () -> gameService.guessClassic(exampleCountries.get(0).setName(null)));


    }

}
