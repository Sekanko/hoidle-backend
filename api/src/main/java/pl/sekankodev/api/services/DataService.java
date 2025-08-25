package pl.sekankodev.api.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import pl.sekankodev.api.models.HoidleDailyCountryDto;
import pl.sekankodev.api.mappers.HoidleDailyCountryMapper;
import pl.sekankodev.data.models.Hoi4Country;
import pl.sekankodev.data.models.HoidleDailyCountry;
import pl.sekankodev.data.models.GameMode;
import pl.sekankodev.data.repositories.Hoi4CountryRepository;
import pl.sekankodev.data.repositories.HoidleDailyCountryRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DataService {
    private final Hoi4CountryRepository hoi4CountryRepository;
    private final HoidleDailyCountryRepository dailyCountryRepository;
    private final HoidleDailyCountryMapper dailyCountryMapper;

    public Hoi4Country getHoi4CountryByName(String name){
        Hoi4Country country = hoi4CountryRepository.findByName(name);
        Validate.notNull(country, "Couldn't find country with name " + name);
        return country;
    }

    public List<Hoi4Country> getAllHoi4Countries(){
        List<Hoi4Country> countries = hoi4CountryRepository.findAll();
        Validate.notEmpty(countries, "Couldn't find any countries.");
        return countries;
    }
    public List<String> getFieldOfHoi4Country(){
        return Arrays.stream(Hoi4Country.class.getDeclaredFields()).map(Field::getName).toList();
    }

    public HoidleDailyCountryDto getOrSetDailyCountryByGameMode(GameMode gameMode){
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Warsaw"));
        HoidleDailyCountry dailyCountry = dailyCountryRepository.findByGameModeAndDate(gameMode, today);

        if (dailyCountry == null){
            List<Hoi4Country> countries = this.getAllHoi4Countries();
            Hoi4Country newDailyCountry = countries.get(
                    ThreadLocalRandom.current().nextInt(countries.size())
            );
            dailyCountry = new HoidleDailyCountry()
                    .setCountry(newDailyCountry)
                    .setDate(today)
                    .setGameMode(gameMode);
            dailyCountryRepository.save(dailyCountry);
        }

        return dailyCountryMapper.toDto(dailyCountry);
    }

    public String getDailyBorderUrl(){
        var country = getOrSetDailyCountryByGameMode(GameMode.BORDER);
        return country.getUrl();
    }
}
