package pl.sekankodev.hoidledata.data_exceptions;

public class NoDrawResultException extends RuntimeException {
    public NoDrawResultException() {
        super("Random country wasn't drawn");
    }
}
