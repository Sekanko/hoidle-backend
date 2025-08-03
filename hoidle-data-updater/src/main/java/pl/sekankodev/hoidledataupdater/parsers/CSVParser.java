package pl.sekankodev.hoidledataupdater.parsers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pl.sekankodev.hoidledataupdater.contract.Hoi4CountryDTO;
import pl.sekankodev.hoidledataupdater.update_exceptions.CouldNotParseException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CSVParser implements ICSVParser{
    @Override
    public List<Hoi4CountryDTO> parseCountriesFromCSV(String fileName) {
        Resource resource = new ClassPathResource(fileName);

        try (InputStream is = resource.getInputStream();
             Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            CsvToBean<Hoi4CountryDTO> csvToBean = new CsvToBeanBuilder<Hoi4CountryDTO>(reader)
                    .withType(Hoi4CountryDTO.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (Exception e) {
            throw new CouldNotParseException();
        }
    }
}
