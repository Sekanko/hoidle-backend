package pl.sekankodev.data.repositories;

import pl.sekankodev.data.models.Hoi4Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Hoi4CountryRepository extends JpaRepository<Hoi4Country, Long> {
}
