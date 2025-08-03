package pl.sekankodev.hoidledata.data_exceptions;

public class GenericDbException extends RuntimeException {
    public GenericDbException() {
        super("Something went wrong with the database");
    }
}
