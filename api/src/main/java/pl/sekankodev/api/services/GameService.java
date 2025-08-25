package pl.sekankodev.api.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import pl.sekankodev.api.models.ResponseColor;
import pl.sekankodev.data.models.GameMode;
import pl.sekankodev.data.models.Hoi4Country;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DataService dataService;

    public Map<String, ResponseColor> guessClassic(Hoi4Country guessCountry){
        String dailyCountryName = dataService.getOrSetDailyCountryByGameMode(GameMode.CLASSIC).getCountryName();

        var fields = Arrays.stream(Hoi4Country.class.getDeclaredFields()).toList();
        Map<String, ResponseColor> result = new HashMap<>();

        if (guessCountry.getName().equals(dailyCountryName)){
            fields.forEach(f -> {
                if (!f.getName().equals("id") && !f.getName().equals("url")){
                    result.put(f.getName(),ResponseColor.GREEN);
                }
            });

            return result;
        }

        Hoi4Country dailyHoi4Country = dataService.getHoi4CountryByName(dailyCountryName);

        fields.forEach(field -> {
            try {
                field.setAccessible(true);

                var guessField = field.get(guessCountry);
                var dailyField = field.get(dailyHoi4Country);

                Validate.notNull(guessField, "Guessed country has fields with null values");
                Validate.notNull(dailyField, "Daily country has fields with null values");

                String fieldName = field.getName();
                var fieldType = field.getType();

                if (guessField.equals(dailyField)){
                    result.put(fieldName, ResponseColor.GREEN);
                } else if (List.class.isAssignableFrom(fieldType)) {
                    List<?> guessList = (List<?>) guessField;
                    List<?> dailyList = (List<?>) dailyField;

                    result.put(fieldName,
                            guessList.stream().anyMatch(dailyList::contains) ?
                                    ResponseColor.ORANGE :
                                    ResponseColor.RED
                    );
                } else if (fieldType.equals(int.class)) {
                    int guessValue = (Integer) guessField;
                    int dailyValue = (Integer) dailyField;

                    result.put(fieldName,
                            guessValue > dailyValue ?
                                    ResponseColor.LOWER_RED :
                                    ResponseColor.UPPER_RED
                    );
                } else {
                    result.put(fieldName, ResponseColor.RED);
                }
                field.setAccessible(false);

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
    public boolean isGuessedCountryCorrect(Hoi4Country guessCountry, GameMode gameMode){
        var dailyCountry = dataService.getOrSetDailyCountryByGameMode(gameMode);
        Validate.notNull(dailyCountry, "Getting Daily Country went wrong!");
        return dailyCountry.getCountryName().equals(guessCountry.getName());
    }

}
