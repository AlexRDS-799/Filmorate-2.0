package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.serializers.DurationToSecondsSerializer;
import ru.yandex.practicum.filmorate.validators.PositiveDuration;
import ru.yandex.practicum.filmorate.validators.ReleaseAfter1895;
import java.time.Duration;
import java.time.LocalDate;


@Data
public class Film {
    long id;
    @NotBlank(message = "Имя не может быть пустым")
    @NotNull(message = "Имя не может быть null")
    String name;
    @Size(min = 1, max = 200, message = "Превышено максимальное количество символов")
    String description;
    @ReleaseAfter1895
    @NotNull(message = "Дата релиза не может быть null")
    LocalDate releaseDate;
    @JsonSerialize(using = DurationToSecondsSerializer.class)
    @PositiveDuration()
    Duration duration;

}
