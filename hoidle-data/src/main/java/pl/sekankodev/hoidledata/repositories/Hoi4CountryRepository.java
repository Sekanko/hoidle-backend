package pl.sekankodev.hoidledata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sekankodev.hoidledata.model.Hoi4Country;


public interface Hoi4CountryRepository extends JpaRepository<Hoi4Country, Long> {
    @Query(value = "SELECT * FROM public.hoi4country ORDER BY RANDOM() LIMIT 1;", nativeQuery = true)
    Hoi4Country getRandomCountry();
    Hoi4Country findByName(String name);
}
