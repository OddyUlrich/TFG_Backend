package com.tfgbackend.annotations;

import com.tfgbackend.model.enumerators.Rol;
import com.tfgbackend.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsTeacherValidator implements ConstraintValidator<IsTeacher, User> {

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        return value.getRol().equals(Rol.TEACHER) || value.getRol().equals(Rol.ADMIN);
    }
}
