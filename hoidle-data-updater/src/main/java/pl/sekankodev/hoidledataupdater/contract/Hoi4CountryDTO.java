package pl.sekankodev.hoidledataupdater.contract;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.hoidledataupdater.parsers.converters.ToListConverter;

import java.util.List;

@Data
@Accessors(chain = true)
public class Hoi4CountryDTO {
    @CsvBindByName(column = "Name")
    private String name;
    @CsvCustomBindByName(column = "Continents", converter = ToListConverter.class)
    private List<String> continents;
    @CsvBindByName(column = "Ideology")
    private String ideology;
    @CsvCustomBindByName(column = "Historical faction", converter = ToListConverter.class)
    private List<String> historicalFactions;
    @CsvCustomBindByName(column = "Formable nation", converter = ToListConverter.class)
    private List<String> formableNations;
    @CsvBindByName(column = "Research slots")
    private byte researchSlotsNumber;
    @CsvBindByName(column = "National focus tree")
    private boolean nationalFocusTree;
    @CsvBindByName(column = "Access to the see")
    private boolean accessToTheSea;
    @CsvBindByName(column = "Train researched")
    private boolean researchedTrain;

    public boolean hasResearchedTrain() {
        return researchedTrain;
    }

    public boolean hasAccessToTheSea() {
        return accessToTheSea;
    }
    public boolean hasNationalFocusTree() {
        return nationalFocusTree;
    }
}
