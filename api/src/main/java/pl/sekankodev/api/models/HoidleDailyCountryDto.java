package pl.sekankodev.api.models;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.data.models.GameMode;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class HoidleDailyCountryDto {
    private String countryName;
    private GameMode gameMode;
    private String url;
    private LocalDate date;
}
