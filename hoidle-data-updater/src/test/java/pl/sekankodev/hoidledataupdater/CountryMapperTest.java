package pl.sekankodev.hoidledataupdater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sekankodev.hoidledata.model.Continent;
import pl.sekankodev.hoidledata.model.Faction;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.model.Ideology;
import pl.sekankodev.hoidledataupdater.contract.Hoi4CountryDTO;
import pl.sekankodev.hoidledataupdater.mappers.CountryMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryMapperTest {
    private CountryMapper countryMapper;

    @BeforeEach
    public void setup(){
        countryMapper = new CountryMapper();
    }

    @Test
    public void toEntityTest(){
        Hoi4CountryDTO countryDto = new Hoi4CountryDTO()
                .setName("Romania")
                .setIdeology(Ideology.DEMOCRATIC.toString())
                .setContinents(List.of(Continent.EUROPE.toString()))
                .setHistoricalFactions(List.of(
                        Faction.AXIS.toString(),
                        Faction.ALLIES.toString()
                ))
                .setAccessToTheSea(true)
                .setResearchedTrain(true)
                .setNationalFocusTree(true)
                .setFormableNations(List.of("None"))
                .setResearchSlotsNumber((byte)3);

        Hoi4Country expectedCountry = new Hoi4Country()
                .setName("Romania")
                .setIdeology(Ideology.DEMOCRATIC)
                .setContinents(List.of(Continent.EUROPE))
                .setHistoricalFactions(List.of(
                        Faction.ALLIES,
                        Faction.AXIS
                ))
                .setAccessToTheSea(true)
                .setResearchedTrain(true)
                .setNationalFocusTree(true)
                .setFormableNations(List.of("None"))
                .setResearchSlotsNumber((byte)3);

        var result = countryMapper.toEntity(countryDto);

        assertEquals(result, expectedCountry);
    }
}
