package ru.otus.socialnetwork.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.socialnetwork.model.dto.UserDto;
import ru.otus.socialnetwork.model.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = ".", source = ".")
    public abstract UserDto mapToDto(UserEntity entity);

    @Mapping(target = ".", source = ".")
    public abstract List<UserDto> mapToDto(List<UserEntity> entities);

    @Mapping(target = ".", source = ".")
    public abstract UserEntity mapToEntity(UserDto dto);

    @Mapping(target = ".", source = ".")
    public abstract List<UserEntity> mapToEntity(List<UserDto> dto);
}