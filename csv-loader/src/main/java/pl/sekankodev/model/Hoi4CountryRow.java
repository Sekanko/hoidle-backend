package pl.sekankodev.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.converter.ToListConverter;

import java.util.List;

@Data
@Accessors(chain = true)
public class Hoi4CountryRow {
    @CsvBindByName(column = "Name")
    private String name;
    @CsvCustomBindByName(column = "Continents", converter = ToListConverter.class)
    private List<String> continents;
    @CsvBindByName(column = "Ideology")
    private String ideology;
    @CsvCustomBindByName(column = "Historical faction", converter = ToListConverter.class)
    private List<String> historicalFactions;
    @CsvCustomBindByName(column = "Formable nations", converter = ToListConverter.class)
    private List<String> formableNations;
    @CsvBindByName(column = "National focus tree")
    private boolean nationalFocusTree;
    @CsvBindByName(column = "Access to the sea")
    private boolean accessToTheSea;
    @CsvBindByName(column = "Train researched")
    private boolean researchedTrain;
    @CsvBindByName(column = "Stability")
    private int stability;
    @CsvBindByName(column = "Civilian Factories")
    private int civilianFactories;
    @CsvBindByName(column = "url")
    private String url;

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
