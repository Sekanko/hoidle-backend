package pl.sekankodev.hoidlegamelogic.services;

import pl.sekankodev.hoidlegamelogic.modelDto.Hoi4CountryDTO;

import java.util.List;

public interface IHoi4CountryService {
    List<Hoi4CountryDTO> getHoi4Countries();
}
