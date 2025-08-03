package pl.sekankodev.hoidledataupdater.update_exceptions;

public class MappingToEnumFailedException extends RuntimeException {
    public MappingToEnumFailedException() {
        super("Mapping to enum failed");
    }
}
