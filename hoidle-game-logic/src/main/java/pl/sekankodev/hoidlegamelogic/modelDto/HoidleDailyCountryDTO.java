package pl.sekankodev.hoidlegamelogic.modelDto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
@Data
@Accessors(chain = true)
public class HoidleDailyCountryDTO {
    private String countryName;
    private LocalDate date;
}
