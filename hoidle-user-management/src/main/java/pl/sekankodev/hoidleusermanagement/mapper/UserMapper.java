package pl.sekankodev.hoidleusermanagement.mapper;
import org.springframework.stereotype.Component;
import pl.sekankodev.hoidledata.model.HoidleUser;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserResponseDTO;

@Component
public class UserMapper implements IUserMapper {
    @Override
    public HoidleUserResponseDTO toResponseDTO(HoidleUser user) {
        return new HoidleUserResponseDTO()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setRole(user.getRole())
                .setLastWin(user.getLastWin())
                .setStreak(user.getStreak())
                .setLongestStreak(user.getLongestStreak())
                .setTodaysAttempts(user.getTodaysAttempts());
    }
    @Override
    public HoidleUser toEntity(HoidleUserRequestDTO requestDTO) {
        return toEntity(requestDTO, new HoidleUser());
    }

    @Override
    public HoidleUser toEntity(HoidleUserRequestDTO requestDTO, HoidleUser user) {
        return user.setEmail(requestDTO.getEmail())
                .setPassword(requestDTO.getPassword() == null ? user.getPassword() : requestDTO.getPassword())
                .setRole(requestDTO.getRole())
                .setUsername(requestDTO.getUsername())
                .setLastWin(requestDTO.getLastWin())
                .setStreak(requestDTO.getStreak())
                .setTodaysAttempts(requestDTO.getTodaysAttempts())
                .setLongestStreak(requestDTO.getLongestStreak());
    }
}
