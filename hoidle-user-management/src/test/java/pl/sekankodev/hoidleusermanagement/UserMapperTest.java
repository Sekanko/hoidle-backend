package pl.sekankodev.hoidleusermanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sekankodev.hoidledata.model.HoidleUser;
import pl.sekankodev.hoidledata.model.Role;
import pl.sekankodev.hoidleusermanagement.mapper.UserMapper;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserResponseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    public void setup(){
        userMapper = new UserMapper();
    }

    @Test
    public void mapToResponseDTOTest(){
        HoidleUser user = new HoidleUser()
                .setEmail("test@mail.com")
                .setPassword("strongPassword")
                .setRole(Role.ADMIN);

        HoidleUserResponseDTO responseDTO = userMapper.toResponseDTO(user);
        assertEquals(user.getEmail(), responseDTO.getEmail());
        assertEquals(user.getRole(), responseDTO.getRole());
    }

    @Test
    public void mapToEntityWithOneArgumentTest(){
        HoidleUserRequestDTO requestDTO = new HoidleUserRequestDTO()
                .setEmail("test@mail.com")
                .setPassword("strongPassword")
                .setRole(Role.ADMIN);

        HoidleUser user = userMapper.toEntity(requestDTO);
        assertEquals(requestDTO.getEmail(), user.getEmail());
        assertEquals(requestDTO.getPassword(), user.getPassword());
        assertEquals(requestDTO.getRole(), user.getRole());
    }
    @Test
    public void mapToEntityWithTwoArgumentsTest(){
        HoidleUserRequestDTO requestDTO = new HoidleUserRequestDTO()
                .setEmail("test@mail.com")
                .setPassword("strongPassword")
                .setRole(Role.ADMIN);

        HoidleUser user = new HoidleUser()
                .setId(1L)
                .setStreak(2);

        HoidleUser resultUser = userMapper.toEntity(requestDTO, user);
        assertEquals(1L, resultUser.getId());
        assertEquals(requestDTO.getEmail(), resultUser.getEmail());
        assertEquals(requestDTO.getPassword(), resultUser.getPassword());
        assertEquals(requestDTO.getRole(), resultUser.getRole());
        assertEquals(0, resultUser.getStreak());
    }
}
