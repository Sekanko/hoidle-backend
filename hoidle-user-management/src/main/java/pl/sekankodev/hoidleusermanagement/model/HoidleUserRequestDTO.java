package pl.sekankodev.hoidleusermanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import pl.sekankodev.hoidledata.model.Role;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class HoidleUserRequestDTO {
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private Role role;
    private int streak;
    private int longestStreak;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastWin;
    private int todaysAttempts;
}