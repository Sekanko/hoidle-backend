package pl.sekankodev.api.mappers;

import org.springframework.stereotype.Component;
import pl.sekankodev.data.models.Continent;
import pl.sekankodev.data.models.Faction;
import pl.sekankodev.data.models.Hoi4Country;
import pl.sekankodev.data.models.Ideology;
import pl.sekankodev.model.Hoi4CountryRow;

import java.util.List;

@Component
public class Hoi4CountryMapper {
    public List<Hoi4Country> fromCsvRowsToEntity(List<Hoi4CountryRow> rows){
        return rows.stream().map(row ->
            new Hoi4Country()
                    .setName(row.getName())
                    .setStability(row.getStability())
                    .setAccessToTheSea(row.hasAccessToTheSea())
                    .setCivilianFactories(row.getCivilianFactories())
                    .setContinents(convertListToEnum(row.getContinents(), Continent.class))
                    .setFormableNations(row.getFormableNations())
                    .setHistoricalFactions(convertListToEnum(row.getHistoricalFactions(), Faction.class))
                    .setIdeology(Ideology.valueOf(prepareToEnumValue(row.getIdeology())))
                    .setNationalFocusTree(row.hasNationalFocusTree())
                    .setTrainResearched(row.hasResearchedTrain())
                    .setUrl(row.getUrl())
        ).toList();
    }

    private <T extends Enum<T>> List<T> convertListToEnum (List<String> inputList, Class<T> enumType){
        return inputList.stream()
                .map(el -> {
                    try {
                        return Enum.valueOf(enumType, prepareToEnumValue(el));
                    } catch (Exception e){
                        throw new RuntimeException("String to Enum error");
                    }
                })
                .sorted()
                .toList();
    }

    private String prepareToEnumValue(String input){
        return input.trim().toUpperCase().replaceAll(" |-", "_");
    }
}
