package pl.sekankodev.hoidlegamelogic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sekankodev.hoidledata.data_exceptions.CountryNotFoundException;
import pl.sekankodev.hoidledata.data_exceptions.GenericDbException;
import pl.sekankodev.hoidledata.data_exceptions.NoDrawResultException;
import pl.sekankodev.hoidledata.model.DailyType;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.model.HoidleDailyCountry;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidlegamelogic.exceptions.CheckingGuessFailedException;
import pl.sekankodev.hoidlegamelogic.exceptions.ObjectFieldIsNullException;
import pl.sekankodev.hoidlegamelogic.mappers.Mapper;
import pl.sekankodev.hoidlegamelogic.modelDto.Colors;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;
import pl.sekankodev.hoidlegamelogic.modelDto.HoidleDailyCountryDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final IRepositoryCatalog db;

    @Override
    public List<Colors> checkGuessForClassic(Hoi4CountryDTO guessedCountry) {
       String todaysCountryName = getOrSetCountry(DailyType.CLASSIC).getCountryName();

       var fields = Hoi4CountryDTO.class.getDeclaredFields();

       if (guessedCountry.getName().equals(todaysCountryName)){
           return Collections.nCopies(fields.length,Colors.GREEN);
       }

       Hoi4Country todaysCountry = db.getHoi4CountryRepository().findByName(todaysCountryName);

       if (todaysCountry == null){
           throw new CountryNotFoundException("Today's country not found");
       }

       Hoi4CountryDTO todaysCountryDto = Mapper.mapCountry(todaysCountry);
       
       List<Colors> result = new ArrayList<>();

       try {
           for (var field : fields) {
               field.setAccessible(true);

               var guessedCountryField = field.get(guessedCountry);
               var hoi4CountryField = field.get(todaysCountryDto);

               if (guessedCountryField == null || hoi4CountryField == null){
                   throw new ObjectFieldIsNullException(field.getName());
               }
               
               if(guessedCountryField.equals(hoi4CountryField)){
                   result.add(Colors.GREEN);
               } else if (List.class.isAssignableFrom(field.getType())) {
                   List<?> guessedList = (List<?>) guessedCountryField;
                   List<?> todaysDailyList = (List<?>) hoi4CountryField;

                   boolean hasCommon = guessedList.stream()
                           .anyMatch(todaysDailyList::contains);

                   if (hasCommon) {
                       result.add(Colors.ORANGE);
                   } else {
                       result.add(Colors.RED);
                   }
               } else if(field.getType().equals(int.class)) {
                   int guessedIntValue = (Integer) guessedCountryField;
                   int todaysIntValue = (Integer) hoi4CountryField;
                   if (guessedIntValue > todaysIntValue) {
                       result.add(Colors.LOWER_RED);
                   } else if (guessedIntValue < todaysIntValue) {
                       result.add(Colors.UPPER_RED);
                   }
               } else {
                   result.add(Colors.RED);
               }

               field.setAccessible(false);
           }
           return result;
       } catch (Exception e) {
           throw new CheckingGuessFailedException();
       }
    }

    @Override
    public boolean checkGuessForBorders(Hoi4CountryDTO guessedCountry) {
        return getOrSetCountry(DailyType.BORDER).getCountryName().equals(guessedCountry.getName());
    }

    @Override
    public HoidleDailyCountryDTO getOrSetCountry(DailyType dailyType) {
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Warsaw"));
        var todaysCountry = db.getHoidleDailyCountryRepository().findByDateAndDailyType(today, dailyType);

        if (todaysCountry == null) {
            todaysCountry = new HoidleDailyCountry();
            todaysCountry.setDate(today);
            todaysCountry.setDailyType(dailyType);

            var countryList = db.getHoi4CountryRepository().findAll();
            var randomHoi4Country = countryList.get(ThreadLocalRandom.current().nextInt(countryList.size()));

            if (randomHoi4Country == null) {
                throw new NoDrawResultException();
            }

            todaysCountry.setCountry(randomHoi4Country);

            try {
                db.getHoidleDailyCountryRepository().save(todaysCountry);
            } catch (Exception e) {
                throw new GenericDbException();
            }
        }

        return Mapper.mapHoidleDailyCountry(todaysCountry);
    }

}