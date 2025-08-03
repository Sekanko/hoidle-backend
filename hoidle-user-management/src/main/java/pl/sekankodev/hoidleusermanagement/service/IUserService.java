package pl.sekankodev.hoidleusermanagement.service;

import pl.sekankodev.hoidleusermanagement.model.AuthenticationResponse;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserResponseDTO;

import java.util.List;

public interface IUserService {
    Long createUser(HoidleUserRequestDTO requestUser);
    Long updateUser(HoidleUserRequestDTO requestUser);
    Long deleteUser(HoidleUserRequestDTO requestUser);
    AuthenticationResponse logInUser(HoidleUserRequestDTO requestUser);

    List<HoidleUserResponseDTO> getTop5UsersAttempts();
    List<HoidleUserResponseDTO> getTop5UsersLongestCurrentStreak();

    HoidleUserResponseDTO getUserInfo(String token);
}
