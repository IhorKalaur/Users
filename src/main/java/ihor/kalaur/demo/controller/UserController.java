package ihor.kalaur.demo.controller;

import ihor.kalaur.demo.dto.CreateUserRequestDto;
import ihor.kalaur.demo.dto.UpdateAnyFieldsUserRequestDto;
import ihor.kalaur.demo.dto.UserDto;
import ihor.kalaur.demo.dto.date.DateRange;
import ihor.kalaur.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users management",
        description = "Endpoints for managing users information")
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user",
            description = "Create new user. "
                    + "The creation of a new user is possible only in case of reaching the age of majority")
    public UserDto createUser(
            @RequestBody @Valid CreateUserRequestDto userRequestDto
    ) {
        return userService.save(userRequestDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update any information ",
            description = "Allows updating specific fields of an existing user. "
                    + "This endpoint does not create new users.")
    public UserDto updateAnyUserFields(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAnyFieldsUserRequestDto updateAnyFieldsUserRequestDto
    ) {
        return userService.updateAnyUserFields(id, updateAnyFieldsUserRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update all information ",
            description = "This endpoint allow update information about user. "
                    + "Updates all user information. If the user does not exist, a new record will be created.")
    public UserDto updateAllUserFields(
            @PathVariable Long id,
            @RequestBody @Valid CreateUserRequestDto createUserRequestDto
    ) {
        return userService.updateAllUserFields(id, createUserRequestDto);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = " Search for users by birth date range.",
            description = "Retrieves a list of users whose birth dates fall within the specified range. "
                    + "Dates should be provided in YYYY-MM-DD format.")
    public List<UserDto> findByBirthDateRange(
            @Valid @ModelAttribute DateRange dateRange
    ) {
        return userService.findByBirthDateRange(dateRange.getFrom(), dateRange.getTo());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user by id",
            description = "Marks a user as deleted, implementing the soft delete mechanism to retain information.")
    public void deleteUser(
            @PathVariable Long id
    ) {
        userService.delete(id);
    }

}
