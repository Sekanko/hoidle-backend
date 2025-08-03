package pl.sekankodev.hoidledataupdater.mappers;

public interface IMap <TDto, TEntity> {
    TEntity toEntity(TDto dtoEntity);
}
