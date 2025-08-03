package pl.sekankodev.hoidlegamelogic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.sekankodev.hoidledata.data_exceptions.CountryNotFoundException;
import pl.sekankodev.hoidledata.model.Hoi4Country;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidlegamelogic.mappers.Mapper;
import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Hoi4CountryService  implements IHoi4CountryService {
    private final IRepositoryCatalog db;

    @Override
    @Cacheable("hoi4Countries")
    public List<Hoi4CountryDTO> getHoi4Countries() {
        List<Hoi4Country> allCountries = db.getHoi4CountryRepository().findAll();

        if (allCountries.isEmpty()) {
            throw new CountryNotFoundException();
        }

        return Mapper.mapList(allCountries,Mapper::mapCountry);
    }

}
