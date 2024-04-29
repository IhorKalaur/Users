package ihor.kalaur.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ihor.kalaur.demo.dto.CreateUserRequestDto;
import ihor.kalaur.demo.dto.UpdateAnyFieldsUserRequestDto;
import ihor.kalaur.demo.dto.UserDto;
import ihor.kalaur.demo.exceptions.EntityNotFoundException;
import ihor.kalaur.demo.service.UserService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final String BASE_URL = "/users";
    private static final String SEARCH_PART_OF_URL = "/search";
    private static final String URL_SPLITTER = "/";
    private static final Long ID_ONE = 1L;
    private static final String EMAIL_VALID = "john.doe@example.com";
    private static final String EMAIL_INVALID = "not-an-email";
    private static final String FIRST_NAME = "John";
    private static final String UPDATED_FIRST_NAME = "Bob";
    private static final String LAST_NAME = "Doe";
    private static final String ADDRESS = "123 Main St";
    private static final String PHONE_VALID = "+1234567890";
    private static final LocalDate BIRTH_DATE_VALID = LocalDate.of(1990, 1, 1);

    private static final LocalDate BIRTH_DATE_INVALID = LocalDate.now().minusYears(1);
    private static final String ERROR_MESSAGE_INVALID_EMAIL = "email: must be a valid email address";
    private static final String ERROR_MESSAGE_NOT_ADULT = "birthDate: User must be adult";
    private static final String ERROR_MESSAGE_USER_NOT_FOUND = "User not found";
    private static final String DATE_VALIDATION_ERROR_MESSAGE = "valid: The 'from' date must be before the 'to' date.";

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private UserService userService;

    @Test
    void createUser_ValidRequest_returnUserDto() throws Exception {
        CreateUserRequestDto requestDto = createValidUserRequestDto();

        UserDto expected = toUserDto(requestDto);

        given(userService.save(requestDto)).willReturn(expected);

        MvcResult mvcResult = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        UserDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);

        assertNotNull(actual.getId());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    void createUser_invalidEmail_returnError() throws Exception {
        CreateUserRequestDto requestDto = createValidUserRequestDto();
        requestDto.setEmail(EMAIL_INVALID);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value(ERROR_MESSAGE_INVALID_EMAIL));
    }

    @Test
    void createUser_invalidAge_returnError() throws Exception {
        CreateUserRequestDto requestDto = createValidUserRequestDto();
        requestDto.setBirthDate(BIRTH_DATE_INVALID);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value(ERROR_MESSAGE_NOT_ADULT));
    }

    @Test
    void updateAnyUserFields_existingUser_updatesFieldsSuccessfully() throws Exception {
        UpdateAnyFieldsUserRequestDto requestDto = createUpdateAnyFieldsUserRequestDto();
        UserDto expectedDto = createUpdatedUserDto(requestDto);

        given(userService.updateAnyUserFields(ID_ONE, requestDto)).willReturn(expectedDto);

        MvcResult mvcResult = mockMvc.perform(patch(BASE_URL + URL_SPLITTER + ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk()).andReturn();

        UserDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
        assertEquals(expectedDto, actual);
    }

    @Test
    void updateAnyUserFields_nonExistingUser_returnsNotFound() throws Exception {
        UpdateAnyFieldsUserRequestDto requestDto = createUpdateAnyFieldsUserRequestDto();

        given(userService.updateAnyUserFields(ID_ONE, requestDto))
                .willThrow(new EntityNotFoundException(ERROR_MESSAGE_USER_NOT_FOUND));

        mockMvc.perform(patch(BASE_URL + URL_SPLITTER + ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ERROR_MESSAGE_USER_NOT_FOUND));
    }

    @Test
    void updateAllUserFields_existingUser_updatesSuccessfully() throws Exception {
        CreateUserRequestDto requestDto = createValidUserRequestDto();
        UserDto expectedDto = toUserDto(requestDto);

        given(userService.updateAllUserFields(ID_ONE, requestDto)).willReturn(expectedDto);

        MvcResult mvcResult = mockMvc.perform(put(BASE_URL + URL_SPLITTER + ID_ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk()).andReturn();

        UserDto actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);

        assertEquals(expectedDto, actual);
    }

    @Test
    void deleteUser_userExists_deletesSuccessfully() throws Exception {
        doNothing().when(userService).delete(ID_ONE);

        mockMvc.perform(delete(BASE_URL + URL_SPLITTER + ID_ONE))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_nonExistentUser_returnsNotFound() throws Exception {

        doThrow(new EntityNotFoundException(ERROR_MESSAGE_USER_NOT_FOUND)).when(userService).delete(ID_ONE);

        mockMvc.perform(delete(BASE_URL + URL_SPLITTER + ID_ONE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ERROR_MESSAGE_USER_NOT_FOUND));
    }

    @Test
    void findByBirthDateRange_validRange_returnUsers() throws Exception {
        LocalDate startDate = BIRTH_DATE_VALID;
        LocalDate endDate = BIRTH_DATE_VALID.plusDays(1);
        List<UserDto> expectedUsers = createUserDtos();

        given(userService.findByBirthDateRange(startDate, endDate)).willReturn(expectedUsers);

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + SEARCH_PART_OF_URL)
                        .param("from", startDate.toString())
                        .param("to", endDate.toString()))
                .andExpect(status().isOk())
                .andReturn();

        UserDto[] actual = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserDto[].class);
        assertEquals(expectedUsers.size(), actual.length);
        assertEquals(expectedUsers, Arrays.stream(actual).toList());
    }

    @Test
    void findByBirthDateRange_invalidOrder_returnValidationError() throws Exception {
        LocalDate startDate = BIRTH_DATE_VALID;
        LocalDate endDate = BIRTH_DATE_VALID.minusDays(1);

        mockMvc.perform(get(BASE_URL + SEARCH_PART_OF_URL)
                        .param("from", startDate.toString())
                        .param("to", endDate.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value(DATE_VALIDATION_ERROR_MESSAGE));
    }
    
    private CreateUserRequestDto createValidUserRequestDto() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail(EMAIL_VALID);
        requestDto.setFirstName(FIRST_NAME);
        requestDto.setLastName(LAST_NAME);
        requestDto.setBirthDate(BIRTH_DATE_VALID);
        requestDto.setAddress(ADDRESS);
        requestDto.setPhoneNumber(PHONE_VALID);
        return requestDto;
    }

    private UserDto toUserDto(CreateUserRequestDto createUserRequestDto) {
        return new UserDto(
                1L,
                createUserRequestDto.getEmail(),
                createUserRequestDto.getFirstName(),
                createUserRequestDto.getLastName(),
                createUserRequestDto.getBirthDate(),
                createUserRequestDto.getAddress(),
                createUserRequestDto.getPhoneNumber()

        );
    }

    private List<UserDto> createUserDtos() {
        return Collections.singletonList(toUserDto(createValidUserRequestDto()));
    }

    private UpdateAnyFieldsUserRequestDto createUpdateAnyFieldsUserRequestDto() {
        UpdateAnyFieldsUserRequestDto dto = new UpdateAnyFieldsUserRequestDto();
        dto.setFirstName(UPDATED_FIRST_NAME);
        return dto;
    }

    private UserDto createUpdatedUserDto(UpdateAnyFieldsUserRequestDto requestDto) {
        UserDto dto = new UserDto();
        dto.setFirstName(requestDto.getFirstName());
        return dto;
    }
}
