package pl.sekankodev.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sekankodev.data.models.Hoi4Country;

public interface Hoi4CountryRepository extends JpaRepository<Hoi4Country, Long> {
    Hoi4Country findByName(String name);
}
