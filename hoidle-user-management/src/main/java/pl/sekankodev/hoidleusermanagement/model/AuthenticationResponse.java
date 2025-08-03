package pl.sekankodev.hoidleusermanagement.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationResponse {
    private HoidleUserResponseDTO user;
    private String token;
}
