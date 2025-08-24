import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sekankodev.model.Hoi4CountryRow;
import pl.sekankodev.parser.CsvParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvParserTest {
    private CsvParser csvParser;

    @BeforeEach
    void setUp () {csvParser = new CsvParser();}

    @Test
    public void loadingDataTest() throws URISyntaxException, IOException {
        List<Hoi4CountryRow> countries = CsvParser.getHoi4CountriesFromCsvFile();
        assertNotNull(countries, "List of countries cannot be null");
        assertTrue(countries.stream().allMatch(Objects::nonNull),"There cannot be null country in the list");
        assertTrue(countries.stream().allMatch(country -> {
            var fields = Hoi4CountryRow.class.getDeclaredFields();

            for (Field field : fields){
                field.setAccessible(true);
                try {
                    if (field.get(country) == null){
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Not possible exception");
                }
            }
            return true;
        }),"Countries cannot have any null fields");
    }

}
