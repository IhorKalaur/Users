package ihor.kalaur.demo.dto;

import ihor.kalaur.demo.validator.ValidAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {
    @NotBlank(message = "required field")
    @Email(message = "must be a valid email address")
    private String email;

    @NotBlank(message = "required field")
    @Size(max = 100, message = "first name must be under 100 characters")
    private String firstName;

    @NotBlank(message = "required field")
    @Size(max = 100, message = "last name must be under 100 characters")
    private String lastName;

    @NotNull(message = "required field")
    @ValidAge
    private LocalDate birthDate;

    @Size(max = 200, message = "address must be under 200 characters")
    private String address;

    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "invalid phone number format")
    private String phoneNumber;
}
