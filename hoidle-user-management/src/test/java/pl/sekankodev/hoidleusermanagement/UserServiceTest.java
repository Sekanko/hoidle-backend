package pl.sekankodev.hoidleusermanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sekankodev.hoidledata.model.HoidleUser;
import pl.sekankodev.hoidledata.repositories.HoidleUserRepository;
import pl.sekankodev.hoidledata.repositories.IRepositoryCatalog;
import pl.sekankodev.hoidleusermanagement.mapper.IUserMapper;
import pl.sekankodev.hoidleusermanagement.mapper.UserMapper;
import pl.sekankodev.hoidleusermanagement.model.AuthenticationResponse;
import pl.sekankodev.hoidleusermanagement.model.HoidleAppUserDetails;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserResponseDTO;
import pl.sekankodev.hoidleusermanagement.service.JWTService;
import pl.sekankodev.hoidleusermanagement.service.UserService;
import pl.sekankodev.hoidleusermanagement.user_exceptions.AuthenticationRefusedException;
import pl.sekankodev.hoidleusermanagement.user_exceptions.UserAlreadyRegisteredException;
import pl.sekankodev.hoidleusermanagement.user_exceptions.UserNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService userService;
    private IUserMapper userMapper;
    private BCryptPasswordEncoder encoder;
    private IRepositoryCatalog db;
    private JWTService jwtService;
    private AuthenticationManager authManager;

    @BeforeEach
    void setUp(){
        userMapper = Mockito.mock(UserMapper.class);
        encoder = Mockito.mock(BCryptPasswordEncoder.class);
        authManager = Mockito.mock(AuthenticationManager.class);
        jwtService = Mockito.mock(JWTService.class);
        db = Mockito.mock(IRepositoryCatalog.class);

        userService = new UserService(db, userMapper,encoder, authManager, jwtService);

        HoidleUserRepository hoidleUserRepository = Mockito.mock(HoidleUserRepository.class);

        when(db.getHoidleUserRepository()).thenReturn(hoidleUserRepository);
    }

    @Test
    public void createUserWithUnusedEmailTest(){

        String mail = "mail@mail.com";
        HoidleUserRequestDTO newUser = new HoidleUserRequestDTO()
                .setEmail(mail)
                .setPassword("testPassword");

        HoidleUser dbUser = new HoidleUser();
        dbUser.setId(1L);
        when(db.getHoidleUserRepository().existsByEmail(mail)).thenReturn(false);
        when(db.getHoidleUserRepository().findByEmail(mail)).thenReturn(dbUser);
        when(userMapper.toEntity(newUser)).thenReturn(dbUser);

        Long result = userService.createUser(newUser);

        verify(db.getHoidleUserRepository(), times(1)).save(any());
        assertEquals(1L,result);
    }

    @Test
    public void createUserWithUsedEmailTest(){
        String mail = "mail@mail.com";
        HoidleUserRequestDTO newUser = new HoidleUserRequestDTO()
                .setEmail(mail)
                .setPassword("testPassword");

        when(db.getHoidleUserRepository().existsByEmail(mail)).thenReturn(true);
        assertThrows(UserAlreadyRegisteredException.class, () -> userService.createUser(newUser) );
    }

    @Test
    public void updateUserWithCorrectDataTest(){
        String existingUserMail = "mail@mail.com";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(existingUserMail)
                .setPassword("testPassword");


        String newMail = "mail@mail.com";
        HoidleUserRequestDTO newUser = new HoidleUserRequestDTO()
                .setEmail(newMail)
                .setPassword("testPassword");

        when(db.getHoidleUserRepository().findByEmail(existingUserMail)).thenReturn(existingUser);
        when(encoder.matches(newUser.getPassword(),existingUser.getPassword())).thenReturn(true);
        when(userMapper.toEntity(newUser, existingUser)).thenReturn(existingUser);

        Long result = userService.updateUser(newUser);
        verify(db.getHoidleUserRepository(),times(1)).save(any());
        assertEquals(1L,result);
    }

    @Test
    public void updateUserWithIncorrectPasswordTest(){
        String existingUserMail = "mail@mail.com";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(existingUserMail)
                .setPassword("testPassword");


        String newMail = "mail@mail.com";
        HoidleUserRequestDTO newUser = new HoidleUserRequestDTO()
                .setEmail(newMail)
                .setPassword("notCorrectPassword");

        when(db.getHoidleUserRepository().findByEmail(existingUserMail)).thenReturn(existingUser);
        when(encoder.matches(newUser.getPassword(),existingUser.getPassword())).thenReturn(false);
        assertThrows(AuthenticationRefusedException.class, () -> userService.updateUser(newUser) );
    }

    @Test
    public void updateUserButUserCannotBeFoundTest(){

        String newMail = "mail@mail.com";
        HoidleUserRequestDTO newUser = new HoidleUserRequestDTO()
                .setEmail(newMail)
                .setPassword("strongPassword");

        when(db.getHoidleUserRepository().findByEmail(newMail)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(newUser) );
    }

    @Test
    public void deleteUserWithCorrectCredentialsTest (){
        String existingUserMail = "mail@mail.com";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(existingUserMail)
                .setPassword("testPassword");


        String otherMail = "mail@mail.com";
        HoidleUserRequestDTO deleteUser = new HoidleUserRequestDTO()
                .setEmail(otherMail)
                .setPassword("testPassword");

        when(db.getHoidleUserRepository().findByEmail(otherMail)).thenReturn(existingUser);
        when(encoder.matches(deleteUser.getPassword(),existingUser.getPassword())).thenReturn(true);
        Long id = userService.deleteUser(deleteUser);

        verify(db.getHoidleUserRepository(),times(1)).delete(any());
        assertEquals(1L,id);
    }

    @Test
    public void deleteUserWithIncorrectCredentialsTest(){
        String existingUserMail = "mail@mail.com";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(existingUserMail)
                .setPassword("testPassword");


        String otherMail = "mail@mail.com";
        HoidleUserRequestDTO deleteUser = new HoidleUserRequestDTO()
                .setEmail(otherMail)
                .setPassword("testPassword");

        when(db.getHoidleUserRepository().findByEmail(otherMail)).thenReturn(existingUser);
        when(encoder.matches(deleteUser.getPassword(),existingUser.getPassword())).thenReturn(false);

        assertThrows(AuthenticationRefusedException.class, () -> userService.deleteUser(deleteUser) );
    }

    @Test
    public void deleteUserButUserCannotBeFound(){
        String existingUserMail = "mail@mail.com";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(existingUserMail)
                .setPassword("testPassword");


        String otherMail = "mail@mail.com";
        HoidleUserRequestDTO deleteUser = new HoidleUserRequestDTO()
                .setEmail(otherMail)
                .setPassword("testPassword");

        when(db.getHoidleUserRepository().findByEmail(otherMail)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(deleteUser) );
    }

    @Test
    public void logInWithAuthenticationPassed(){
        String existingUserMail = "mail@mail.com";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(existingUserMail)
                .setPassword("testPassword");

        String mail = "mail@mail.com";
        HoidleUserRequestDTO userRequest = new HoidleUserRequestDTO()
                .setEmail(mail)
                .setPassword("testPassword");

        HoidleUserResponseDTO userResponse = new HoidleUserResponseDTO()
                .setEmail(mail);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                mail,
                "testPassword",
                Collections.emptyList()
        );

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(db.getHoidleUserRepository().findByEmail(mail)).thenReturn(existingUser);
        when(userMapper.toResponseDTO(existingUser)).thenReturn(userResponse);
        when(jwtService.generateToken(mail)).thenReturn("testToken");

        AuthenticationResponse response = new AuthenticationResponse().setUser(userResponse).setToken("testToken");

        assertEquals(response, userService.logInUser(userRequest));
    }

    @Test
    public void logInWithAuthenticationDenied(){
        String mail = "mail@mail.com";
        HoidleUserRequestDTO userRequest = new HoidleUserRequestDTO()
                .setEmail(mail)
                .setPassword("testPassword");

        when(authManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));
        assertThrows(AuthenticationRefusedException.class, () -> userService.logInUser(userRequest) );
    }

    @Test
    public void loadUserByUsernameWithCorrectEmail(){
        String mail = "testMail";
        HoidleUser existingUser = new HoidleUser()
                .setId(1L)
                .setEmail(mail)
                .setPassword("testPassword");

        when(db.getHoidleUserRepository().findByEmail(mail)).thenReturn(existingUser);
        HoidleAppUserDetails userDetails = new HoidleAppUserDetails(existingUser);

        assertEquals(userDetails, userService.loadUserByUsername(mail));
    }

    @Test
    public void loadUserByUsernameWithIncorrectEmail(){
        String mail = "testMail";
        when(db.getHoidleUserRepository().findByEmail(mail)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername(mail) );
    }
}
