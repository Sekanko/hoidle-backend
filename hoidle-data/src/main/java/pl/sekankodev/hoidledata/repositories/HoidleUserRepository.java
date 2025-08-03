package pl.sekankodev.hoidledata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sekankodev.hoidledata.model.HoidleUser;

import java.util.List;

public interface HoidleUserRepository extends JpaRepository<HoidleUser, Long> {
    HoidleUser findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM HoidleUser u WHERE u.todaysAttempts != 0 ORDER BY u.todaysAttempts LIMIT 5")
    List<HoidleUser> findTop5Attempts();

    @Query("SELECT u FROM HoidleUser u where u.streak != 0 ORDER BY u.streak DESC LIMIT 5")
    List<HoidleUser> findTop5WithLongestCurrentStreak();
}
