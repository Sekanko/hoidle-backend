package pl.sekankodev.hoidledata.data_exceptions;

public class CountryNotFoundException extends RuntimeException{
    public CountryNotFoundException(){
        super("Country not found");
    }
    public CountryNotFoundException(String message){
        super(message);
    }
}
