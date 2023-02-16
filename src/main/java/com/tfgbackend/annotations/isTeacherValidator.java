package com.tfgbackend.annotations;

import com.tfgbackend.model.Rol;
import com.tfgbackend.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class isTeacherValidator implements ConstraintValidator<isTeacher, User> {

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        return value.rol().name().equals(Rol.TEACHER.name());
    }
}
