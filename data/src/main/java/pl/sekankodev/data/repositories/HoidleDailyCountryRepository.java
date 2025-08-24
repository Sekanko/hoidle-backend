package pl.sekankodev.data.repositories;

import pl.sekankodev.data.models.HoidleDailyCountry;
import org.springframework.data.jpa.repository.JpaRepository;

interface HoidleDailyCountryRepository extends JpaRepository<HoidleDailyCountry, Long> {
}
