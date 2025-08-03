package pl.sekankodev.hoidlegamelogic.modelDto;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.hoidledata.model.Continent;
import pl.sekankodev.hoidledata.model.Faction;
import pl.sekankodev.hoidledata.model.Ideology;

import java.util.List;
@Data
@Accessors(chain = true)
public class Hoi4CountryDTO {
    private String name;
    private List<Continent> continents;
    private Ideology ideology;
    private List<Faction> historicalFaction;
    private List<String> formableNation;
    private boolean nationalFocusTree;
    private boolean accessToTheSea;
    private boolean researchedTrain;
    private int stability;
    private int civilianFactories;
    private String url;
}
