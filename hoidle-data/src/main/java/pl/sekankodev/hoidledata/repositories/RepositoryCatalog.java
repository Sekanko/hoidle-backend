package pl.sekankodev.hoidledata.repositories;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@RequiredArgsConstructor
public class RepositoryCatalog implements IRepositoryCatalog {
    private final Hoi4CountryRepository hoi4CountryRepository;
    private final HoidleDailyCountryRepository hoidleDailyCountryRepository;
    private final HoidleUserRepository hoidleUserRepository;
}
