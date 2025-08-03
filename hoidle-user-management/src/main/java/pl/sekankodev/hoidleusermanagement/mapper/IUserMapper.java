package pl.sekankodev.hoidleusermanagement.mapper;

import pl.sekankodev.hoidledata.model.HoidleUser;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserRequestDTO;
import pl.sekankodev.hoidleusermanagement.model.HoidleUserResponseDTO;

public interface IUserMapper {
    HoidleUserResponseDTO toResponseDTO(HoidleUser user);
    HoidleUser toEntity(HoidleUserRequestDTO requestDTO);

    HoidleUser toEntity(HoidleUserRequestDTO requestDTO, HoidleUser user);
}
