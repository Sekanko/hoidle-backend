package pl.sekankodev.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sekankodev.data.models.GameMode;
import pl.sekankodev.data.models.HoidleDailyCountry;

import java.time.LocalDate;

public interface HoidleDailyCountryRepository extends JpaRepository<HoidleDailyCountry, Long> {
    HoidleDailyCountry findByGameModeAndDate(GameMode gameMode, LocalDate date);
}
