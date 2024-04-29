package ihor.kalaur.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.beans.factory.annotation.Value;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {
    @Value("${app.user.min-adult-age}")
    private int adultAge;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return true;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= adultAge;
    }
}
