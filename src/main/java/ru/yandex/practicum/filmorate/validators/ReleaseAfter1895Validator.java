package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseAfter1895Validator implements ConstraintValidator<ReleaseAfter1895, LocalDate> {

    private static final LocalDate MIN_RELEAS_DATE = LocalDate.of(1895,12,28);


    @Override
    public void initialize(ReleaseAfter1895 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext) {
        return !releaseDate.isBefore(MIN_RELEAS_DATE);
    }
}