package pl.sekankodev.hoidledata.repositories;

public interface IRepositoryCatalog {
    Hoi4CountryRepository getHoi4CountryRepository();
    HoidleDailyCountryRepository getHoidleDailyCountryRepository();
    HoidleUserRepository getHoidleUserRepository();
}
