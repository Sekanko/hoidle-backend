package pl.sekankodev.hoidleusermanagement.model;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.hoidledata.model.Role;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class HoidleUserResponseDTO {
    private String username;
    private String email;
    private Role role;
    private int streak;
    private int longestStreak;
    private LocalDate lastWin;
    private int todaysAttempts;
}