package pl.sekankodev.hoidledata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sekankodev.hoidledata.model.HoidleDailyCountry;

import java.time.LocalDate;

public interface HoidleDailyCountryRepository extends JpaRepository<HoidleDailyCountry, Long> {
    HoidleDailyCountry findByDate(LocalDate date);
}
