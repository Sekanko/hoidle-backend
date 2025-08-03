package pl.sekankodev.hoidledataupdater.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sekankodev.hoidledata.data_exceptions.GenericDbException;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidledataupdater.contract.Hoi4CountryDTO;
import pl.sekankodev.hoidledataupdater.mappers.CountryMapper;
import pl.sekankodev.hoidledataupdater.parsers.ICSVParser;
import pl.sekankodev.hoidledataupdater.update_exceptions.NothingWasParsedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final IRepositoryCatalog db;
    private final ICSVParser parser;
    private final CountryMapper mapper;

    public void UpdateCountryDatabaseFromCSVFile(String fileName) {
        List<Hoi4CountryDTO> countriesDto = parser.parseCountriesFromCSV(fileName);

        if (countriesDto.isEmpty()) {
            throw new NothingWasParsedException();
        }

        List<Hoi4Country> countriesAsEntities = countriesDto.stream()
                .map(country -> {
                    var hoi4Country = mapper.toEntity(country);
                    var hoi4CountryDB = db.getHoi4CountryRepository().findByName(country.getName());
                    if (hoi4CountryDB != null) {
                        hoi4Country.setId(hoi4CountryDB.getId());
                    }
                    return hoi4Country;
                })
                .toList();
        try{
            db.getHoi4CountryRepository().saveAll(countriesAsEntities);
        } catch (Exception e){
            throw new GenericDbException();
        }
    }
}
