package pl.sekankodev.hoidlegamelogic.mappers;

import org.springframework.stereotype.Component;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.model.HoidleDailyCountry;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;

import java.util.List;
import java.util.function.Function;
@Component
public class Mapper {
    public static Hoi4CountryDTO mapCountry(Hoi4Country country) {
        return new Hoi4CountryDTO()
                .setName(country.getName())
                .setContinents(country.getContinents())
                .setIdeology(country.getIdeology())
                .setHistoricalFaction(country.getHistoricalFactions())
                .setResearchSlotsNumber(country.getResearchSlotsNumber())
                .setNationalFocusTree(country.isNationalFocusTree())
                .setAccessToTheSea(country.isAccessToTheSea())
                .setResearchedTrain(country.isResearchedTrain())
                .setFormableNation(country.getFormableNations());
    }

    public static HoidleDailyCountryDTO mapHoidleDailyCountry(HoidleDailyCountry country) {
        return new HoidleDailyCountryDTO()
                .setCountryName(country.getCountry().getName())
                .setDate(country.getDate());
    }

    public static <T, R> List<R> mapList(List<T> tList, Function<T,R> mapperFunction){
        return tList.stream()
                .map(mapperFunction)
                .toList();
    }
}
