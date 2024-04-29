package ihor.kalaur.demo.mapper;

import ihor.kalaur.demo.config.MapperConfig;
import ihor.kalaur.demo.dto.CreateUserRequestDto;
import ihor.kalaur.demo.dto.UpdateAnyFieldsUserRequestDto;
import ihor.kalaur.demo.dto.UserDto;
import ihor.kalaur.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(CreateUserRequestDto createUserRequestDto);

    User toEntity(UserDto userDto);

    User toEntity(UpdateAnyFieldsUserRequestDto updateAnyFieldsUserRequestDto, @MappingTarget User user);
}
