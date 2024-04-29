package ihor.kalaur.demo.service.impl;

import ihor.kalaur.demo.dto.CreateUserRequestDto;
import ihor.kalaur.demo.dto.UpdateAnyFieldsUserRequestDto;
import ihor.kalaur.demo.dto.UserDto;
import ihor.kalaur.demo.exceptions.EntityNotFoundException;
import ihor.kalaur.demo.mapper.UserMapper;
import ihor.kalaur.demo.model.User;
import ihor.kalaur.demo.repository.UserRepository;
import ihor.kalaur.demo.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE = "Can't find user with id ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto save(CreateUserRequestDto requestDto) {
        User user = convertToEntity(requestDto);
        return covertToDto(userRepository.save(user));
    }

    @Override
    public UserDto updateAnyUserFields(Long id, UpdateAnyFieldsUserRequestDto updateAnyFieldsUserRequestDto) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE + id));

        userFromDb = userMapper.toEntity(updateAnyFieldsUserRequestDto, userFromDb);
        return covertToDto(userRepository.save(userFromDb));
    }

    @Override
    public UserDto updateAllUserFields(Long id, CreateUserRequestDto createUserRequestDto) {
        User updatedUser = convertToEntity(createUserRequestDto);
        updatedUser.setId(id);
        return covertToDto(userRepository.save(updatedUser));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findByBirthDateRange(LocalDate from, LocalDate to) {
        return userRepository.findByBirthDateBetween(from, to).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    private UserDto covertToDto(User user) {
        return userMapper.toDto(user);
    }

    private User convertToEntity(CreateUserRequestDto createUserRequestDto) {
        return userMapper.toEntity(createUserRequestDto);
    }
}
