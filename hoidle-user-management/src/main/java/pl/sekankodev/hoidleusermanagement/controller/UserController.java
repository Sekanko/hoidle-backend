package pl.sekankodev.hoidleusermanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sekankodev.hoidleusermanagement.model.AuthenticationResponse;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserResponseDTO;
import pl.sekankodev.hoidleusermanagement.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final IUserService userService;
    @PostMapping("register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody  HoidleUserRequestDTO user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("update")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody HoidleUserRequestDTO user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody HoidleUserRequestDTO user) {
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> logInUser(@Valid @RequestBody HoidleUserRequestDTO user){
        return new ResponseEntity<>(userService.logInUser(user) , HttpStatus.OK);
    }

    @GetMapping("top5/attempts")
    public ResponseEntity<List<HoidleUserResponseDTO>> getTop5UsersAttempts(){
        return new ResponseEntity<>(userService.getTop5UsersAttempts(), HttpStatus.OK);
    }

    @GetMapping("top5/streak")
    public ResponseEntity<List<HoidleUserResponseDTO>> getTop5UsersLongestCurrentStreak(){
        return new ResponseEntity<>(userService.getTop5UsersLongestCurrentStreak(), HttpStatus.OK);
    }

    @PostMapping("userInfo")
    public  ResponseEntity<HoidleUserResponseDTO> getUserInfo(@RequestBody String token){
        return new ResponseEntity<>(userService.getUserInfo(token), HttpStatus.OK);
    }
}
