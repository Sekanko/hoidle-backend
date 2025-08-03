package pl.sekankodev.hoidledataupdater.mappers;

import org.springframework.stereotype.Component;
import pl.sekankodev.hoidledata.model.Continent;
import pl.sekankodev.hoidledata.model.Faction;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.model.Ideology;
import pl.sekankodev.hoidledataupdater.contract.Hoi4CountryDTO;
import pl.sekankodev.hoidledataupdater.update_exceptions.MappingToEnumFailedException;

import java.util.List;

@Component
public class CountryMapper implements IMap<Hoi4CountryDTO, Hoi4Country> {

    @Override
    public Hoi4Country toEntity(Hoi4CountryDTO dtoEntity) {
        return new Hoi4Country()
                .setName(dtoEntity.getName())
                .setIdeology(Ideology.valueOf(prepareToEnumValue(dtoEntity.getIdeology())))
                .setContinents(convertListToEnum(dtoEntity.getContinents(), Continent.class))
                .setHistoricalFactions(convertListToEnum(dtoEntity.getHistoricalFactions(), Faction.class))
                .setFormableNations(dtoEntity.getFormableNations())
                .setResearchSlotsNumber(dtoEntity.getResearchSlotsNumber())
                .setNationalFocusTree(dtoEntity.hasNationalFocusTree())
                .setAccessToTheSea(dtoEntity.hasAccessToTheSea())
                .setResearchedTrain(dtoEntity.hasResearchedTrain());
    }
    private <T extends Enum<T>> List<T> convertListToEnum (List<String> inputList, Class<T> enumType){
        return inputList.stream()
                .map(el -> {
                    try {
                        return Enum.valueOf(enumType, prepareToEnumValue(el));
                    } catch (Exception e){
                        throw new MappingToEnumFailedException();
                    }
                })
                .sorted()
                .toList();
    }

    private String prepareToEnumValue(String input){
        return input.trim().toUpperCase().replaceAll(" |-", "_");
    }
}
