package pl.sekankodev.hoidlegamelogic.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Getter
public class ServiceCatalog implements IServiceCatalog {
    private final IHoi4CountryService hoi4CountryService;
    private final IGameService gameService;
}
