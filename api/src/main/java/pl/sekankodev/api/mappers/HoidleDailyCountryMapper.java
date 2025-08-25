package pl.sekankodev.api.mappers;

import org.springframework.stereotype.Component;
import pl.sekankodev.api.models.HoidleDailyCountryDto;
import pl.sekankodev.data.models.HoidleDailyCountry;

@Component
public class HoidleDailyCountryMapper {
    public HoidleDailyCountryDto toDto(HoidleDailyCountry dailyCountry){
        var hoi4Country = dailyCountry.getCountry();
        return new HoidleDailyCountryDto()
                .setCountryName(hoi4Country.getName())
                .setDate(dailyCountry.getDate())
                .setUrl(hoi4Country.getUrl())
                .setGameMode(dailyCountry.getGameMode());
    }

}
