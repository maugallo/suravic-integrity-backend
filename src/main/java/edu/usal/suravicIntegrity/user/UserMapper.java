package edu.usal.suravicIntegrity.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ResponseUserDTO toDTO(User user);

    User toEntity(RequestUserDTO requestUserDTO);

}
