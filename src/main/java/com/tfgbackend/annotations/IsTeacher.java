package com.tfgbackend.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsTeacherValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsTeacher {
    String message() default "Invalid user, rol not correct";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
