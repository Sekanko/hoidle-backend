package pl.sekankodev.hoidledataupdater.update_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sekankodev.hoidledata.data_exceptions.GenericDbException;

@ControllerAdvice
public class UpdateExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {GenericDbException.class})
    public ResponseEntity<Object> handleGenericDbException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MappingToEnumFailedException.class})
    public ResponseEntity<Object> handleMappingToEnumFailedException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CouldNotParseException.class})
    public ResponseEntity<Object> handleCouldNotParseException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {NothingWasParsedException.class})
    public ResponseEntity<Object> handleNothingWasParsedException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
