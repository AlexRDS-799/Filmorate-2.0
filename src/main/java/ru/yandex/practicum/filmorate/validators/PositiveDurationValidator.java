package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

public class PositiveDurationValidator implements ConstraintValidator<PositiveDuration, Duration> {

    @Override
    public void initialize(PositiveDuration constraintAnnotation) {
    }

    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return duration != null && duration.compareTo(Duration.ZERO) > 0;
    }
}
