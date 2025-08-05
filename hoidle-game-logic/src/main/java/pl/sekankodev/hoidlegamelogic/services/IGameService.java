package pl.sekankodev.hoidlegamelogic.services;

import pl.sekankodev.hoidledata.model.DailyType;
import pl.sekankodev.hoidlegamelogic.modelDto.Colors;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;

import java.util.List;

public interface IGameService {
    HoidleDailyCountryDTO getOrSetCountry(DailyType dailyType);
    List<Colors> checkGuessForClassic(Hoi4CountryDTO guessedCountry);
    boolean checkGuessForBorders(Hoi4CountryDTO guessedCountry);
}
