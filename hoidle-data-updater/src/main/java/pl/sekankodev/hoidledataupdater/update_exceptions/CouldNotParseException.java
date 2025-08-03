package pl.sekankodev.hoidledataupdater.update_exceptions;

public class CouldNotParseException extends RuntimeException {
    public CouldNotParseException(){
        super("Couldn't parse data");
    }
}
