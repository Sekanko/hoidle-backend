package pl.sekankodev.hoidledataupdater.parsers;

import pl.sekankodev.hoidledataupdater.contract.Hoi4CountryDTO;

import java.util.List;

public interface ICSVParser {
    List<Hoi4CountryDTO> parseCountriesFromCSV(String fileName);
}
