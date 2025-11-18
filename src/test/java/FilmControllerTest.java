import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

public class FilmControllerTest {

    FilmController filmController = new FilmController();
    Film film = new Film();
    private Validator validator;
    private Set<ConstraintViolation<Film>> violations;

    @BeforeEach
    public void setFilm() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        film.setName("first-film");
        film.setDuration(Duration.ofSeconds(120));
        film.setDescription("first-film-description");
        film.setReleaseDate(LocalDate.of(2000, 01, 01));
        //Ручная валидация корректного файла. Список ошибок будет пуст.
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNullName() {
        //Замена имени на пустое значение. Обновляем валидатор, получаем ошибку.
        film.setName("");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testDescription0Symbol() {
        film.setDescription("");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testDescription100Symbol() {
        film.setDescription("A".repeat(100));
        assertEquals(100, film.getDescription().length());
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testDescription200Symbol() {
        film.setDescription("A".repeat(200));
        assertEquals(200, film.getDescription().length());
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testDescription201Symbol() {
        film.setDescription("A".repeat(201));
        assertEquals(201, film.getDescription().length());
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testReleaseDate_1895_12_28() {
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testReleaseDate_1895_12_27() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testDuration() {
        film.setDuration(Duration.ofMinutes(-10));
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
