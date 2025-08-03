package pl.sekankodev.hoidlegamelogic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sekankodev.hoidledata.data_exceptions.CountryNotFoundException;
import pl.sekankodev.hoidledata.data_exceptions.GenericDbException;


@ControllerAdvice
public class HoidleExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CountryNotFoundException.class})
    public ResponseEntity<Object> handleCountryNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {GenericDbException.class})
    public ResponseEntity<Object> handleGenericDbException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
