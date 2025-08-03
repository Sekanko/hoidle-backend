package hoidlegamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.sekankodev.hoidledata.data_exceptions.CountryNotFoundException;
import pl.sekankodev.hoidledata.data_exceptions.GenericDbException;
import pl.sekankodev.hoidlegamelogic.exceptions.HoidleExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HoidleExceptionHandlerTest {
    private HoidleExceptionHandler hoidleExceptionHandler;

    @BeforeEach
    void setUp() {
        hoidleExceptionHandler = new HoidleExceptionHandler();
    }

    @Test
    void GenericDbExceptionTest() {
        GenericDbException genericDbException = new GenericDbException();

        var response = hoidleExceptionHandler.handleGenericDbException(genericDbException);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(genericDbException.getMessage(), response.getBody());
    }

    @Test
    void CountryNotFoundExceptionTest() {
        CountryNotFoundException countryNotFoundException = new CountryNotFoundException();

        var response = hoidleExceptionHandler.handleCountryNotFoundException(countryNotFoundException);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(countryNotFoundException.getMessage(), response.getBody());

        String message = "Custom message";
        CountryNotFoundException secondCountryNotFoundException = new CountryNotFoundException(message);

        response = hoidleExceptionHandler.handleCountryNotFoundException(secondCountryNotFoundException);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody());
    }
}
