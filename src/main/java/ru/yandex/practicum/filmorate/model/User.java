package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class User {
    long id;
    @NotNull
    @Email String email;
    @Pattern(regexp = "^\\S+$", message = "Логин не может содержать пробел или быть пустым")
    String login;
    String name;
    @PastOrPresent(message = "Дата должна быть в прошлом или сегодня")
    LocalDate birthday;
}
