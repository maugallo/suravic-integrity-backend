package edu.usal.suravicIntegrity.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default ResponseUserDTO toDTO(User user) {
        ResponseUserDTO dto = new ResponseUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setIsEnabled(user.getIsEnabled());

        return dto;
    }

    default User toEntity(RequestUserDTO requestUserDTO) {
        User user = new User();
        user.setUsername(requestUserDTO.getUsername());
        user.setPassword(requestUserDTO.getPassword());
        user.setRole(requestUserDTO.getRole());

        return user;
    }

}
