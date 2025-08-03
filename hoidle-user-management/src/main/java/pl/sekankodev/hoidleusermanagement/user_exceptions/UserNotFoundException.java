package pl.sekankodev.hoidleusermanagement.user_exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found");
    }
}
