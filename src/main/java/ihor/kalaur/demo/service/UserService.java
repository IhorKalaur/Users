package ihor.kalaur.demo.service;

import ihor.kalaur.demo.dto.CreateUserRequestDto;
import ihor.kalaur.demo.dto.UpdateAnyFieldsUserRequestDto;
import ihor.kalaur.demo.dto.UserDto;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserDto save(CreateUserRequestDto requestDto);

    UserDto updateAnyUserFields(Long id, UpdateAnyFieldsUserRequestDto updateAnyFieldsUserRequestDto);

    UserDto updateAllUserFields(Long id, CreateUserRequestDto createUserRequestDto);

    void delete(Long id);

    List<UserDto> findByBirthDateRange(LocalDate from, LocalDate to);
}
