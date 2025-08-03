package pl.sekankodev.hoidledataupdater.update_exceptions;

public class NothingWasParsedException extends RuntimeException {
    public NothingWasParsedException() {
        super("Nothing was parsed");
    }
}
