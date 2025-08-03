package pl.sekankodev.hoidleusermanagement.user_exceptions;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(){
        super("User is already registered");
    }
}
