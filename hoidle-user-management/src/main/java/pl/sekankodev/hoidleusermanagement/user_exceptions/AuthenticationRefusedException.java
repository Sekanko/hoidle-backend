package pl.sekankodev.hoidleusermanagement.user_exceptions;

public class AuthenticationRefusedException extends RuntimeException{
    public AuthenticationRefusedException(){
        super("Authentication refused");
    }
}
