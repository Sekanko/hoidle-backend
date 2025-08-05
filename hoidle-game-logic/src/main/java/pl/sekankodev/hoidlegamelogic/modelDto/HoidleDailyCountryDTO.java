package pl.sekankodev.hoidlegamelogic.modelDto;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.hoidledata.model.DailyType;

import java.time.LocalDate;
@Data
@Accessors(chain = true)
public class HoidleDailyCountryDTO {
    private String countryName;
    private String url;
    private LocalDate date;
    private DailyType dailyType;
}
