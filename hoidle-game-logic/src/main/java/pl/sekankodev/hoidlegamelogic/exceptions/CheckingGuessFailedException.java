package pl.sekankodev.hoidlegamelogic.exceptions;

public class CheckingGuessFailedException extends RuntimeException {
    public CheckingGuessFailedException() {
        super("Checking guess failed with message");
    }
}
