package com.tfgbackend.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = isTeacherValidator.class)
@Target( { ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface isTeacher {
    String message() default "Invalid user, rol not correct";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
