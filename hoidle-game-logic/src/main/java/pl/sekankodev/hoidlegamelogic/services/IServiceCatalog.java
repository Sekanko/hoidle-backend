package pl.sekankodev.hoidlegamelogic.services;

public interface IServiceCatalog {
    IHoi4CountryService getHoi4CountryService();
    IGameService getGameService();
}
