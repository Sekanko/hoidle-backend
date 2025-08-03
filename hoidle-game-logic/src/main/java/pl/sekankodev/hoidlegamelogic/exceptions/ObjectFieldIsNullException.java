package pl.sekankodev.hoidlegamelogic.exceptions;


public class ObjectFieldIsNullException extends RuntimeException {
    public ObjectFieldIsNullException(String fieldName) {
        super("Object field - " + fieldName + " - is null");
    }
}
