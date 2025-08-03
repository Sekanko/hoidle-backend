package pl.sekankodev.hoidledataupdater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sekankodev.hoidledata.data_exceptions.GenericDbException;
import pl.sekankodev.hoidledataupdater.update_exceptions.CouldNotParseException;
import pl.sekankodev.hoidledataupdater.update_exceptions.MappingToEnumFailedException;
import pl.sekankodev.hoidledataupdater.update_exceptions.NothingWasParsedException;
import pl.sekankodev.hoidledataupdater.update_exceptions.UpdateExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateExceptionHandlerTest {
    private UpdateExceptionHandler updateExceptionHandler;
    @BeforeEach
    public void setup(){
        updateExceptionHandler = new UpdateExceptionHandler();
    }

    @Test
    public void couldNotParseExceptionTest(){
        CouldNotParseException couldNotParseException = new CouldNotParseException();
        var response = updateExceptionHandler.handleCouldNotParseException(couldNotParseException);

        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertEquals(couldNotParseException.getMessage(), response.getBody());
    }

    @Test
    public void mappingToEnumFailedExceptionTest(){
        MappingToEnumFailedException mappingToEnumFailedException = new MappingToEnumFailedException();
        var response = updateExceptionHandler.handleMappingToEnumFailedException(mappingToEnumFailedException);

        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertEquals(mappingToEnumFailedException.getMessage(), response.getBody());
    }

    @Test
    public void nothingWasParsedExceptionTest(){
        NothingWasParsedException nothingWasParsedException = new NothingWasParsedException();
        var response = updateExceptionHandler.handleNothingWasParsedException(nothingWasParsedException);

        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertEquals(nothingWasParsedException.getMessage(), response.getBody());
    }

    @Test
    public void genericDbExceptionTest(){
        GenericDbException genericDbException = new GenericDbException();
        var response = updateExceptionHandler.handleGenericDbException(genericDbException);
        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertEquals(genericDbException.getMessage(), response.getBody());
    }
}
