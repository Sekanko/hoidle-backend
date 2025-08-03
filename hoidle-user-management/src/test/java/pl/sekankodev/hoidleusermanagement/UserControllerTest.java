package pl.sekankodev.hoidleusermanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pl.sekankodev.hoidleusermanagement.controller.UserController;
import pl.sekankodev.hoidleusermanagement.model.AuthenticationResponse;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.service.IUserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO: Make tests for @Valid
public class UserControllerTest {
    private UserController userController;
    private IUserService userService;

    @BeforeEach
    public void setUp(){
        userService = Mockito.mock(IUserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void registerUserWithCorrectValuesTest(){
        HoidleUserRequestDTO requestDTO = new HoidleUserRequestDTO()
                .setEmail("test@mail.com")
                .setPassword("testPassword");

        when(userService.createUser(requestDTO)).thenReturn(1L);

        var result = userController.registerUser(requestDTO);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(userService, Mockito.times(1)).createUser(requestDTO);
    }

    @Test
    public void updateUserWithCorrectValuesTest(){
        HoidleUserRequestDTO requestDTO = new HoidleUserRequestDTO()
                .setEmail("test@mail.com")
                .setPassword("testPassword")
                .setStreak(2);

        when(userService.updateUser(requestDTO)).thenReturn(1L);
        var result = userController.updateUser(requestDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService, Mockito.times(1)).updateUser(requestDTO);
    }

    @Test
    public void deleteUserWithCorrectValuesTest(){
        HoidleUserRequestDTO requestDTO = new HoidleUserRequestDTO()
                .setEmail("test@mail.com")
                .setPassword("testPassword");
        when(userService.deleteUser(requestDTO)).thenReturn(1L);
        var result = userController.deleteUser(requestDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userService, Mockito.times(1)).deleteUser(requestDTO);
    }

    @Test
    public void logInWithCorrectValuesTest(){
        HoidleUserRequestDTO requestDTO = new HoidleUserRequestDTO()
                .setEmail("test@mail.com")
                .setPassword("testPassword");

        when(userService.logInUser(requestDTO)).thenReturn(new AuthenticationResponse());

        var result = userController.logInUser(requestDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService, Mockito.times(1)).logInUser(requestDTO);
    }
}
