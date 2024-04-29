package ihor.kalaur.demo.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidAge {
    String message() default "User must be adult";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
