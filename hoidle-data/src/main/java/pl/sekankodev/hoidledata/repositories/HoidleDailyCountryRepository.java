package pl.sekankodev.hoidledata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sekankodev.hoidledata.model.DailyType;
import pl.sekankodev.hoidledata.model.HoidleDailyCountry;

import java.time.LocalDate;

public interface HoidleDailyCountryRepository extends JpaRepository<HoidleDailyCountry, Long> {
    HoidleDailyCountry findByDateAndDailyType(LocalDate date, DailyType dailyType);

    HoidleDailyCountry findByDate(LocalDate today);
}
