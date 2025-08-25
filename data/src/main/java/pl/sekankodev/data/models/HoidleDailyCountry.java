package pl.sekankodev.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
public class HoidleDailyCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Hoi4Country country;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private GameMode gameMode;
}
