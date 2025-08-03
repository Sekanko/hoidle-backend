package hoidlegamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pl.sekankodev.hoidlegamelogic.controllers.GameController;
import pl.sekankodev.hoidlegamelogic.modelDto.Colors;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;
import pl.sekankodev.hoidlegamelogic.services.GameService;
import pl.sekankodev.hoidlegamelogic.services.IServiceCatalog;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    private GameController gameController;
    private IServiceCatalog serviceCatalog;

    @BeforeEach
    void setUp() {
        this.serviceCatalog = Mockito.mock(IServiceCatalog.class);
        this.gameController = new GameController(serviceCatalog);

        GameService gameService = Mockito.mock(GameService.class);
        when(this.serviceCatalog.getGameService()).thenReturn(gameService);
    }

    @Test
    public void getTodaysCountryTest(){
        HoidleDailyCountryDTO hoidleDailyCountryDTO = new HoidleDailyCountryDTO();
        hoidleDailyCountryDTO.setCountryName("France");

        when(serviceCatalog.getGameService().getOrSetTodaysCountry()).thenReturn(hoidleDailyCountryDTO);

        var result = gameController.getCountryOfTheDay();
        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals(hoidleDailyCountryDTO, result.getBody());
    }

    @Test
    public void guessCountryTest(){
        Hoi4CountryDTO guess = new Hoi4CountryDTO().setName("France");
        List<Colors> colors = List.of(
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN,
                Colors.ORANGE,
                Colors.RED,
                Colors.GREEN,
                Colors.GREEN,
                Colors.GREEN
        );

        when(serviceCatalog.getGameService().guessResult(guess)).thenReturn(colors);
        var result = gameController.guessResult(guess);
        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals(colors, result.getBody());
    }
}
