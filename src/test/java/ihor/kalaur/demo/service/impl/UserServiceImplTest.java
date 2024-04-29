package ihor.kalaur.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ihor.kalaur.demo.dto.CreateUserRequestDto;
import ihor.kalaur.demo.dto.UpdateAnyFieldsUserRequestDto;
import ihor.kalaur.demo.dto.UserDto;
import ihor.kalaur.demo.exceptions.EntityNotFoundException;
import ihor.kalaur.demo.mapper.UserMapper;
import ihor.kalaur.demo.model.User;
import ihor.kalaur.demo.repository.UserRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long ID_ONE = 1L;
    private static final String EMAIL_VALID = "john.doe@example.com";
    private static final String EMAIL_UPDATED = "updated@example.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String ADDRESS = "123 Main St";
    private static final String PHONE_VALID = "+1234567890";
    private static final LocalDate BIRTH_DATE_VALID = LocalDate.of(1990, 1, 1);
    private static final String CANT_FIND_USER_MESSAGE_TEMPLATE = "Can't find user with id %d";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private CreateUserRequestDto createUserRequestDto;
    private UpdateAnyFieldsUserRequestDto updateAnyFieldsUserRequestDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail(EMAIL_VALID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setBirthDate(BIRTH_DATE_VALID);
        user.setAddress(ADDRESS);
        user.setPhoneNumber(PHONE_VALID);

        userDto = toUserDto(user);

        createUserRequestDto = toCreateUserRequestDto(user);

        updateAnyFieldsUserRequestDto = new UpdateAnyFieldsUserRequestDto();
        updateAnyFieldsUserRequestDto.setEmail(EMAIL_UPDATED);

        lenient().when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        lenient().when(userMapper.toEntity(any(CreateUserRequestDto.class))).thenReturn(user);
        lenient().when(userMapper.toEntity(any(UpdateAnyFieldsUserRequestDto.class), any(User.class))).thenReturn(user);
    }

    @Test
    void save_validData_createsUserSuccessfully() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto actual = userService.save(createUserRequestDto);

        assertNotNull(actual);
        assertEquals(userDto, actual);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateAnyUserFields_existingUser_updatesSuccessfully() {
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto actual = userService.updateAnyUserFields(ID_ONE, updateAnyFieldsUserRequestDto);

        assertNotNull(actual);
        assertEquals(userDto, actual);
        verify(userRepository).save(user);
    }

    @Test
    void updateAnyUserFields_nonExistingUser_throwsEntityNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.updateAnyUserFields(ID_ONE, updateAnyFieldsUserRequestDto),
                "Expected updateAnyUserFields to throw, but it didn't"
        );

        String expectedMessage = String.format(CANT_FIND_USER_MESSAGE_TEMPLATE, ID_ONE);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void updateAllUserFields_createsOrUpdatesUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto actual = userService.updateAllUserFields(ID_ONE, createUserRequestDto);

        assertNotNull(actual);
        verify(userRepository).save(user);
    }

    @Test
    void delete_existingUser_deletesUser() {
        when(userRepository.existsById(ID_ONE)).thenReturn(true);

        assertDoesNotThrow(() -> userService.delete(ID_ONE));
        verify(userRepository).deleteById(ID_ONE);
    }

    @Test
    void delete_nonExistingUser_throwsException() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.delete(ID_ONE),
                "Expected delete to throw, but it didn't"
        );

        String expectedMessage = String.format(CANT_FIND_USER_MESSAGE_TEMPLATE, ID_ONE);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void findByBirthDateRange_withValidDates_returnsUsers() {
        LocalDate start = LocalDate.of(1990, 1, 1);
        LocalDate end = LocalDate.of(2000, 12, 31);
        when(userRepository.findByBirthDateBetween(start, end)).thenReturn(Collections.singletonList(user));

        List<UserDto> actual = userService.findByBirthDateRange(start, end);

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        verify(userRepository).findByBirthDateBetween(start, end);
    }

    private UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getAddress(),
                user.getPhoneNumber()

        );
    }

    private CreateUserRequestDto toCreateUserRequestDto(User user) {
        return new CreateUserRequestDto(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }
}
