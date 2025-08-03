package pl.sekankodev.hoidledataupdater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sekankodev.hoidledataupdater.parsers.CSVParser;
import pl.sekankodev.hoidledataupdater.update_exceptions.CouldNotParseException;

import static org.junit.jupiter.api.Assertions.*;

public class CSVParserTest {
    private CSVParser csvParser;

    @BeforeEach
    public void setup(){
        csvParser = new CSVParser();
    }

    @Test
    public void parseCountriesFromCSVWithWrongFileNameTest(){
        assertThrows(CouldNotParseException.class, () -> csvParser.parseCountriesFromCSV("wrongFileName.csv") );
    }

    @Test
    public void parseCountriesFromCSVWithCorrectFileNameTest() {
        String fileName = "test-countries.csv";
        var result = csvParser.parseCountriesFromCSV(fileName);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Kingdom of Afghanistan", result.get(0).getName());
        assertEquals("Albanian Kingdom", result.get(1).getName());
    }
}
