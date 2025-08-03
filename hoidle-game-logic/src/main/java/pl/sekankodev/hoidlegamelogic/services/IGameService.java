package pl.sekankodev.hoidlegamelogic.services;

import pl.sekankodev.hoidlegamelogic.modelDto.Colors;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;

import java.util.List;

public interface IGameService {
    HoidleDailyCountryDTO getOrSetTodaysCountry();
    List<Colors> guessResult(Hoi4CountryDTO guessedCountry);
}
