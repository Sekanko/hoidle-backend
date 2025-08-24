package pl.sekankodev.parser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import pl.sekankodev.model.Hoi4CountryRow;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CsvParser {
    public static List<Hoi4CountryRow> getHoi4CountriesFromCsvFile() throws URISyntaxException, IOException {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(CsvParser.class.getResourceAsStream("/data/countries.csv"))
        )) {
            CsvToBean<Hoi4CountryRow> csvToBean = new CsvToBeanBuilder<Hoi4CountryRow>(reader)
                    .withType(Hoi4CountryRow.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }
}